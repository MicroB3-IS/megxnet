package net.megx.ws.blast;

import java.io.InputStream;
import java.util.List;

import net.megx.ws.blast.uidomain.BlastJob;
import net.megx.ws.blast.uidomain.BlastMatchingSequence;

public interface BlastService {
	public String getBlastJobStatus(String jobId);
	public BlastJob getBlastJob(String jobId);
	public List<BlastMatchingSequence> getMatchingSequence(String jobId);
	public String runBlastJob(InputStream seq, String blastDb, String evalueCutoff);
}
