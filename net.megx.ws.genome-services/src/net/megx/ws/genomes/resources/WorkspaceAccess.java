package net.megx.ws.genomes.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import net.megx.storage.StorageException;
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
		return contentModel.getSession().getRepository().login(username);
	}

	private Node getRelativeNode(Session session, String relativePath)
			throws PathNotFoundException, RepositoryException {
		return session.getNode(relativePath);
	}
}
