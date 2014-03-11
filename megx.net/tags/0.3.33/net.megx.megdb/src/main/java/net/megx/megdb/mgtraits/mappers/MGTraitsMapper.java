package net.megx.megdb.mgtraits.mappers;

import java.util.List;

import net.megx.model.mgtraits.MGTraitsAA;
import net.megx.model.mgtraits.MGTraitsCodon;
import net.megx.model.mgtraits.MGTraitsDNORatio;
import net.megx.model.mgtraits.MGTraitsJobDetails;
import net.megx.model.mgtraits.MGTraitsPfam;
import net.megx.model.mgtraits.MGTraitsResult;
import net.megx.model.mgtraits.MGTraitsTaxonomy;

import org.apache.ibatis.annotations.Param;

public interface MGTraitsMapper {

	public void insertMGTraitsJob(MGTraitsJobDetails job);

	public MGTraitsResult getSimpleTraits(@Param("id") int id);

	public MGTraitsPfam getFunctionTable(@Param("id") int id);

	public MGTraitsAA getAminoAcidContent(@Param("id") int id);

	public MGTraitsDNORatio getDiNucleotideOddsRatio(@Param("id") int id);

	public MGTraitsJobDetails getJobDetails(@Param("id") int id);

	public MGTraitsJobDetails getSuccesfulJob(@Param("id") int id);

	public List<MGTraitsJobDetails> getAllFinishedJobs();

	public List<MGTraitsCodon> getCodonUsage();

	public List<MGTraitsTaxonomy> getTaxonomyContent();

}
