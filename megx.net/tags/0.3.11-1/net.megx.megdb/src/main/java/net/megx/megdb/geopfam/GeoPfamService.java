package net.megx.megdb.geopfam;

import java.util.List;

import net.megx.model.geopfam.GeoPfamRow;

public interface GeoPfamService {
	public List<GeoPfamRow> getByTargetAccession(String targetAccession) throws Exception;
}
