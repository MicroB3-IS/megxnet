package net.megx.ws.blast;

import java.io.InputStream;
import java.util.List;

import net.megx.ws.blast.uidomain.BlastJob;
import net.megx.ws.blast.uidomain.BlastMatchingSequence;
import net.megx.ws.blast.uidomain.SequenceAlignment;

public interface BlastService {
	/**
	 * Called from UI to get blast job status by jobId
	 * Recognized statuses are 
	 * 	running
	 *  completed
	 *  
	 * @param jobId
	 * @return
	 */
	public String getBlastJobStatus(String jobId);
	
	/**
	 * Called by UI to get blast job info
	 * 
	 * @param jobId
	 * @return
	 */
	public BlastJob getBlastJob(String jobId);
	
	/**
	 * Return matching sequences for blast job
	 * 
	 * @param jobId
	 * @return
	 */
	public List<BlastMatchingSequence> getMatchingSequence(String jobId);
	
	/**
	 * Start new blast job, return blast job id
	 * 
	 * @param seq, input stream, can be either from file of manually entered on UI in textbox
	 * 
	 * @param blastDb
	 * 
	 * @param evalueCutoff
	 * @return
	 */
	public String runBlastJob(InputStream seq, String blastDb, String evalueCutoff);
	
	/**
	 * Get Sequence alignment
	 * 
	 * TODO: see identifier for a sequence , for now just use index
	 * @param jobId
	 * @param seqIdx
	 * @return
	 */
	public SequenceAlignment getSequenceAlignment(String jobId, int seqIdx);
}
