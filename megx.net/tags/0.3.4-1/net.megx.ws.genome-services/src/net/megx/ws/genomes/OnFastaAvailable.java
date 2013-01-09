package net.megx.ws.genomes;

public interface OnFastaAvailable {
	public void process(FASTAEntry fastaEntry) throws Exception;
}
