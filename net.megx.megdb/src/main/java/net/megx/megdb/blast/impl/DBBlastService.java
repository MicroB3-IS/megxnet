package net.megx.megdb.blast.impl;

import java.util.List;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.blast.BlastService;
import net.megx.megdb.blast.mappers.BlastMapper;
import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.model.blast.BlastHits;
import net.megx.model.blast.BlastHitsDb;
import net.megx.model.blast.BlastHitsNeighbours;
import net.megx.model.blast.BlastJob;
import net.megx.model.blast.MatchingSequences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBBlastService extends BaseMegdbService implements BlastService {

	private Log log = LogFactory.getLog(getClass());

	@Override
	public String insertBlastJob(final String label, final String customer,
			final int numNeighbors, final String toolLabel,
			final String toolVer, final String programName,
			final String biodbLabel, final String biodbVersion,
			final String rawFasta, final double evalue) throws DBGeneralFailureException {

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
				job.setEvalue(evalue);
				mapper.insertBlastJob(job);
				return String.valueOf(job.getId());
			}
		}, BlastMapper.class);

		return blastJobId;
	}

	@Override
	public BlastHits getSubnetGraphml(final int jid, final String hitId)
			throws DBGeneralFailureException, DBNoRecordsException {
		BlastHits result = doInSession(new DBTask<BlastMapper, BlastHits>() {
			@Override
			public BlastHits execute(BlastMapper mapper) throws Exception {
				return mapper.getSubnetGraphml(jid, hitId);
			}
		}, BlastMapper.class);

		if (result == null) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public BlastJob getBlastJobDetails(final int blastJobId)
			throws DBGeneralFailureException, DBNoRecordsException {
		BlastJob result = doInSession(new DBTask<BlastMapper, BlastJob>() {
			@Override
			public BlastJob execute(BlastMapper mapper) throws Exception {
				return mapper.getBlastJobDetails(blastJobId);
			}
		}, BlastMapper.class);

		if (result == null) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public BlastJob getSuccesfulBlastJob(final int id)
			throws DBGeneralFailureException, DBNoRecordsException {
		BlastJob result = doInSession(new DBTask<BlastMapper, BlastJob>() {
			@Override
			public BlastJob execute(BlastMapper mapper) throws Exception {
				return mapper.getSuccesfulBlastJob(id);
			}
		}, BlastMapper.class);

		if (result == null) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public List<BlastHitsNeighbours> getNeighbours(final int jid,
			final String hitId) throws DBGeneralFailureException,
			DBNoRecordsException {
		List<BlastHitsNeighbours> result = doInSession(
				new DBTask<BlastMapper, List<BlastHitsNeighbours>>() {
					@Override
					public List<BlastHitsNeighbours> execute(BlastMapper mapper)
							throws Exception {
						return mapper.getNeighbours(jid, hitId);
					}
				}, BlastMapper.class);
		if (result.size() == 0) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public List<BlastHitsDb> getDatabases() throws DBGeneralFailureException,
			DBNoRecordsException {
		List<BlastHitsDb> result = doInSession(
				new DBTask<BlastMapper, List<BlastHitsDb>>() {
					@Override
					public List<BlastHitsDb> execute(BlastMapper mapper)
							throws Exception {
						return mapper.getDatabases();
					}
				}, BlastMapper.class);
		if (result.size() == 0) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public BlastJob getResultRaw(final int id)
			throws DBGeneralFailureException, DBNoRecordsException {
		BlastJob result = doInSession(new DBTask<BlastMapper, BlastJob>() {
			@Override
			public BlastJob execute(BlastMapper mapper) throws Exception {
				return mapper.getResultRaw(id);
			}
		}, BlastMapper.class);
		if (result == null) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public BlastJob getGeographicRaw(final int id)
			throws DBGeneralFailureException, DBNoRecordsException {
		BlastJob result = doInSession(new DBTask<BlastMapper, BlastJob>() {
			@Override
			public BlastJob execute(BlastMapper mapper) throws Exception {
				return mapper.getGeographicRaw(id);
			}
		}, BlastMapper.class);
		if (result == null) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public List<MatchingSequences> getMatchingSequences()
			throws DBGeneralFailureException, DBNoRecordsException {
		List<MatchingSequences> result = doInSession(new DBTask<BlastMapper, List<MatchingSequences>>() {

			@Override
			public List<MatchingSequences> execute(BlastMapper mapper)
					throws Exception {
				return mapper.getMatchingSequences();
			}
			
		}, BlastMapper.class);
		if(result.size() == 0){
			throw new DBNoRecordsException("Query returned zero results");
		}else{
			return result;
		}
		
	}
}
