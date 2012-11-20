package net.megx.was.webdav;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;

import javax.jcr.Credentials;
import javax.jcr.GuestCredentials;
import javax.jcr.LoginException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.server.CredentialsProvider;
import org.apache.jackrabbit.util.Base64;
import org.apache.jackrabbit.webdav.DavConstants;

public class DavCredentialsProvider implements CredentialsProvider {
	public static final String KEY = DavCredentialsProvider.class.getName();
	
	private static Log log = LogFactory.getLog(DavCredentialsProvider.class);
	
	public static final String EMPTY_DEFAULT_HEADER_VALUE = "";
	public static final String GUEST_DEFAULT_HEADER_VALUE = "guestcredentials";

	private final String defaultHeaderValue;
	private Repository repository;

	public DavCredentialsProvider(Repository repository,
			String defaultHeaderValue) {
		this.defaultHeaderValue = defaultHeaderValue;
		this.repository = repository;
	}

	@Override
	public Credentials getCredentials(HttpServletRequest request)
			throws LoginException, ServletException {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			//check session first
			SimpleCredentials credentials = getSessionCredentials(principal, request.getSession());
			if(credentials != null) {
				return credentials;
			} else {
				//if not exists, create one
				credentials = new SimpleCredentials(principal.getName(), "".toCharArray());
				//make sure worspace exists
				boolean wsok = ensureWorkspaceExists(principal.getName(), credentials);
				
				if (wsok) {
					//everything ok, put credentials in session for reuse
					setSessionCredentials(credentials, request.getSession());
					return credentials;
				} else {
					// or throw exception, workspace can't be created...
					return null;
				}
			}
		}
		
		//TODO: we dont need below right? 
		// we may remove and throw throw new LoginException(); 
		try {
			String authHeader = request
					.getHeader(DavConstants.HEADER_AUTHORIZATION);
			if (authHeader != null) {
				String[] authStr = authHeader.split(" ");
				if (authStr.length >= 2
						&& authStr[0]
								.equalsIgnoreCase(HttpServletRequest.BASIC_AUTH)) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					Base64.decode(authStr[1].toCharArray(), out);
					String decAuthStr = out.toString("ISO-8859-1");
					int pos = decAuthStr.indexOf(':');
					String userid = decAuthStr.substring(0, pos);
					String passwd = decAuthStr.substring(pos + 1);
					return new SimpleCredentials(userid, passwd.toCharArray());
				}
				throw new ServletException("Unable to decode authorization.");
			} else {
				// check special handling
				if (defaultHeaderValue == null) {
					throw new LoginException();
				} else if (EMPTY_DEFAULT_HEADER_VALUE
						.equals(defaultHeaderValue)) {
					return null;
				} else if (GUEST_DEFAULT_HEADER_VALUE
						.equals(defaultHeaderValue)) {
					return new GuestCredentials();
				} else {
					int pos = defaultHeaderValue.indexOf(':');
					if (pos < 0) {
						return new SimpleCredentials(defaultHeaderValue,
								new char[0]);
					} else {
						return new SimpleCredentials(
								defaultHeaderValue.substring(0, pos),
								defaultHeaderValue.substring(pos + 1)
										.toCharArray());
					}
				}
			}
		} catch (IOException e) {
			throw new ServletException("Unable to decode authorization: "
					+ e.toString());
		}
	}

	private void setSessionCredentials(SimpleCredentials credentials,
			HttpSession session) {
		session.setAttribute(KEY, credentials);
	}

	private SimpleCredentials getSessionCredentials(Principal principal,
			HttpSession session) {
		SimpleCredentials credentials = (SimpleCredentials) session.getAttribute(KEY);
		if(credentials != null) {
			//maybe unneccessary check for user?
			if(principal.getName().equals(credentials.getUserID())) {
				return credentials;
			} else {
				session.removeAttribute(KEY);
				return null;
			}
		}
		return null;
	}

	/**
	 * Creates workspace if not exists ... return true, if created or exists
	 * returns false if an error occurred
	 * 
	 * @param name
	 * @return
	 */
	private boolean ensureWorkspaceExists(String name, Credentials credentials) {
		Session sess = null;
		try {
			sess = repository.login(credentials);
			String[] wsnames = sess.getWorkspace()
					.getAccessibleWorkspaceNames();
			if (inArr(name, wsnames)) {
				// workspace exists
				return true;
			} else {
				log.info(" Creating Repository workspace " + name);
				sess.getWorkspace().createWorkspace(name);
			}
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (RepositoryException e) {
			// ugly check, we are getting something like workspace 'wsname'
			// already exists.
			if (e.getMessage().indexOf("already exists") > 0) {
				// ignore exteption
			} else {
				// TODO: handle exception
				e.printStackTrace();
				return false;
			}
		} finally {
			if (sess != null) {
				sess.logout();
			}
		}
		return true;
	}

	private static boolean inArr(String name, String[] wsnames) {
		if (wsnames != null) {
			for (String s : wsnames) {
				if (s.equals(name)) {
					return true;
				}
			}
		}
		return false;
	}
}
