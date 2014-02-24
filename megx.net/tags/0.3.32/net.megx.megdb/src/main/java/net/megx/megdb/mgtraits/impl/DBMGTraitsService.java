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
    public MGTraitsPfam getFunctionTable(final int id)
            throws DBGeneralFailureException, DBNoRecordsException {

        MGTraitsPfam result = doInSession(
                new DBTask<MGTraitsMapper, MGTraitsPfam>() {
                    @Override
                    public MGTraitsPfam execute(MGTraitsMapper mapper)
                            throws Exception {
                        return mapper.getFunctionTable(id);
                    }
                }, MGTraitsMapper.class);

        if (result == null) {
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
                    public MGTraitsJobDetails execute(
                            MGTraitsMapper mapper) throws Exception {
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
                    public MGTraitsJobDetails execute(
                            MGTraitsMapper mapper) throws Exception {
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
            final String sampleLabel, final String sampleEnvironment)
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
                        mapper.insertMGTraitsJob(job);
                        return job.getPublicSampleLabel();
                    }
                }, MGTraitsMapper.class);

        return publicSampleLable;
    }

    @Override
    public List<MGTraitsJobDetails> getAllFinishedJobs()
            throws DBGeneralFailureException, DBNoRecordsException {

        List<MGTraitsJobDetails> result = doInSession(
                new DBTask<MGTraitsMapper, List<MGTraitsJobDetails>>() {
                    @Override
                    public List<MGTraitsJobDetails> execute(
                            MGTraitsMapper mapper) throws Exception {
                        return mapper.getAllFinishedJobs();
                    }
                }, MGTraitsMapper.class);

        if (result.size() == 0) {
            throw new DBNoRecordsException("Query returned zero results");
        } else {
            return result;
        }
    }

}
