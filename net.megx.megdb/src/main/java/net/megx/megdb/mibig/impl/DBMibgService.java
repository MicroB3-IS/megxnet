package net.megx.megdb.mibig.impl;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.mibig.MibigService;
import net.megx.megdb.mibig.mappers.MibigMapper;
import net.megx.model.mibig.MibigSubmission;

public class DBMibgService extends BaseMegdbService implements MibigService {

	@Override
	public void storeMibigSubmission(final MibigSubmission mibig)
			throws DBGeneralFailureException {
		log.debug("Saving  MIBIG Submission");
		doInTransaction(new DBTask<MibigMapper, Object>() {
			@Override
			public Object execute(MibigMapper mapper) throws Exception {
				mapper.storeMibigSubmission(mibig);
				return null;
			}
		}, MibigMapper.class);

	}

}
