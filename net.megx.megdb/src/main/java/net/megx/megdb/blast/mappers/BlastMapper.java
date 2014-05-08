package net.megx.megdb.blast.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.megx.model.blast.BlastHits;
import net.megx.model.blast.BlastHitsDb;
import net.megx.model.blast.BlastHitsNeighbours;
import net.megx.model.blast.BlastJob;
import net.megx.model.blast.MatchingSequences;

public interface BlastMapper {

	public void insertBlastJob(BlastJob job);

	public BlastHits getSubnetGraphml(@Param("jid") int jid,
			@Param("hitId") String hitId);

	public BlastJob getBlastJobDetails(@Param("blastJobId") int blastJobId);

	public BlastJob getSuccesfulBlastJob(@Param("id") int id);

	public List<BlastHitsNeighbours> getNeighbours(@Param("jid") int jid,
			@Param("hitId") String hitId);

	public List<BlastHitsDb> getDatabases();

	public BlastJob getResultRaw(@Param("id") int id);

	public BlastJob getGeographicRaw(@Param("id") int id);

	public List<MatchingSequences> getMatchingSequences();

}
