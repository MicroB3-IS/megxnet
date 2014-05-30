package net.megx.megdb.osdregistry;

import java.util.List;

import net.megx.model.osdregistry.OSDParticipant;

public interface OSDRegistryService {
	
	public List<OSDParticipant> getOSDParticipants() throws Exception;
	
	public void storeOSDParticipant(OSDParticipant participant) throws Exception;
	
	public void updateOSDParticipant(OSDParticipant participant) throws Exception;
	
	public OSDParticipant getParticipant(String id) throws Exception;
	
	public void deleteOSDParticipant(String id) throws Exception;
	
	public void saveSample(final String json) throws Exception;
}
