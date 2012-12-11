package net.megx.ws.blast.mock;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.megx.ws.blast.BlastService;
import net.megx.ws.blast.uidomain.BlastJob;
import net.megx.ws.blast.uidomain.BlastMatchingSequence;

public class MockBlast implements BlastService {

	private MockBlast() {
		
	}
	
	static MockBlast instance = null;
	public static MockBlast getInstance() {
		if(instance == null) {
			instance = new MockBlast();
		}
		return instance;
	}
	
	@Override
	public BlastJob getBlastJob(String jobId) {
		BlastJob job = jobs.get(jobId);
		if(job != null) {
			if("running".equals(job.getStatus())) {
				Calendar c = job.getSubmitted();
				Calendar now = Calendar.getInstance();
				long delta = now.getTimeInMillis() - c.getTimeInMillis();
				if(delta > Math.random()*50000) {
					job.setStatus("completed");
				}
			}
			return job;
		}
		return null;
	}

	@Override
	public List<BlastMatchingSequence> getMatchingSequence(String jobId) {
		List<BlastMatchingSequence> ls = new ArrayList<BlastMatchingSequence>();
		for(int i=0; i<15; i++) {
			ls.add(seq(i+1));
		}
		return ls;
	}

	private BlastMatchingSequence seq(int i) {
		BlastMatchingSequence seq = new BlastMatchingSequence();
		seq.setEval(Double.parseDouble("2.761E-24"));
		seq.setScore(Math.random()*100);
		seq.setLen((int) (Math.random()*100));
		seq.setQuery(i+" Lorem ipsum");
		seq.setSubject(i+" Phasellus id");
		seq.setSamples(i+" Ut aliquam, ");
		seq.setLocations(i+" Vestibulum ");		
		return seq;
	}

	@Override
	public String getBlastJobStatus(String jobId) {
		return getBlastJob(jobId).getStatus();
	}

	private Map<String, BlastJob> jobs = new HashMap<String, BlastJob>();
	
	@Override
	public String runBlastJob(InputStream seq, String blastDb,
			String evalueCutoff) {
		String jobId = "10000-" +  jobs.keySet().size();
		BlastJob b = new BlastJob();
		b.setJobId(jobId);
		b.setJobName("My Workflow 09/16/2012 10:22PM");
		b.setStatus("running");
		b.setSubmitted(Calendar.getInstance());
		b.setProgram("tblastn");
		b.setQuerySequence("alternoFASTA");
		b.setSubjectSequence("All Metagenomics 454 cDNA Reads (N)");
		b.setHits(3);
		jobs.put(jobId, b);
		return jobId;
	}
}
