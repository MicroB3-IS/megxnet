package net.megx.ws.genomes;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.jcr.RepositoryException;

import net.megx.storage.StorageException;
import net.megx.storage.StoredResource;
import net.megx.ws.genomes.resources.WorkspaceAccess;

public class GCContent extends BaseGenomeService{

	private static final int CHUNK_SIZE = 4*1024; // 4KiB
	
	public GCContent(WorkspaceAccess access) {
		super(access);
	}

	
	public String gcContent(){
		return null;
	}
	
	
	
	public double calculateGCContent(String username, String filePath) throws RepositoryException, URISyntaxException, StorageException, IOException {
		StoredResource file = this.getAccess().getResource(username, filePath);
		InputStream stream = file.read();
		byte [] chunk = new byte[CHUNK_SIZE];
		int bytesRead = -1;
		long total = 0;
		long gcCount = 0;
		char b = 0;
		while((bytesRead = stream.read(chunk))>=0){
			for(int i = 0; i < bytesRead; i++, total++){
				b = (char)chunk[i];
				if(b == 'G' || b == 'g' || b == 'C' || b =='c'){
					gcCount++;
				}
			}
		}
		stream.close();
		return gcCount/((double)total);
		
	}
}
