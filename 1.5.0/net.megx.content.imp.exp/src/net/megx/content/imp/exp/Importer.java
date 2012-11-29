package net.megx.content.imp.exp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.jcr.ImportUUIDBehavior;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.io.IOUtils;

public class Importer {
	
	private Session session;
	
	public Importer(Session session) {
		this.session = session;
	}

	public boolean imp(ZipInputStream zis) throws Exception {
		
		ZipInfo info = readInfo(zis);	
		
		ZipEntry ze = null;
		while( (ze = zis.getNextEntry()) != null) {
			//ze.setMethod(ZipEntry.DEFLATED);
			String path = info.getFilesPaths().get(ze.getName());
			ByteArrayOutputStream bas = new ByteArrayOutputStream();
			IOUtils.copy(zis, bas);
			ByteArrayInputStream bis = new ByteArrayInputStream(bas.toByteArray());
			String p = path.substring(0, path.lastIndexOf('/'));
			removeNode(path);
			session.importXML(p, bis, ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING);
			session.save();
			//String s = readInputStreamToString(zis, "utf-8");
            //System.out.println(s);
            zis.closeEntry();
		}
 		return true;
	}

	private boolean removeNode(String p) throws RepositoryException {
		try {
			Node n = session.getNode(p);
			n.remove();
			session.save();
			return true;
		} catch (PathNotFoundException e) {
			return false;
		} catch (RepositoryException e) {
			throw e;
		}
	}

	private ZipInfo readInfo(ZipInputStream zis) throws Exception {
		ZipEntry ze = zis.getNextEntry();
		if(!ze.getName().equals("info.json")) {
			throw new Exception("Invalid File structure. Missing info.json");
		}
		String s = readInputStreamToString(zis, "utf-8");
		zis.closeEntry();
		return ZipInfo.fromJSON(s);
	}

	private String readInputStreamToString(InputStream is, String charset) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int numRead;
        while ((numRead = is.read(buffer, 0, buffer.length)) != -1) {
        	bos.write(buffer, 0, numRead);
        }
        return new String(bos.toByteArray(), charset);
	}
}