package net.megx.megdb.osdregistry.mappers;

import java.util.List;

import net.megx.model.osdregistry.OSDParticipant;

public interface OSDRegistryMapper {
	
	public List<OSDParticipant> getOSDParticipants();
	
	public void storeOSDParticipant(OSDParticipant participant);
	
	public OSDParticipant getOSDParticipant(String id);
	
	public void updateOSDParticipant(OSDParticipant participant);
	
	public void deleteOSDParticipant(String id);
	
	public void saveSample(String json);
}
