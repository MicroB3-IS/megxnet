package net.megx.megdb.mgtraits.mappers;

import java.util.List;

import net.megx.model.mgtraits.MGTraitsAA;
import net.megx.model.mgtraits.MGTraitsDNORatio;
import net.megx.model.mgtraits.MGTraitsJobDetails;
import net.megx.model.mgtraits.MGTraitsPfam;
import net.megx.model.mgtraits.MGTraitsResult;

import org.apache.ibatis.annotations.Param;

public interface MGTraitsMapper {

	public void insertMGTraitsJob(MGTraitsJobDetails job);

	public List<MGTraitsResult> getSimpleTraits(@Param("id") int id);

	public List<MGTraitsPfam> getFunctionTable(@Param("id") int id);

	public List<MGTraitsAA> getAminoAcidContent(@Param("id") int id);

	public List<MGTraitsDNORatio> getDiNucleotideOddsRatio(
			@Param("id") int id);

	public List<MGTraitsJobDetails> getJobDetails(@Param("sampleLabel") String sampleLabel);

}
