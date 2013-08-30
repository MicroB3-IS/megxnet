package net.megx.security.logging;

import java.util.Date;

import net.megx.model.logging.LoggedError;
import net.megx.security.auth.model.PaginatedResult;

public interface ErrorLog {
	public LoggedError logError(int code, String message, String requestURI,
			String user, String remoteIP, Throwable t, String feedback);

	public LoggedError logError(int code, String message, String requestURI,
			String user, String javaType, String remoteIP, String stackTrace, String feedback);

	public void addFeedback(String errorId, String feedback) throws Exception;

	public LoggedError retrieveError(String errorId) throws Exception;

	public PaginatedResult<LoggedError> retrieveErrors(int start, int pageSize)
			throws Exception;
	
	public int getErrorCount(String user, String remoteIP, Date afterTimestamp) throws Exception;
}
