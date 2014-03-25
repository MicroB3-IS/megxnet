package net.megx.megdb.blast.mappers;

import org.apache.ibatis.annotations.Param;

import net.megx.model.blast.BlastHits;
import net.megx.model.blast.BlastJob;

public interface BlastMapper {

	public void insertBlastJob(BlastJob job);

	public BlastHits getSubnetGraphml(@Param("jid") int jid,
			@Param("hitId") String hitId);

}
