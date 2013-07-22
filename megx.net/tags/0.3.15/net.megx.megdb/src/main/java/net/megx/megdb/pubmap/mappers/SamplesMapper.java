package net.megx.megdb.pubmap.mappers;

import org.apache.ibatis.annotations.Param;

public interface SamplesMapper {

	int deleteByPrimaryKey(@Param("study") String study,
			@Param("label") String label);

	int insert(net.megx.model.Sample record);

	int insertSampleSelective(net.megx.model.Sample record);

	net.megx.model.Sample selectByPrimaryKey(@Param("study") String study,
			@Param("label") String label);

	int updateByPrimaryKeySelective(net.megx.model.Sample record);

	int updateByPrimaryKey(net.megx.model.Sample record);
}