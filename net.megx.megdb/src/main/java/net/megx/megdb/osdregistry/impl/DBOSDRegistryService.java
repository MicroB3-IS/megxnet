package net.megx.megdb.osdregistry.impl;

import java.util.List;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.megdb.osdregistry.OSDRegistryService;
import net.megx.megdb.osdregistry.mappers.OSDRegistryMapper;
import net.megx.model.osdregistry.OSDParticipant;
import net.megx.model.osdregistry.OSDParticipation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBOSDRegistryService extends BaseMegdbService implements
		OSDRegistryService {

	private Log log = LogFactory.getLog(getClass());

	@Override
	public List<OSDParticipant> getOSDParticipants()
			throws DBGeneralFailureException, DBNoRecordsException {

		log.debug("Get OSD participants list");

		List<OSDParticipant> result = doInSession(
				new DBTask<OSDRegistryMapper, List<OSDParticipant>>() {
					@Override
					public List<OSDParticipant> execute(OSDRegistryMapper mapper)
							throws Exception {
						return mapper.getOSDParticipants();
					}
				}, OSDRegistryMapper.class);
		if (result.size() == 0) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public void storeOSDParticipant(final OSDParticipant participant)
			throws DBGeneralFailureException {

		log.debug("Store OSD participant with ID: " + participant.getId());

		doInTransaction(new DBTask<OSDRegistryMapper, Object>() {
			@Override
			public Object execute(OSDRegistryMapper mapper) throws Exception {
				mapper.storeOSDParticipant(participant);
				return null;
			}
		}, OSDRegistryMapper.class);

	}

	@Override
	public OSDParticipant getParticipant(final String id) throws DBGeneralFailureException,DBNoRecordsException {

		log.debug("Get OSD participant with ID: " + id);

		OSDParticipant result = doInSession(new DBTask<OSDRegistryMapper, OSDParticipant>() {
			@Override
			public OSDParticipant execute(OSDRegistryMapper mapper)
					throws Exception {
				 
				return mapper.getOSDParticipant(id);
			}
		}, OSDRegistryMapper.class);
		if(result == null){
			throw new DBNoRecordsException("Query returned zero results");
		}else{
			return result;
		}
	}

	@Override
	public void updateOSDParticipant(final OSDParticipant participant)
			throws DBGeneralFailureException{

		log.debug("Update OSD participant with ID: " + participant.getId());

		doInTransaction(new DBTask<OSDRegistryMapper, Object>() {
			@Override
			public Object execute(OSDRegistryMapper mapper) throws Exception {
				mapper.updateOSDParticipant(participant);
				return null;
			}
		}, OSDRegistryMapper.class);

	}

	@Override
	public void deleteOSDParticipant(final String id) throws DBGeneralFailureException  {

		log.debug("Deleting OSD participant with ID: " + id);

		doInTransaction(new DBTask<OSDRegistryMapper, Object>() {
			@Override
			public Object execute(OSDRegistryMapper mapper) throws Exception {
				mapper.deleteOSDParticipant(id);
				return null;
			}
		}, OSDRegistryMapper.class);

	}

	@Override
	public void saveSample(final String json) throws DBGeneralFailureException {
		log.debug("Saving  Test Sample Submission");
		doInTransaction(new DBTask<OSDRegistryMapper, Object>() {
			@Override
			public Object execute(OSDRegistryMapper mapper) throws Exception {
				mapper.saveSample(json);
				return null;
			}
		}, OSDRegistryMapper.class);

	}

  @Override
  public void saveParticipation(final OSDParticipation participation)
      throws DBGeneralFailureException {
    log.debug("Saving participation");
    doInTransaction(new DBTask<OSDRegistryMapper, Object>() {
      @Override
      public Object execute(OSDRegistryMapper mapper) throws Exception {
        mapper.saveParticipation(participation);
        return null;
      }
    }, OSDRegistryMapper.class);
    
  }

}
