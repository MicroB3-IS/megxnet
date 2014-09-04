package net.megx.megdb.geopfam.mappers;

import java.util.List;

import net.megx.model.geopfam.GeoPfamRow;

import org.apache.ibatis.annotations.Param;

public interface GeoPfamMapper {
	public List<GeoPfamRow> getByTargetAccession(
			@Param("targetAccession") String targetAccession);
}
