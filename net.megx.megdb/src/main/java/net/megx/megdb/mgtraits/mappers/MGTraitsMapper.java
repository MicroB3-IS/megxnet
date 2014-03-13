package net.megx.megdb.mgtraits.mappers;

import java.util.List;

import net.megx.model.mgtraits.MGTraitsAA;
import net.megx.model.mgtraits.MGTraitsCodon;
import net.megx.model.mgtraits.MGTraitsDNORatio;
import net.megx.model.mgtraits.MGTraitsDownloadJobs;
import net.megx.model.mgtraits.MGTraitsJobDetails;
import net.megx.model.mgtraits.MGTraitsPfam;
import net.megx.model.mgtraits.MGTraitsPublicJobDetails;
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
	
	public List<MGTraitsPublicJobDetails> getAllPublicJobs();
	
	public List<MGTraitsDownloadJobs> downloadJobs(@Param("traitIds") List<Integer> traitIds);

	public MGTraitsCodon getCodonUsage(@Param("id") int id);

	public List<MGTraitsTaxonomy> getTaxonomyContent(@Param("id") int id);

}
