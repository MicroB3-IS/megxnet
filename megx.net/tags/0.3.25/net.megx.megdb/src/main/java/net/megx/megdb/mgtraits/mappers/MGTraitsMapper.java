package net.megx.megdb.mgtraits.mappers;

import org.apache.ibatis.annotations.Param;

public interface MGTraitsMapper {
	
	public void insertMGTraitsJob(
			@Param("inputFileURL") String inputFileURL,
			@Param("sampleName") String sampleName,
			@Param("envName") String envName);

}
