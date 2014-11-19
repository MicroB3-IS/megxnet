package net.megx.storage.ams;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import net.megx.storage.Context;
import net.megx.storage.ResourceAccessException;
import net.megx.storage.StorageSecuirtyException;

public class URLBasedAccess extends BaseAccessMechanism{

	private Map<String, String> hostConfig;
	
	public URLBasedAccess(Context context, Map<String, String> hostConfig) {
		super(context);
		this.hostConfig = hostConfig;
	}
	
	// translates the following scheme:
	/*
	  <scheme>://{host}/{path}
	   - host is mapped from configuration 
	   - path is retained
	 */
	protected URL getAccessURL(URI uri) throws ResourceAccessException{
		String scheme = uri.getScheme();
		String host = uri.getHost();
		String path = uri.getRawPath();
		String query = uri.getRawQuery();
		String fragment = uri.getRawFragment();
		
		String resolvedHost = hostConfig.get(host);
		if(resolvedHost == null){
			resolvedHost = host;
		}
		
		String reassembledURL = 
				scheme + "://" + resolvedHost + path;
		
		if(query != null){
			reassembledURL += "?"+query;
		}
		if(fragment != null){
			reassembledURL +="#"+fragment;
		}
		URL url = null;
		try {
			url = new URL(reassembledURL);
		} catch (MalformedURLException e) {
			throw new ResourceAccessException();// wrap
		}
		return url;
	}
	
	
	@Override
	public boolean resourceExist(URI uri) throws StorageSecuirtyException {
		try {
			URL url = getAccessURL(uri);
			InputStream is = url.openStream();
			is.close();
		} catch (ResourceAccessException e) {
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public InputStream readResource(URI uri) throws StorageSecuirtyException,
			ResourceAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputStream writeResource(URI uri) throws StorageSecuirtyException,
			ResourceAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI create(URI uri) throws StorageSecuirtyException,
			ResourceAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(URI uri) throws StorageSecuirtyException,
			ResourceAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public URI move(URI uri, URI toURI) throws StorageSecuirtyException,
			ResourceAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI copy(URI uri, URI toURI) throws StorageSecuirtyException,
			ResourceAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI resolve(URI input) {
		try {
			return getAccessURL(input).toURI();
		} catch (ResourceAccessException e) {
			
		} catch (URISyntaxException e) {
			
		}
		return null;
	}

	@Override
	public Object readAttribute(URI resource, String attrName)
			throws StorageSecuirtyException, ResourceAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveAttribute(URI resource, String attrName, Object attrValue)
			throws StorageSecuirtyException, ResourceAccessException {
		// TODO Auto-generated method stub
		
	}

	
	public static void main(String[] args) throws URISyntaxException, IOException {
		String url = "http://myhost/the/path/to/the/file.1?someAttr=someValue&another#2";
		
		URI uri = new URI(url);
		System.out.println(uri.getScheme());
		System.out.println(uri.getHost());
		System.out.println(uri.getPath());
		System.out.println(uri.getRawPath());
		System.out.println(uri.getRawQuery());
		System.out.println(uri.getRawFragment());
		
		
		URL url2 = new URL("file:///home/pavle/text.txt");
		URLConnection c = url2.openConnection();
		System.out.println(c.getLastModified());
		//InputStream is = c.getInputStream();
		OutputStream os = c.getOutputStream();
		os.write("Test".getBytes("UTF-8"));
		os.close();
	}

	@Override
	public URI createURI(String... parts) throws URISyntaxException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
