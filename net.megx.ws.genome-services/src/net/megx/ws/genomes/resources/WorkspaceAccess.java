package net.megx.ws.genomes.resources;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import net.megx.storage.ResourceAccessException;
import net.megx.storage.ResourceMeta;
import net.megx.storage.StorageException;
import net.megx.storage.StorageSecuirtyException;
import net.megx.storage.StorageSession;
import net.megx.storage.StoredResource;

import org.chon.cms.model.ContentModel;

public class WorkspaceAccess {

	public static final String STORED_RESOURCEURI_PROP = "STORAGE-URI";

	private ContentModel contentModel;
	private StorageSession storageSession;
	
	
	public WorkspaceAccess(ContentModel contentModel,
			StorageSession storageSession) {
		super();
		this.contentModel = contentModel;
		this.storageSession = storageSession;
	}

	public String getProperty(String username, String relativePath,
			String propName) throws RepositoryException {
		Session session = getUserSession(username);
		String propValue = null;
		try {
			Node requestedNode = getRelativeNode(session, relativePath);
			Property property = requestedNode.getProperty(propName);
			propValue = property.getString();
		} finally {
			session.logout();
		}
		return propValue;
	}

	public void setProperty(String username, String relativePath,
			String propName, String value) throws RepositoryException {
		Session session = getUserSession(username);
		try {
			Node requestedNode = getRelativeNode(session, relativePath);
			requestedNode.setProperty(propName, value);
		} finally {
			session.logout();
		}
	}

	public StoredResource getResource(String username, String relativePath)
			throws 	RepositoryException, URISyntaxException, StorageException {
		return storageSession.lookup(lookupResourceURI(username, relativePath));
	}
	public StoredResource getResourceJCR(String username, String relativePath)
			throws 	RepositoryException, URISyntaxException, StorageException {
		
		Session session = getUserSession(username);
		Node fileNode = getRelativeNode(session, relativePath);
		String type = fileNode.getProperty("jcr:primaryType").getString();
		if("nt:file".equals(type)){
			Node content = fileNode.getNode("jcr:content");
			JCRStoredResource resource = new JCRStoredResource(new URI(relativePath), content);
			return resource;
		}else{
			throw new ResourceAccessException("Invalid resource type.");
		}
	}
	
	
	public StoredResource createNewResourceJCR(String username, String relativePath)
		throws RepositoryException, URISyntaxException, StorageException{
		Session session = getUserSession(username);
		if(relativePath.startsWith("/")){
			relativePath = relativePath.substring(1);
		}
		String  [] path = relativePath.split("/");
		Node node = session.getRootNode();
		for(int i = 0; i < path.length-1; i++){
			node = getOrCreate(path[i], node);
		}
		node = node.addNode(path[path.length-1], "nt:file");
		Node contentNode = node.addNode("jcr:content", "nt:resource");
		contentNode.setProperty ("jcr:mimeType", "text/plain");
		contentNode.setProperty ("jcr:encoding", "UTF-8");
		contentNode.setProperty ("jcr:data", "");
        Calendar lastModified = Calendar.getInstance ();
        contentNode.setProperty ("jcr:lastModified", lastModified);
		return new JCRStoredResource(new URI(relativePath), contentNode);
	}
	
	private Node getOrCreate(String path, Node parent) throws RepositoryException{
		if(parent.hasNode(path)){
			return parent.getNode(path);
		}else{
			Node node = parent.addNode(path, "nt:files");
			return node;
		}
	}
	
	public StoredResource createNewResource(String username, String relativePath)
			throws RepositoryException, URISyntaxException, StorageException{
			return storageSession.create(createURI(username, relativePath));
		}
	
	protected URI createURI(String username, String relativePath) throws StorageException, URISyntaxException{
		return storageSession.createURI(null, username, relativePath);
	}
	
	
	private URI lookupResourceURI(String username, String relativePath) throws RepositoryException, URISyntaxException{
		Session session = getUserSession(username);
		Node node = getRelativeNode(session, relativePath);
		Property uriProp = node.getProperty(STORED_RESOURCEURI_PROP);
		String resourceURI = uriProp.getString();
		session.logout();
		URI uri = new URI(resourceURI);
		return uri;
	}

	private Session getUserSession(String username) throws RepositoryException {
		return contentModel.getSession().getRepository().login(new SimpleCredentials(username, "default".toCharArray()), username);
	}

	private Node getRelativeNode(Session session, String relativePath)
			throws PathNotFoundException, RepositoryException {
		return session.getNode("/"+relativePath);
	}
	
	private class JCRResourceMeta implements ResourceMeta{

		private Node contentNode;
		public JCRResourceMeta(Node contentNode) {
			this.contentNode = contentNode;
		}
		
		@Override
		public Date getDateCreated() throws StorageSecuirtyException,
				ResourceAccessException {
			Calendar cal = null;
			try {
				if(contentNode.getParent() != null){
					cal = contentNode.getParent().getProperty("jcr:created").getDate();
				}
			} catch (Exception e) {
				throw new ResourceAccessException(e);
			}
			return cal.getTime();
		}

		@Override
		public Date getDateModified() throws StorageSecuirtyException,
				ResourceAccessException {
			Calendar cal = null;
			try {
				cal = contentNode.getProperty("jcr:lastModified").getDate();
			} catch (Exception e) {
				throw new ResourceAccessException(e);
			}
			return cal.getTime();
		}

		@Override
		public long getSize() throws StorageSecuirtyException,
				ResourceAccessException {
			return 0;
		}

		@Override
		public Object getAttr(String meta) throws StorageSecuirtyException,
				ResourceAccessException {
			try {
				return contentNode.getProperty(meta).getString();
			} catch (Exception e) {
				throw new ResourceAccessException(e);
			}
		}

		@Override
		public void setAttr(String name, Object value)
				throws StorageSecuirtyException, ResourceAccessException {
			try{
				if(contentNode.getParent() != null){
					contentNode.getParent().setProperty(name, value.toString());
				}
			}catch (RepositoryException e) {
				throw new ResourceAccessException(e);
			}
		}
		
	}
	
	
	private class JCRStoredResource extends ByteArrayOutputStream implements StoredResource{
		
		private URI nodeURI;
		private Node contentNode;
		private JCRResourceMeta meta;
		
		public JCRStoredResource(URI nodeURI, Node contentNode) {
			this.nodeURI = nodeURI;
			this.contentNode = contentNode;
			meta = new JCRResourceMeta(contentNode);
		}

		@Override
		public InputStream read() throws StorageSecuirtyException,
				ResourceAccessException {
			try {
				return contentNode.getProperty("jcr:data").getBinary().getStream();
			} catch (Exception e) {
				throw new ResourceAccessException(e);
			}
		}

		@Override
		public OutputStream write() throws StorageSecuirtyException,
				ResourceAccessException {
			return this;
		}

		@Override
		public URI getURI() {
			return nodeURI;
		}

		@Override
		public URI getAccessURI() {
			return nodeURI;
		}

		@Override
		public ResourceMeta getResourceMeta() {
			return meta;
		}
		
		@Override
		public void close() throws IOException {
			super.close();
			byte [] buffer = toByteArray();
			if(buffer != null && buffer.length > 0){
				try {
					Binary binary = contentNode.getSession().getValueFactory().createBinary(new ByteArrayInputStream(buffer));
					contentNode.getProperty("jcr:data").setValue(binary);
					contentNode.getSession().save();
					binary.dispose();
				} catch (RepositoryException e) {
					throw new IOException(e);
				}
				
			}
		}
	}
	
	
}
