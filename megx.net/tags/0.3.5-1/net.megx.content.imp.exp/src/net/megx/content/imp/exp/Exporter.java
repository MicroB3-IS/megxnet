package net.megx.content.imp.exp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

public class Exporter {
	

	private List<String> nodePaths;
	private Session session;

	public Exporter(List<String> nodePaths, Session session) {
		this.nodePaths = nodePaths;
		this.session = session;
	}
	
	/**
	 * Export nodes to XML
	 * @return
	 * @throws RepositoryException 
	 * @throws IOException 
	 * @throws PathNotFoundException 
	 */
	public boolean export(ZipOutputStream zos) throws PathNotFoundException, IOException, RepositoryException {
		ZipInfo info = zipInfo();
		
		ZipEntry ze = new ZipEntry("info.json");
		zos.putNextEntry(ze);
		zos.write(info.toJSONString().getBytes());
		zos.closeEntry();
		
		for(Entry<String, String> e : info.getFilesPaths().entrySet()) {
			String name = e.getKey();
			String path = e.getValue();
			ze = new ZipEntry(name);
			zos.putNextEntry(ze);
			//TODO: check if exists...
			//Node node = session.getNode(path);
			session.exportSystemView(path, zos, false, false);
			zos.closeEntry();
		}
		zos.flush();
		zos.close();
		return true;
	}
	
	private ZipInfo zipInfo() {
		ZipInfo zi = new ZipInfo();
		Map<String, String> paths = new HashMap<String, String>();
		int n=1;
		for(String p : nodePaths) {
			String name = String.format("%02d", n);
			paths.put(name + ".xml", p);
			n++;
		}
		zi.setFilesPaths(paths);
		return zi;
	}

	public static void main(String[] args) {
		System.out.println(
				String.format("%02d", 99)
				);
	}
}
