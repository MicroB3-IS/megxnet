package net.megx.was.webdav;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;

import javax.jcr.Credentials;
import javax.jcr.GuestCredentials;
import javax.jcr.LoginException;
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.jackrabbit.server.CredentialsProvider;
import org.apache.jackrabbit.util.Base64;
import org.apache.jackrabbit.webdav.DavConstants;

public class DavCredentialsProvider implements CredentialsProvider {
	public static final String EMPTY_DEFAULT_HEADER_VALUE = "";
    public static final String GUEST_DEFAULT_HEADER_VALUE = "guestcredentials";

    private final String defaultHeaderValue;
    
	public DavCredentialsProvider(String defaultHeaderValue) {
		this.defaultHeaderValue = defaultHeaderValue;
	}

	@Override
	public Credentials getCredentials(HttpServletRequest request)
			throws LoginException, ServletException {
		Principal principal = request.getUserPrincipal();
		if(principal != null){
			return new SimpleCredentials(principal.getName(), "".toCharArray());
		}
		 try {
	            String authHeader = request.getHeader(DavConstants.HEADER_AUTHORIZATION);
	            if (authHeader != null) {
	                String[] authStr = authHeader.split(" ");
	                if (authStr.length >= 2 && authStr[0].equalsIgnoreCase(HttpServletRequest.BASIC_AUTH)) {
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
	                } else if (EMPTY_DEFAULT_HEADER_VALUE.equals(defaultHeaderValue)) {
	                    return null;
	                } else if (GUEST_DEFAULT_HEADER_VALUE.equals(defaultHeaderValue)) {
	                    return new GuestCredentials();
	                } else {
	                    int pos = defaultHeaderValue.indexOf(':');
	                    if (pos < 0) {
	                        return new SimpleCredentials(defaultHeaderValue, new char[0]);
	                    } else {
	                        return new SimpleCredentials(
	                                defaultHeaderValue.substring(0, pos),
	                                defaultHeaderValue.substring(pos+1).toCharArray()
	                        );
	                    }
	                }
	            }
	        } catch (IOException e) {
	            throw new ServletException("Unable to decode authorization: " + e.toString());
	        }
	}

}
