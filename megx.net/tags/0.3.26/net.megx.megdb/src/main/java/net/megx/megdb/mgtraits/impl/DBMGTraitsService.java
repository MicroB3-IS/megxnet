package net.megx.megdb.mgtraits.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.mgtraits.MGTraitsService;
import net.megx.megdb.mgtraits.mappers.MGTraitsMapper;

public class DBMGTraitsService extends BaseMegdbService implements MGTraitsService{
	
	private Log log = LogFactory.getLog(getClass());
	
	@Override
	public void insertMGJob(final String inputFileURL, final String sampleName,
			final String envName) throws Exception {
		
		doInTransaction(new DBTask<MGTraitsMapper, Object>(){
			@Override
			public Object execute(MGTraitsMapper mapper) throws Exception{
				mapper.insertMGTraitsJob(inputFileURL, sampleName, envName);
				return null;
			}
		}, MGTraitsMapper.class);
	}

}
