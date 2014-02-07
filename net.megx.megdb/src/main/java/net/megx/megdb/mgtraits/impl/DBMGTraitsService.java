package net.megx.megdb.mgtraits.impl;

import java.util.List;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.megdb.mgtraits.MGTraitsService;
import net.megx.megdb.mgtraits.mappers.MGTraitsMapper;
import net.megx.model.mgtraits.MGTraitsAA;
import net.megx.model.mgtraits.MGTraitsDNORatio;
import net.megx.model.mgtraits.MGTraitsJobDetails;
import net.megx.model.mgtraits.MGTraitsPfam;
import net.megx.model.mgtraits.MGTraitsResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBMGTraitsService extends BaseMegdbService implements
		MGTraitsService {

	private Log log = LogFactory.getLog(getClass());
	
	@Override
	public List<MGTraitsResult> getSimpleTraits(final int id)
			throws DBGeneralFailureException, DBNoRecordsException {

		List<MGTraitsResult> result = doInSession(
				new DBTask<MGTraitsMapper, List<MGTraitsResult>>() {
					@Override
					public List<MGTraitsResult> execute(MGTraitsMapper mapper)
							throws Exception {
						return mapper.getSimpleTraits(id);
					}
				}, MGTraitsMapper.class);

		if (result.size() == 0) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public List<MGTraitsPfam> getFunctionTable(final int id)
			throws DBGeneralFailureException, DBNoRecordsException {

		List<MGTraitsPfam> result = doInSession(
				new DBTask<MGTraitsMapper, List<MGTraitsPfam>>() {
					@Override
					public List<MGTraitsPfam> execute(MGTraitsMapper mapper)
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
	public List<MGTraitsAA> getAminoAcidContent(final int id)
			throws DBGeneralFailureException, DBNoRecordsException {

		List<MGTraitsAA> result = doInSession(
				new DBTask<MGTraitsMapper, List<MGTraitsAA>>() {
					@Override
					public List<MGTraitsAA> execute(MGTraitsMapper mapper)
							throws Exception {
						return mapper.getAminoAcidContent(id);
					}
				}, MGTraitsMapper.class);

		if (result.size() == 0) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public List<MGTraitsDNORatio> getDiNucleotideOddsRatio(final int id)
			throws DBGeneralFailureException, DBNoRecordsException {

		List<MGTraitsDNORatio> result = doInSession(
				new DBTask<MGTraitsMapper, List<MGTraitsDNORatio>>() {
					@Override
					public List<MGTraitsDNORatio> execute(MGTraitsMapper mapper)
							throws Exception {
						return mapper.getDiNucleotideOddsRatio(id);
					}
				}, MGTraitsMapper.class);

		if (result.size() == 0) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public List<MGTraitsJobDetails> getJobDetails(final String sampleLabel)
			throws DBGeneralFailureException, DBNoRecordsException {

		List<MGTraitsJobDetails> result = doInSession(
				new DBTask<MGTraitsMapper, List<MGTraitsJobDetails>>() {
					@Override
					public List<MGTraitsJobDetails> execute(
							MGTraitsMapper mapper) throws Exception {
						return mapper.getJobDetails(sampleLabel);
					}
				}, MGTraitsMapper.class);

		if (result.size() == 0) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

	@Override
	public String insertJob(final String customer, final String mgUrl, final String sampleLabel,
			final String sampleEnvironment) throws DBGeneralFailureException {

		String jobUrl = doInTransaction(new DBTask<MGTraitsMapper, String>() {
			@Override
			public String execute(MGTraitsMapper mapper) throws Exception {
				MGTraitsJobDetails job = new MGTraitsJobDetails();
				job.setCustomer(customer);
				job.setMgUrl(mgUrl);
				job.setSampleLabel(sampleLabel);
				job.setSampleEnvironment(sampleEnvironment);
				mapper.insertMGTraitsJob(job);
				return  job.getSampleLabel();
			}
		}, MGTraitsMapper.class);
		
		return jobUrl;
	}

}
