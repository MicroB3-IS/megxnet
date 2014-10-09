package net.megx.megdb.mibig;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.model.mibig.MibigSubmission;

public interface MibigService {

	public void storeMibigSubmission(final MibigSubmission mibig)
			throws DBGeneralFailureException;

}
