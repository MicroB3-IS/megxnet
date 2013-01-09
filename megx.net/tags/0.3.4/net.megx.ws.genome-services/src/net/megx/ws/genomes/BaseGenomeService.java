package net.megx.ws.genomes;

import java.io.IOException;
import java.io.InputStream;

import net.megx.ws.core.BaseRestService;
import net.megx.ws.genomes.resources.WorkspaceAccess;

public class BaseGenomeService extends BaseRestService{
	private WorkspaceAccess access;

	public BaseGenomeService(WorkspaceAccess access) {
		super();
		this.access = access;
	}
	
	protected WorkspaceAccess getAccess(){
		return access;
	}
	
	private static final int DEFAULT_LINE_WIDTH = 80;
	
	private static final char CR = 0x0D;
	private static final char LF = 0x0A;
	
	protected void processFastaStream(InputStream inputStream, OnFastaAvailable callback) throws IOException, Exception{
		StringBuffer line = new StringBuffer(DEFAULT_LINE_WIDTH);
		int c = 0;
		String lineEnd = null;
		FastaFileLineHandler lineHandler = new FastaFileLineHandler(callback);
		
		while((c = inputStream.read()) >= 0){
			if(c == CR || c == LF){
				if(lineEnd != null){
					lineEnd+=(char)c;
					lineHandler.onLine(line.toString(), lineEnd);
					lineEnd = null;
					line = new StringBuffer(DEFAULT_LINE_WIDTH);
				}else{
					lineEnd += (char)c;
				}
			}else{
				if(lineEnd != null){
					lineHandler.onLine(line.toString(), lineEnd);
					lineEnd = null;
					line = new StringBuffer(DEFAULT_LINE_WIDTH);
				}
				line.append((char)c);
			}
		}
		if(line.length() > 0){
			lineHandler.onLine(line.toString(), "");
			lineHandler.end();
		}
	}
	
	private static class FastaFileLineHandler{
		
		private OnFastaAvailable callback;
		private StringBuffer fasta;
		private int headerLength;
		private String headerSeparator;
		
		
		public FastaFileLineHandler(OnFastaAvailable callback) {
			this.callback = callback;
			
		}



		public void onLine(String line, String lineEnd) throws Exception{
			if(line.trim().startsWith(">")){
				// FASTA header line
				if(fasta != null){
					FASTAEntry entry = new FASTAEntry(fasta.toString(), headerLength, headerLength+headerSeparator.length());
					callback.process(entry);
				}else{
					fasta = new StringBuffer(4096); // 4KiB
				}
				headerLength = line.length();
				headerSeparator = lineEnd;
			}
			fasta.append(line);//.append(lineEnd);
		}
		
		public void end() throws Exception{
			if(fasta.length() > 0){
				String fs = fasta.toString();
				if(!fs.startsWith(">")){
					throw new Exception("Not a fasta sequence");
				}
				FASTAEntry entry = new FASTAEntry(fasta.toString(), headerLength, headerLength+headerSeparator.length());
				callback.process(entry);
			}
		}
	}
}
