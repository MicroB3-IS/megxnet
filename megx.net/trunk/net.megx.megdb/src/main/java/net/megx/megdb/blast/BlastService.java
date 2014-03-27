package net.megx.megdb.blast;

import java.util.List;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.model.blast.BlastHits;
import net.megx.model.blast.BlastHitsDb;
import net.megx.model.blast.BlastHitsNeighbours;
import net.megx.model.blast.BlastJob;

public interface BlastService {

	public String insertBlastJob(final String label, final String customer,
			final int numNeighbors, final String toolLabel,
			final String toolVer, final String programName,
			final String biodbLabel, final String biodbVersion,
			final String rawFasta) throws DBGeneralFailureException;

	public BlastHits getSubnetGraphml(int jid, String hitId)
			throws DBGeneralFailureException, DBNoRecordsException;

	public BlastJob getBlastJobDetails(final int blastJobId)
			throws DBGeneralFailureException, DBNoRecordsException;

	public BlastJob getSuccesfulBlastJob(final int id)
			throws DBGeneralFailureException, DBNoRecordsException;

	public List<BlastHitsNeighbours> getNeighbours(int jid, String hitId)
			throws DBGeneralFailureException, DBNoRecordsException;

	public List<BlastHitsDb> getDatabases() throws DBGeneralFailureException,
			DBNoRecordsException;

	public BlastJob getResultRaw(int id) throws DBGeneralFailureException,
			DBNoRecordsException;

}
