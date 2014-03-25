package net.megx.megdb.blast;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.model.blast.BlastHits;

public interface BlastService {

	public String insertBlastJob(final String label, final String customer,
			final int numNeighbors, final String toolLabel,
			final String toolVer, final String programName,
			final String biodbLabel, final String biodbVersion,
			final String rawFasta) throws DBGeneralFailureException;

	public BlastHits getSubnetGraphml(int jid, String hitId)
			throws DBGeneralFailureException, DBNoRecordsException;
	
}
