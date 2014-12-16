package net.megx.model.blast;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.postgresql.util.PGInterval;

public class BlastJob {

	private int id;
	private String label;
	private String customer;
	private int numNeighbors;
	private String toolLabel;
	private String toolVer;
	private String programName;
	private String biodbLabel;
	private String biodbVersion;
	private String rawFasta;
	private double evalue;
	private int gapOpen;
	private int gapExtend;
	private int xDropoff;
	private boolean giDefine;
	private int nucMismatch;
	private int nucMatch;
	private int numDesc;
	private int numAlign;
	private int extThreshold;
	private boolean gapAlign;
	private int geneticCode;
	private int dbGenCode;
	private int numProcessors;
	private boolean believeSeqFile;
	private String matrix;
	private int wordSize;
	private double effectiveDb;
	private int keptHits;
	private double effectiveSpace;
	private int queryStrand;
	private double xDropoffUngap;
	private double xDropoffGap;
	private int multiHitsWinSize;
	private int concatQueries;
	private boolean legacyEngine;
	private String compositionStat;
	private boolean localOptimum;
	private String result;
	private String resultRaw;
	private char filter;
	private Timestamp timeSubmitted;
	private Timestamp timeFinished;
	private PGInterval makePublic;
	private PGInterval keepData;
	private Timestamp timeStarted;
	private String clusterNode;
	private int jobId;
	private int returnCode = -1;
	private String errorMessage;
	private double totalRunTime;
	private String[] timeProtocol;
	private String queryId;
	private String querySeq;
	
	private String formatedTimeSubmitted = "";
	private String formatedTimeFinished = "";
	private String formatedTimeStarted = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public int getNumNeighbors() {
		return numNeighbors;
	}

	public void setNumNeighbors(int numNeighbors) {
		this.numNeighbors = numNeighbors;
	}

	public String getToolLabel() {
		return toolLabel;
	}

	public void setToolLabel(String toolLabel) {
		this.toolLabel = toolLabel;
	}

	public String getToolVer() {
		return toolVer;
	}

