package net.megx.storage.ams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Map;

import net.megx.storage.Context;
import net.megx.storage.ResourceAccessException;
import net.megx.storage.StorageSecuirtyException;

public class FileAccess extends BaseAccessMechanism{

	private Map<String, String> config;
	
	public FileAccess(Context context, Map<String, String> config) {
		super(context);
		this.config = config;
	}

	@Override
	public boolean resourceExist(URI uri) throws StorageSecuirtyException {
		try {
			return new File(doResolve(uri)).exists();
		} catch (ResourceAccessException e) {
			return false;
		}
	}

	@Override
	public InputStream readResource(URI uri) throws StorageSecuirtyException,
			ResourceAccessException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(doResolve(uri)));
		} catch (FileNotFoundException e) {
			throw new ResourceAccessException(e);// wrap
		}
		return fis;
	}

	@Override
	public OutputStream writeResource(URI uri) throws StorageSecuirtyException,
			ResourceAccessException {
		try {
			return new FileOutputStream(new File(doResolve(uri)));
		} catch (FileNotFoundException e) {
			throw new ResourceAccessException(e);
		}
		
	}

	@Override
	public URI create(URI uri) throws StorageSecuirtyException,
			ResourceAccessException {
		URI newUri = doResolve(uri);
		File file = new File(newUri);
		if(!file.exists()){
			try {
				
				File parentDir = file.getParentFile();
				if(!parentDir.exists()){
					if(!parentDir.mkdirs()){
						throw new ResourceAccessException("Unable to create parent directories.");
					}
				}
				if(file.createNewFile()){
					// everything is fine
					return newUri;
				}else{
					throw new ResourceAccessException("Unable to create file at that location!");
				}
			} catch (IOException e) {
				throw new ResourceAccessException(e);
			}
		}else{
			throw new ResourceAccessException("File already exists!");
		}
	}

	@Override
	public void delete(URI uri) throws StorageSecuirtyException,
			ResourceAccessException {
		URI newUri = doResolve(uri);
		File file = new File(newUri);
		if(!file.delete()){
			throw new ResourceAccessException("Cannot delete file.");
		}
	}

	@Override
	public URI move(URI uri, URI toURI) throws StorageSecuirtyException,
			ResourceAccessException {
		URI from = doResolve(uri);
		URI to = doResolve(toURI);
		File start = new File(from);
		File toFile = new File(to);
		if(start.renameTo(toFile)){
			return to;
		}
		throw new ResourceAccessException("Unable to rename file.");
	}

	@Override
	public URI copy(URI uri, URI toURI) throws StorageSecuirtyException,
			ResourceAccessException {
		URI srcUri = doResolve(uri);
		URI destUri = doResolve(toURI);
		Path src = FileSystems.getDefault().getPath(srcUri.getPath());
		Path dest = FileSystems.getDefault().getPath(destUri.getPath());
		try {
			Files.copy(src, dest);
		} catch (IOException e) {
			throw new ResourceAccessException(e);
		}
		return destUri;
	}

	@Override
	public URI resolve(URI input) {
		try {
			return doResolve(input);
		} catch (ResourceAccessException e) {
			return null;
		}
	}
	
	protected URI doResolve(URI  input) throws ResourceAccessException{
		String host = input.getHost();
		String path = input.getPath();
		String resolvedHost = config.get(host);
		if(resolvedHost == null){
			throw new ResourceAccessException("Unknown host.");
		}
		try {
			return new URI("file",resolvedHost,path, null);
		} catch (URISyntaxException e) {
			throw new ResourceAccessException(e);
		}
	}

	@Override
	public Object readAttribute(URI resource, String attrName)
			throws StorageSecuirtyException, ResourceAccessException {
		if(resourceExist(resource)){
			URI rs = doResolve(resource);
			if(ATTR_DATE_MODIFIED.equals(attrName)){
				return new Date(new File(rs).lastModified());
			}else if(ATTR_DATE_CREATED.equals(attrName)){
				return new Date(new File(rs).lastModified()); // FIXME
			}else if(ATTR_SIZE.equals(attrName)){
				return new File(rs).length();
			}else{
				throw new ResourceAccessException("Cannot read requested attribute.");
			}
		}else{
			throw new ResourceAccessException("File not found");
		}
	}

	@Override
	public void saveAttribute(URI resource, String attrName, Object attrValue)
			throws StorageSecuirtyException, ResourceAccessException {
		throw new ResourceAccessException("Operation not available.");
	}

}
