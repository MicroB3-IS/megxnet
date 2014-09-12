package net.megx.megdb.osdregistry;

import java.util.List;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.model.osdregistry.OSDParticipant;

public interface OSDRegistryService {

	public List<OSDParticipant> getOSDParticipants()
			throws DBGeneralFailureException, DBNoRecordsException;

	public void storeOSDParticipant(OSDParticipant participant)
			throws DBGeneralFailureException;

	public void updateOSDParticipant(OSDParticipant participant)
			throws DBGeneralFailureException;

	public OSDParticipant getParticipant(String id)
			throws DBGeneralFailureException, DBNoRecordsException;

	public void deleteOSDParticipant(String id)
			throws DBGeneralFailureException;

	public void saveSample(final String json) throws DBGeneralFailureException;
}
