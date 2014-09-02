package net.megx.megdb.logging;

import java.util.Date;

import net.megx.model.logging.LoggedError;
import net.megx.security.auth.model.PaginatedResult;

public interface ErrorLogService {
	public void logError(LoggedError error) throws Exception;
	public void addFeedback(String errorId, String feedback) throws Exception;
	public LoggedError getError(String errorId) throws Exception;
	public PaginatedResult<LoggedError> getErrors(int start, int size) throws Exception;
	public int countErrors(String user, String ip, Date timestamp) throws Exception;
}
