package net.megx.megdb.mgtraits;

import java.util.List;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.model.mgtraits.MGTraitsAA;
import net.megx.model.mgtraits.MGTraitsCodon;
import net.megx.model.mgtraits.MGTraitsDNORatio;
import net.megx.model.mgtraits.MGTraitsJobDetails;
import net.megx.model.mgtraits.MGTraitsPfam;
import net.megx.model.mgtraits.MGTraitsResult;
import net.megx.model.mgtraits.MGTraitsTaxonomy;

public interface MGTraitsService {

	public MGTraitsResult getSimpleTraits(int id)
			throws DBGeneralFailureException, DBNoRecordsException;

	public MGTraitsPfam getFunctionTable(int id)
			throws DBGeneralFailureException, DBNoRecordsException;

	public MGTraitsAA getAminoAcidContent(int id)
			throws DBGeneralFailureException, DBNoRecordsException;

	public MGTraitsDNORatio getDiNucleotideOddsRatio(int id)
			throws DBGeneralFailureException, DBNoRecordsException;

	public MGTraitsJobDetails getJobDetails(int id)
			throws DBGeneralFailureException, DBNoRecordsException;

	public MGTraitsJobDetails getSuccesfulJob(int id)
			throws DBGeneralFailureException, DBNoRecordsException;

	public String insertJob(String customer, String mgUrl, String sampleLabel,
			String sampleEnvironment) throws DBGeneralFailureException;

	public List<MGTraitsJobDetails> getAllFinishedJobs()
			throws DBGeneralFailureException, DBNoRecordsException;

	public List<MGTraitsCodon> getCodonUsage()
			throws DBGeneralFailureException, DBNoRecordsException;

	public List<MGTraitsTaxonomy> getTaxonomyContent()
			throws DBGeneralFailureException, DBNoRecordsException;
}
