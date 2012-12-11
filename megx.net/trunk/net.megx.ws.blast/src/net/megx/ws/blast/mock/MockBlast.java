package net.megx.ws.blast.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.megx.ws.blast.BlastService;
import net.megx.ws.blast.uidomain.BlastJob;
import net.megx.ws.blast.uidomain.BlastMatchingSequence;

public class MockBlast implements BlastService {

	@Override
	public BlastJob getBlastJob(String jobId) {
		BlastJob b = new BlastJob();
		b.setJobId(jobId);
		b.setJobName("My Workflow 09/16/2012 10:22PM");
		b.setStatus("completed");
		b.setSubmitted(Calendar.getInstance());
		b.setProgram("tblastn");
		b.setQuerySequence("alternoFASTA");
		b.setSubjectSequence("All Metagenomics 454 cDNA Reads (N)");
		b.setHits(3);
		return b;
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
		return "done";
	}
}
