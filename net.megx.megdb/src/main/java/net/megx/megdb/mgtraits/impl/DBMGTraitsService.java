package net.megx.megdb.mgtraits.impl;

import java.util.List;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.megdb.mgtraits.MGTraitsService;
import net.megx.megdb.mgtraits.mappers.MGTraitsMapper;
import net.megx.model.mgtraits.MGTraitsAA;
import net.megx.model.mgtraits.MGTraitsCodon;
import net.megx.model.mgtraits.MGTraitsDNORatio;
import net.megx.model.mgtraits.MGTraitsDownloadJobs;
import net.megx.model.mgtraits.MGTraitsFunctional;
import net.megx.model.mgtraits.MGTraitsJobDetails;
import net.megx.model.mgtraits.MGTraitsPublicJobDetails;
import net.megx.model.mgtraits.MGTraitsResult;
import net.megx.model.mgtraits.MGTraitsTaxonomy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBMGTraitsService extends BaseMegdbService implements
		MGTraitsService {

	private Log log = LogFactory.getLog(getClass());

	@Override
	public MGTraitsResult getSimpleTraits(final int id)
			throws DBGeneralFailureException, DBNoRecordsException {

		MGTraitsResult result = doInSession(
				new DBTask<MGTraitsMapper, MGTraitsResult>() {
					@Override
					public MGTraitsResult execute(MGTraitsMapper mapper)
							throws Exception {
						return mapper.getSimpleTraits(id);
					}
				}, MGTraitsMapper.class);

		if (result == null) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public List<MGTraitsFunctional> getFunctionTable(final int id)
			throws DBGeneralFailureException, DBNoRecordsException {

		List<MGTraitsFunctional> result = doInSession(
				new DBTask<MGTraitsMapper, List<MGTraitsFunctional>>() {
					@Override
					public List<MGTraitsFunctional> execute(MGTraitsMapper mapper)
							throws Exception {
						return mapper.getFunctionTable(id);
					}
				}, MGTraitsMapper.class);

		if (result.size() == 0) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public MGTraitsAA getAminoAcidContent(final int id)
			throws DBGeneralFailureException, DBNoRecordsException {

		MGTraitsAA result = doInSession(
				new DBTask<MGTraitsMapper, MGTraitsAA>() {
					@Override
					public MGTraitsAA execute(MGTraitsMapper mapper)
							throws Exception {
						return mapper.getAminoAcidContent(id);
					}
				}, MGTraitsMapper.class);

		if (result == null) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public MGTraitsDNORatio getDiNucleotideOddsRatio(final int id)
			throws DBGeneralFailureException, DBNoRecordsException {

		MGTraitsDNORatio result = doInSession(
				new DBTask<MGTraitsMapper, MGTraitsDNORatio>() {
					@Override
					public MGTraitsDNORatio execute(MGTraitsMapper mapper)
							throws Exception {
						return mapper.getDiNucleotideOddsRatio(id);
					}
				}, MGTraitsMapper.class);

		if (result == null) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public MGTraitsJobDetails getJobDetails(final int id)
			throws DBGeneralFailureException, DBNoRecordsException {

		MGTraitsJobDetails result = doInSession(
				new DBTask<MGTraitsMapper, MGTraitsJobDetails>() {
					@Override
					public MGTraitsJobDetails execute(MGTraitsMapper mapper)
							throws Exception {
						return mapper.getJobDetails(id);
					}
				}, MGTraitsMapper.class);

		if (result == null) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public MGTraitsJobDetails getSuccesfulJob(final int id)
			throws DBGeneralFailureException, DBNoRecordsException {

		MGTraitsJobDetails result = doInSession(
				new DBTask<MGTraitsMapper, MGTraitsJobDetails>() {
					@Override
					public MGTraitsJobDetails execute(MGTraitsMapper mapper)
							throws Exception {
						return mapper.getSuccesfulJob(id);
					}
				}, MGTraitsMapper.class);

		if (result == null) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public String insertJob(final String customer, final String mgUrl,
			final String sampleLabel, final String sampleEnvironment,final double sampleLatitude,
			final double sampleLongitude, final String sampleName, final String sampleDescription,
			final String sampleEnvOntology)
			throws DBGeneralFailureException {

		String publicSampleLable = doInTransaction(
				new DBTask<MGTraitsMapper, String>() {
					@Override
					public String execute(MGTraitsMapper mapper)
							throws Exception {
						MGTraitsJobDetails job = new MGTraitsJobDetails();
						job.setCustomer(customer);
						job.setMgUrl(mgUrl);
						job.setSampleLabel(sampleLabel);
						job.setSampleEnvironment(sampleEnvironment);
						job.setSampleLatitude(sampleLatitude);
						job.setSampleLongitude(sampleLongitude);
						job.setSampleName(sampleName);
						job.setSampleDescription(sampleDescription);
						job.setSampleEnvOntology(sampleEnvOntology);
						mapper.insertMGTraitsJob(job);
						return job.getPublicSampleLabel();
					}
				}, MGTraitsMapper.class);

		return publicSampleLable;
	}

	@Override
	public List<MGTraitsPublicJobDetails> getAllPublicJobs()
			throws DBGeneralFailureException, DBNoRecordsException {

		List<MGTraitsPublicJobDetails> result = doInSession(
				new DBTask<MGTraitsMapper, List<MGTraitsPublicJobDetails>>() {
					@Override
					public List<MGTraitsPublicJobDetails> execute(
							MGTraitsMapper mapper) throws Exception {
						return mapper.getAllPublicJobs();
					}
				}, MGTraitsMapper.class);

		if (result.size() == 0) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}
	
	@Override
	public List<MGTraitsDownloadJobs> downloadJobs(final List<Integer> traitIds)
			throws DBGeneralFailureException, DBNoRecordsException {

		List<MGTraitsDownloadJobs> result = doInSession(
				new DBTask<MGTraitsMapper, List<MGTraitsDownloadJobs>>() {
					@Override
					public List<MGTraitsDownloadJobs> execute(
							MGTraitsMapper mapper) throws Exception {
						return mapper.downloadJobs(traitIds);
					}
				}, MGTraitsMapper.class);

		if (result.size() == 0) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public MGTraitsCodon getCodonUsage(final int id)
			throws DBGeneralFailureException, DBNoRecordsException {

		MGTraitsCodon result = doInSession(
				new DBTask<MGTraitsMapper, MGTraitsCodon>() {
					@Override
					public MGTraitsCodon execute(MGTraitsMapper mapper)
							throws Exception {
						return mapper.getCodonUsage(id);
					}
				}, MGTraitsMapper.class);

		if (result == null) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public List<MGTraitsTaxonomy> getTaxonomyContent(final int id)
			throws DBGeneralFailureException, DBNoRecordsException {
		List<MGTraitsTaxonomy> result = doInSession(
				new DBTask<MGTraitsMapper, List<MGTraitsTaxonomy>>() {
					@Override
					public List<MGTraitsTaxonomy> execute(MGTraitsMapper mapper)
							throws Exception {
						return mapper.getTaxonomyContent(id);
					}
				}, MGTraitsMapper.class);

		if (result.size() == 0) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

}
