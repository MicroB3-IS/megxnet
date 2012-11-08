package net.megx.ws.genomes.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import net.megx.storage.ResourceAccessException;
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
			String propName) throws LoginException, NoSuchWorkspaceException,
			RepositoryException {
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
			String propName, String value) throws LoginException,
			NoSuchWorkspaceException, RepositoryException {
		Session session = getUserSession(username);
		try {
			Node requestedNode = getRelativeNode(session, relativePath);
			requestedNode.setProperty(propName, value);
		} finally {
			session.logout();
		}
	}

	public StoredResource getResource(String username, String relativePath)
			throws LoginException, NoSuchWorkspaceException,
			RepositoryException, URISyntaxException, ResourceAccessException,
			StorageSecuirtyException {
		Session session = getUserSession(username);
		Node node = getRelativeNode(session, relativePath);
		Property uriProp = node.getProperty(STORED_RESOURCEURI_PROP);
		String resourceURI = uriProp.getString();
		session.logout();
		URI uri = new URI(resourceURI);
		return storageSession.lookup(uri);
	}

	private Session getUserSession(String username) throws LoginException,
			NoSuchWorkspaceException, RepositoryException {
		return contentModel.getSession().getRepository().login(username);
	}

	private Node getRelativeNode(Session session, String relativePath)
			throws PathNotFoundException, RepositoryException {
		return session.getNode(relativePath);
	}
}
