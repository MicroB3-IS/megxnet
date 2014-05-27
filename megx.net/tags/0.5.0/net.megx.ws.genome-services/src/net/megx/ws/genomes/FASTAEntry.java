package net.megx.ws.genomes;

import java.io.UnsupportedEncodingException;

public class FASTAEntry {
	private String content;
	private int headerLength;
	private int contentStart;
	private String encoding = "UTF-8";
	
	
	
	
	
	public FASTAEntry(String content, int headerLength, int contentStart) {
		super();
		this.content = content;
		this.headerLength = headerLength;
		this.contentStart = contentStart;
	}

	public FASTAEntry(String content, int headerLength, int contentStart,
			String encoding) {
		super();
		this.content = content;
		this.headerLength = headerLength;
		this.contentStart = contentStart;
		this.encoding = encoding;
	}

	public String getHeader() throws UnsupportedEncodingException{
		return content.substring(0, headerLength);
	}
	
	public String getSequenceData(){
		return content.substring(contentStart);
	}
	
	public String getEntryContent(){
		return content;
	}
	
	public String getEncoding(){
		return encoding;
	}
}
