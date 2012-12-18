package net.megx.megdb.logging.mappers;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import net.megx.model.logging.LoggedError;
import net.megx.security.auth.model.PaginatedResult;

public interface ErrorLogMapper {
	public void saveError(LoggedError error);
	public PaginatedResult<LoggedError> getErrors(
			@Param("start") int start, 
			@Param("size") int size);
	public void updateFeedback(
			@Param("id") String errorId,
			@Param("feedback") String feedback
			);
	public LoggedError getError(String id);
	
	public int countError(
			@Param("user")String user, 
			@Param("remoteIP")String remoteIP, 
			@Param("timestamp")Date afterTimestamp);
}