	public void setToolVer(String toolVer) {
		this.toolVer = toolVer;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getBiodbLabel() {
		return biodbLabel;
	}

	public void setBiodbLabel(String biodbLabel) {
		this.biodbLabel = biodbLabel;
	}

	public String getBiodbVersion() {
		return biodbVersion;
	}

	public void setBiodbVersion(String biodbVersion) {
		this.biodbVersion = biodbVersion;
	}

	public String getRawFasta() {
		return rawFasta;
	}

	public void setRawFasta(String rawFasta) {
		this.rawFasta = rawFasta;
	}

	public double getEvalue() {
		return evalue;
	}

	public void setEvalue(double evalue) {
		this.evalue = evalue;
	}

	public int getGapOpen() {
		return gapOpen;
	}

	public void setGapOpen(int gapOpen) {
		this.gapOpen = gapOpen;
	}

	public int getGapExtend() {
		return gapExtend;
	}

	public void setGapExtend(int gapExtend) {
		this.gapExtend = gapExtend;
	}

	public int getxDropoff() {
		return xDropoff;
	}

	public void setxDropoff(int xDropoff) {
		this.xDropoff = xDropoff;
	}

	public boolean isGiDefine() {
		return giDefine;
	}

	public void setGiDefine(boolean giDefine) {
		this.giDefine = giDefine;
	}

	public int getNucMismatch() {
		return nucMismatch;
	}

	public void setNucMismatch(int nucMismatch) {
		this.nucMismatch = nucMismatch;
	}

	public int getNucMatch() {
		return nucMatch;
	}

	public void setNucMatch(int nucMatch) {
		this.nucMatch = nucMatch;
	}

	public int getNumDesc() {
		return numDesc;
	}

	public void setNumDesc(int numDesc) {
		this.numDesc = numDesc;
	}

	public int getNumAlign() {
		return numAlign;
	}

	public void setNumAlign(int numAlign) {
		this.numAlign = numAlign;
	}

	public int getExtThreshold() {
		return extThreshold;
	}

	public void setExtThreshold(int extThreshold) {
		this.extThreshold = extThreshold;
	}

	public boolean isGapAlign() {
		return gapAlign;
	}

	public void setGapAlign(boolean gapAlign) {
		this.gapAlign = gapAlign;
	}

	public int getGeneticCode() {
		return geneticCode;
	}

	public void setGeneticCode(int geneticCode) {
		this.geneticCode = geneticCode;
	}

	public int getDbGenCode() {
		return dbGenCode;
	}

	public void setDbGenCode(int dbGenCode) {
		this.dbGenCode = dbGenCode;
	}

	public int getNumProcessors() {
		return numProcessors;
	}

	public void setNumProcessors(int numProcessors) {
		this.numProcessors = numProcessors;
	}

	public boolean isBelieveSeqFile() {
		return believeSeqFile;
	}

	public void setBelieveSeqFile(boolean believeSeqFile) {
		this.believeSeqFile = believeSeqFile;
	}

	public String getMatrix() {
		return matrix;
	}

	public void setMatrix(String matrix) {
		this.matrix = matrix;
	}

	public int getWordSize() {
		return wordSize;
	}

	public void setWordSize(int wordSize) {
		this.wordSize = wordSize;
	}

	public double getEffectiveDb() {
		return effectiveDb;
	}

	public void setEffectiveDb(double effectiveDb) {
		this.effectiveDb = effectiveDb;
	}

	public int getKeptHits() {
		return keptHits;
	}

	public void setKeptHits(int keptHits) {
		this.keptHits = keptHits;
	}

	public double getEffectiveSpace() {
		return effectiveSpace;
	}

	public void setEffectiveSpace(double effectiveSpace) {
		this.effectiveSpace = effectiveSpace;
	}

	public int getQueryStrand() {
		return queryStrand;
	}

	public void setQueryStrand(int queryStrand) {
		this.queryStrand = queryStrand;
	}

	public double getxDropoffUngap() {
		return xDropoffUngap;
	}

	public void setxDropoffUngap(double xDropoffUngap) {
		this.xDropoffUngap = xDropoffUngap;
	}

	public double getxDropoffGap() {
		return xDropoffGap;
	}

	public void setxDropoffGap(double xDropoffGap) {
		this.xDropoffGap = xDropoffGap;
	}

	public int getMultiHitsWinSize() {
		return multiHitsWinSize;
	}

	public void setMultiHitsWinSize(int multiHitsWinSize) {
		this.multiHitsWinSize = multiHitsWinSize;
	}

	public int getConcatQueries() {
		return concatQueries;
	}

	public void setConcatQueries(int concatQueries) {
		this.concatQueries = concatQueries;
	}

	public boolean isLegacyEngine() {
		return legacyEngine;
	}

	public void setLegacyEngine(boolean legacyEngine) {
		this.legacyEngine = legacyEngine;
	}

	public String getCompositionStat() {
		return compositionStat;
	}

	public void setCompositionStat(String compositionStat) {
		this.compositionStat = compositionStat;
	}

	public boolean isLocalOptimum() {
		return localOptimum;
	}

	public void setLocalOptimum(boolean localOptimum) {
		this.localOptimum = localOptimum;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultRaw() {
		return resultRaw;
	}

	public void setResultRaw(String resultRaw) {
		this.resultRaw = resultRaw;
	}

	public char getFilter() {
		return filter;
	}

	public void setFilter(char filter) {
		this.filter = filter;
	}

	public String getTimeSubmitted() {
		if (this.timeSubmitted != null) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
			formatedTimeSubmitted = df.format(this.timeSubmitted);
		}
		return formatedTimeSubmitted;
	}

	public void setTimeSubmitted(Timestamp timeSubmitted) {
		this.timeSubmitted = timeSubmitted;
	}

	public String getTimeFinished() {
		if (this.timeFinished != null) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
			formatedTimeFinished = df.format(this.timeFinished);
		}
		return formatedTimeFinished;
	}

