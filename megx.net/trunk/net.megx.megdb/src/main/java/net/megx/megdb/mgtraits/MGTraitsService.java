package net.megx.megdb.mgtraits;

import java.util.List;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.model.mgtraits.MGTraitsAA;
import net.megx.model.mgtraits.MGTraitsDNORatio;
import net.megx.model.mgtraits.MGTraitsJobDetails;
import net.megx.model.mgtraits.MGTraitsPfam;
import net.megx.model.mgtraits.MGTraitsResult;

public interface MGTraitsService {
    public List<MGTraitsResult> getSimpleTraits(int id)
            throws DBGeneralFailureException, DBNoRecordsException;

    public List<MGTraitsPfam> getFunctionTable(int id)
            throws DBGeneralFailureException, DBNoRecordsException;

    public List<MGTraitsAA> getAminoAcidContent(int id)
            throws DBGeneralFailureException, DBNoRecordsException;

    public List<MGTraitsDNORatio> getDiNucleotideOddsRatio(int id)
            throws DBGeneralFailureException, DBNoRecordsException;

    public List<MGTraitsJobDetails> getJobDetails(int id)
            throws DBGeneralFailureException, DBNoRecordsException;

    public String insertJob(String customer, String mgUrl, String sampleLabel,
            String sampleEnvironment) throws DBGeneralFailureException;

    public List<MGTraitsJobDetails> getAllFinishedJobs()
            throws DBGeneralFailureException, DBNoRecordsException;
}
