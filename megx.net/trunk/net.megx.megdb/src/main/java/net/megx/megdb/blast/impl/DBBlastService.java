package net.megx.megdb.blast.impl;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.blast.BlastService;
import net.megx.megdb.blast.mappers.BlastMapper;
import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.model.blast.BlastJob;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBBlastService extends BaseMegdbService implements BlastService {

	private Log log = LogFactory.getLog(getClass());

	@Override
	public String insertBlastJob(final String label, final String customer,
			final int numNeighbors, final String toolLabel,
			final String toolVer, final String programName,
			final String biodbLabel, final String biodbVersion,
			final String rawFasta) throws DBGeneralFailureException {

		String blastJobId = doInTransaction(new DBTask<BlastMapper, String>() {
			@Override
			public String execute(BlastMapper mapper) throws Exception {
				BlastJob job = new BlastJob();
				job.setLabel(label);
				job.setCustomer(customer);
				job.setNumNeighbors(numNeighbors);
				job.setToolLabel(toolLabel);
				job.setToolVer(toolVer);
				job.setProgramName(programName);
				job.setBiodbLabel(biodbLabel);
				job.setBiodbVersion(biodbVersion);
				job.setRawFasta(rawFasta);
				mapper.insertBlastJob(job);
				return String.valueOf(job.getId());
			}
		}, BlastMapper.class);

		return blastJobId;
	}

}