	public void setTimeFinished(Timestamp timeFinished) {
		this.timeFinished = timeFinished;
	}

	public PGInterval getMakePublic() {
		return makePublic;
	}

	public void setMakePublic(PGInterval makePublic) {
		this.makePublic = makePublic;
	}

	public PGInterval getKeepData() {
		return keepData;
	}

	public void setKeepData(PGInterval keepData) {
		this.keepData = keepData;
	}

	public String getTimeStarted() {
		if (this.timeStarted != null) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
			formatedTimeStarted = df.format(this.timeStarted);
		}
		return formatedTimeStarted;
	}

	public void setTimeStarted(Timestamp timeStarted) {
		this.timeStarted = timeStarted;
	}

	public String getClusterNode() {
		return clusterNode;
	}

	public void setClusterNode(String clusterNode) {
		this.clusterNode = clusterNode;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public double getTotalRunTime() {
		return totalRunTime;
	}

	public void setTotalRunTime(double totalRunTime) {
		this.totalRunTime = totalRunTime;
	}

	public String[] getTimeProtocol() {
		return timeProtocol;
	}

	public void setTimeProtocol(String[] timeProtocol) {
		this.timeProtocol = timeProtocol;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getQuerySeq() {
		return querySeq;
	}

	public void setQuerySeq(String querySeq) {
		this.querySeq = querySeq;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (believeSeqFile ? 1231 : 1237);
		result = prime * result
				+ ((biodbLabel == null) ? 0 : biodbLabel.hashCode());
		result = prime * result
				+ ((biodbVersion == null) ? 0 : biodbVersion.hashCode());
		result = prime * result
				+ ((clusterNode == null) ? 0 : clusterNode.hashCode());
		result = prime * result
				+ ((compositionStat == null) ? 0 : compositionStat.hashCode());
		result = prime * result + concatQueries;
		result = prime * result
				+ ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + dbGenCode;
		long temp;
		temp = Double.doubleToLongBits(effectiveDb);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(effectiveSpace);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((errorMessage == null) ? 0 : errorMessage.hashCode());
		temp = Double.doubleToLongBits(evalue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + extThreshold;
		result = prime * result + filter;
		result = prime * result + (gapAlign ? 1231 : 1237);
		result = prime * result + gapExtend;
		result = prime * result + gapOpen;
		result = prime * result + geneticCode;
		result = prime * result + (giDefine ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + jobId;
		result = prime * result
				+ ((keepData == null) ? 0 : keepData.hashCode());
		result = prime * result + keptHits;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + (legacyEngine ? 1231 : 1237);
		result = prime * result + (localOptimum ? 1231 : 1237);
		result = prime * result
				+ ((makePublic == null) ? 0 : makePublic.hashCode());
		result = prime * result + ((matrix == null) ? 0 : matrix.hashCode());
		result = prime * result + multiHitsWinSize;
		result = prime * result + nucMatch;
		result = prime * result + nucMismatch;
		result = prime * result + numAlign;
		result = prime * result + numDesc;
		result = prime * result + numNeighbors;
		result = prime * result + numProcessors;
		result = prime * result
				+ ((programName == null) ? 0 : programName.hashCode());
		result = prime * result + ((queryId == null) ? 0 : queryId.hashCode());
		result = prime * result
				+ ((querySeq == null) ? 0 : querySeq.hashCode());
		result = prime * result + queryStrand;
		result = prime * result
				+ ((rawFasta == null) ? 0 : rawFasta.hashCode());
		result = prime * result
				+ ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result
				+ ((resultRaw == null) ? 0 : resultRaw.hashCode());
		result = prime * result + returnCode;
		result = prime * result
				+ ((timeFinished == null) ? 0 : timeFinished.hashCode());
		result = prime * result + Arrays.hashCode(timeProtocol);
		result = prime * result
				+ ((timeStarted == null) ? 0 : timeStarted.hashCode());
		result = prime * result
				+ ((timeSubmitted == null) ? 0 : timeSubmitted.hashCode());
		result = prime * result
				+ ((toolLabel == null) ? 0 : toolLabel.hashCode());
		result = prime * result + ((toolVer == null) ? 0 : toolVer.hashCode());
		temp = Double.doubleToLongBits(totalRunTime);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + wordSize;
		result = prime * result + xDropoff;
		temp = Double.doubleToLongBits(xDropoffGap);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(xDropoffUngap);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlastJob other = (BlastJob) obj;
		if (believeSeqFile != other.believeSeqFile)
			return false;
		if (biodbLabel == null) {
			if (other.biodbLabel != null)
				return false;
		} else if (!biodbLabel.equals(other.biodbLabel))
			return false;
		if (biodbVersion == null) {
			if (other.biodbVersion != null)
				return false;
		} else if (!biodbVersion.equals(other.biodbVersion))
			return false;
		if (clusterNode == null) {
			if (other.clusterNode != null)
				return false;
		} else if (!clusterNode.equals(other.clusterNode))
			return false;
		if (compositionStat == null) {
			if (other.compositionStat != null)
				return false;
		} else if (!compositionStat.equals(other.compositionStat))
			return false;
		if (concatQueries != other.concatQueries)
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (dbGenCode != other.dbGenCode)
			return false;
		if (Double.doubleToLongBits(effectiveDb) != Double
				.doubleToLongBits(other.effectiveDb))
			return false;
		if (Double.doubleToLongBits(effectiveSpace) != Double
				.doubleToLongBits(other.effectiveSpace))
			return false;
		if (errorMessage == null) {
			if (other.errorMessage != null)
				return false;
		} else if (!errorMessage.equals(other.errorMessage))
			return false;
		if (Double.doubleToLongBits(evalue) != Double
				.doubleToLongBits(other.evalue))
			return false;
		if (extThreshold != other.extThreshold)
			return false;
		if (filter != other.filter)
			return false;
		if (gapAlign != other.gapAlign)
			return false;
		if (gapExtend != other.gapExtend)
			return false;
		if (gapOpen != other.gapOpen)
			return false;
		if (geneticCode != other.geneticCode)
			return false;
		if (giDefine != other.giDefine)
			return false;
		if (id != other.id)
			return false;
		if (jobId != other.jobId)
			return false;
		if (keepData == null) {
			if (other.keepData != null)
				return false;
		} else if (!keepData.equals(other.keepData))
			return false;
		if (keptHits != other.keptHits)
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (legacyEngine != other.legacyEngine)
			return false;
		if (localOptimum != other.localOptimum)
			return false;
		if (makePublic == null) {
			if (other.makePublic != null)
				return false;
		} else if (!makePublic.equals(other.makePublic))
			return false;
		if (matrix == null) {
			if (other.matrix != null)
				return false;
		} else if (!matrix.equals(other.matrix))
			return false;
		if (multiHitsWinSize != other.multiHitsWinSize)
			return false;
		if (nucMatch != other.nucMatch)
			return false;
		if (nucMismatch != other.nucMismatch)
			return false;
		if (numAlign != other.numAlign)
			return false;
		if (numDesc != other.numDesc)
			return false;
		if (numNeighbors != other.numNeighbors)
			return false;
		if (numProcessors != other.numProcessors)
			return false;
		if (programName == null) {
			if (other.programName != null)
				return false;
		} else if (!programName.equals(other.programName))
			return false;
		if (queryId == null) {
			if (other.queryId != null)
				return false;
		} else if (!queryId.equals(other.queryId))
			return false;
		if (querySeq == null) {
			if (other.querySeq != null)
				return false;
		} else if (!querySeq.equals(other.querySeq))
			return false;
		if (queryStrand != other.queryStrand)
			return false;
		if (rawFasta == null) {
			if (other.rawFasta != null)
				return false;
		} else if (!rawFasta.equals(other.rawFasta))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (resultRaw == null) {
			if (other.resultRaw != null)
				return false;
		} else if (!resultRaw.equals(other.resultRaw))
			return false;
		if (returnCode != other.returnCode)
			return false;
		if (timeFinished == null) {
			if (other.timeFinished != null)
				return false;
		} else if (!timeFinished.equals(other.timeFinished))
			return false;
		if (!Arrays.equals(timeProtocol, other.timeProtocol))
			return false;
		if (timeStarted == null) {
			if (other.timeStarted != null)
				return false;
		} else if (!timeStarted.equals(other.timeStarted))
			return false;
		if (timeSubmitted == null) {
			if (other.timeSubmitted != null)
				return false;
		} else if (!timeSubmitted.equals(other.timeSubmitted))
			return false;
		if (toolLabel == null) {
			if (other.toolLabel != null)
				return false;
		} else if (!toolLabel.equals(other.toolLabel))
			return false;
		if (toolVer == null) {
			if (other.toolVer != null)
				return false;
		} else if (!toolVer.equals(other.toolVer))
			return false;
		if (Double.doubleToLongBits(totalRunTime) != Double
				.doubleToLongBits(other.totalRunTime))
			return false;
		if (wordSize != other.wordSize)
			return false;
		if (xDropoff != other.xDropoff)
			return false;
		if (Double.doubleToLongBits(xDropoffGap) != Double
				.doubleToLongBits(other.xDropoffGap))
			return false;
		if (Double.doubleToLongBits(xDropoffUngap) != Double
				.doubleToLongBits(other.xDropoffUngap))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BlastJob [id=" + id + ", label=" + label + ", customer="
				+ customer + ", numNeighbors=" + numNeighbors + ", toolLabel="
				+ toolLabel + ", toolVer=" + toolVer + ", programName="
				+ programName + ", biodbLabel=" + biodbLabel
				+ ", biodbVersion=" + biodbVersion + ", rawFasta=" + rawFasta
				+ ", evalue=" + evalue + ", gapOpen=" + gapOpen
				+ ", gapExtend=" + gapExtend + ", xDropoff=" + xDropoff
				+ ", giDefine=" + giDefine + ", nucMismatch=" + nucMismatch
				+ ", nucMatch=" + nucMatch + ", numDesc=" + numDesc
				+ ", numAlign=" + numAlign + ", extThreshold=" + extThreshold
				+ ", gapAlign=" + gapAlign + ", geneticCode=" + geneticCode
				+ ", dbGenCode=" + dbGenCode + ", numProcessors="
				+ numProcessors + ", believeSeqFile=" + believeSeqFile
				+ ", matrix=" + matrix + ", wordSize=" + wordSize
				+ ", effectiveDb=" + effectiveDb + ", keptHits=" + keptHits
				+ ", effectiveSpace=" + effectiveSpace + ", queryStrand="
				+ queryStrand + ", xDropoffUngap=" + xDropoffUngap
				+ ", xDropoffGap=" + xDropoffGap + ", multiHitsWinSize="
				+ multiHitsWinSize + ", concatQueries=" + concatQueries
				+ ", legacyEngine=" + legacyEngine + ", compositionStat="
				+ compositionStat + ", localOptimum=" + localOptimum
				+ ", result=" + result + ", resultRaw=" + resultRaw
				+ ", filter=" + filter + ", timeSubmitted=" + timeSubmitted
				+ ", timeFinished=" + timeFinished + ", makePublic="
				+ makePublic + ", keepData=" + keepData + ", timeStarted="
				+ timeStarted + ", clusterNode=" + clusterNode + ", jobId="
				+ jobId + ", returnCode=" + returnCode + ", errorMessage="
				+ errorMessage + ", totalRunTime=" + totalRunTime
				+ ", timeProtocol=" + Arrays.toString(timeProtocol)
				+ ", queryId=" + queryId + ", querySeq=" + querySeq + "]";
	}

}
