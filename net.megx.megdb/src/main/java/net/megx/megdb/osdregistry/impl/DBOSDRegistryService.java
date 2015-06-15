package net.megx.megdb.osdregistry.impl;

import java.util.ArrayList;
import java.util.List;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.osdregistry.OSDRegistryService;
import net.megx.megdb.osdregistry.mappers.OSDRegistryMapper;
import net.megx.model.osdregistry.OSDParticipant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBOSDRegistryService extends BaseMegdbService implements OSDRegistryService{
	
	private Log log = LogFactory.getLog(getClass());
	
	@Override
	public List<OSDParticipant> getOSDParticipants() throws Exception {
		
		log.debug("Get OSD participants list");
		
		return doInSession(new DBTask<OSDRegistryMapper, List<OSDParticipant>>(){
			@Override
			public List<OSDParticipant> execute(OSDRegistryMapper mapper) throws Exception{
				List<OSDParticipant> osdParticipants = new ArrayList<OSDParticipant>();
				for (OSDParticipant osdParticipant : mapper.getOSDParticipants()) {
					osdParticipants.add(osdParticipant);
				}
				return osdParticipants;
			}
		}, OSDRegistryMapper.class);
	}

	@Override
	public void storeOSDParticipant(final OSDParticipant participant)
			throws Exception {
		
		log.debug("Store OSD participant with ID: " + participant.getId());
		
		doInTransaction(new DBTask<OSDRegistryMapper, Object>(){
			@Override
			public Object execute(OSDRegistryMapper mapper) throws Exception{
				mapper.storeOSDParticipant(participant);
				return null;
			}
		}, OSDRegistryMapper.class);
		
	}

	@Override
	public OSDParticipant getParticipant(final String id) throws Exception{
		
		log.debug("Get OSD participant with ID: " + id);
		
		return doInSession(new DBTask<OSDRegistryMapper, OSDParticipant>(){
			@Override
			public OSDParticipant execute(OSDRegistryMapper mapper) throws Exception{
				OSDParticipant participant = mapper.getOSDParticipant(id);
				return participant;
			}
		}, OSDRegistryMapper.class);
	}

	@Override
	public void updateOSDParticipant(final OSDParticipant participant)
			throws Exception {
		
		log.debug("Update OSD participant with ID: " + participant.getId());
		
		doInTransaction(new DBTask<OSDRegistryMapper, Object>(){
			@Override
			public Object execute(OSDRegistryMapper mapper) throws Exception{
				mapper.updateOSDParticipant(participant);
				return null;
			}
		}, OSDRegistryMapper.class);
		
	}

	@Override
	public void deleteOSDParticipant(final String id) throws Exception{
		
		log.debug("Deleting OSD participant with ID: " + id);
		
		doInTransaction(new DBTask<OSDRegistryMapper, Object>(){
			@Override
			public Object execute(OSDRegistryMapper mapper) throws Exception{
				mapper.deleteOSDParticipant(id);
				return null;
			}
		}, OSDRegistryMapper.class);
		
	}

	@Override
	public void saveSample(final String json) throws Exception {
		log.debug("Saving  Test Sample Submission");
		doInTransaction(new DBTask<OSDRegistryMapper, Object>(){
			@Override
			public Object execute(OSDRegistryMapper mapper) throws Exception{
				mapper.saveSample(json);
				return null;
			}
		}, OSDRegistryMapper.class);
		
	}

}
