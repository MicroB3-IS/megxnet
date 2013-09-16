package net.megx.ws.genomes;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.jcr.RepositoryException;

import net.megx.storage.StorageException;
import net.megx.storage.StoredResource;
import net.megx.ws.core.providers.exceptions.ServiceException;
import net.megx.ws.genomes.resources.WorkspaceAccess;

import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.io.FastaReaderHelper;

public class GCContent extends BaseGenomeService {

	//private static final int CHUNK_SIZE = 4 * 1024; // 4KiB

	public GCContent(WorkspaceAccess access) {
		super(access);
	}

	public String gcContent() {
		return null;
	}

	public void calculateGCContent(InputStream inputFile,
			OutputStream outputFile, boolean writeHeaders) throws ServiceException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputFile));
		String line = null;
		StringBuffer buffer = new StringBuffer();
		String nl = String.format("%n");
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if (line.startsWith(">")) {
				if (buffer.length() > 0) {
					storeGC_Content(buffer, outputFile, writeHeaders);
					writeHeaders = false;
					buffer = new StringBuffer();
				}
			}
			buffer.append(line);
			buffer.append(nl);
		}
		if (buffer.length() > 0) {
			storeGC_Content(buffer, outputFile, writeHeaders);
		}
	}

	private void storeGC_Content(StringBuffer fasta, OutputStream outputStream, boolean writeHeaders) throws ServiceException, IOException {
		Map<String, DNASequence> sequences = null;
		try {
			sequences = FastaReaderHelper
					.readFastaDNASequence(new ByteArrayInputStream(fasta.toString()
							.getBytes()));
		} catch (Exception e) {
			throw new ServiceException(e, "Failed to parse FASTA.", "Failed to parse requested FASTA/MultiFASTA.");
		}
		PrintWriter pw = new PrintWriter(outputStream);
		if(writeHeaders){
			pw.println("\"identifier\",\"gc-content\"");
		}
		for(Map.Entry<String, DNASequence> e: sequences.entrySet()){
			double gcContent = e.getValue().getGCCount()/(double)e.getValue().getLength();
			pw.println(String.format("\"%s\",\""+gcContent+"\"", e.getKey().replaceAll("\"", "\"\"")));
		}
		pw.flush();
	}

	
	public void calculateGCContent(String username, String inputFile, OutputStream outStream, boolean writeHeaders) throws RepositoryException, StorageException, Exception{
		StoredResource inputResource = getAccess().getResourceJCR(username, inputFile);
		InputStream inputStream = inputResource.read();
		calculateGCContent(inputStream, outStream, writeHeaders);
		inputStream.close();
	}
	
	public void calculateGCContent(String username, String inputFile, String outFile, boolean writeHeaders) throws RepositoryException, StorageException, Exception{
		StoredResource outResource = getAccess().createNewResourceJCR(username, outFile);
		OutputStream os = outResource.write();
		calculateGCContent(username, inputFile, os, writeHeaders);
		os.close();
	}
}
