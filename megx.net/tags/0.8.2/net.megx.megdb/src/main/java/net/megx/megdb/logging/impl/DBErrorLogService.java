package net.megx.megdb.logging.impl;

import java.util.Date;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.logging.ErrorLogService;
import net.megx.megdb.logging.mappers.ErrorLogMapper;
import net.megx.model.logging.LoggedError;
import net.megx.security.auth.model.PaginatedResult;

public class DBErrorLogService extends BaseMegdbService implements ErrorLogService{

	@Override
	public void logError(final LoggedError error)  throws Exception{
		doInTransaction(new BaseMegdbService.DBTask<ErrorLogMapper, Object>() {

			@Override
			public Object execute(ErrorLogMapper mapper) throws Exception {
				mapper.saveError(error);
				return null;
			}
			
		}, ErrorLogMapper.class);
	}

	@Override
	public void addFeedback(final String errorId, final String feedback)  throws Exception{
		doInTransaction(new BaseMegdbService.DBTask<ErrorLogMapper, Object>() {

			@Override
			public Object execute(ErrorLogMapper mapper) throws Exception {
				mapper.updateFeedback(errorId, feedback);
				return null;
			}
			
		}, ErrorLogMapper.class);
	}

	@Override
	public LoggedError getError(final String errorId)  throws Exception{
		return doInSession(new BaseMegdbService.DBTask<ErrorLogMapper, LoggedError>() {

			@Override
			public LoggedError execute(ErrorLogMapper mapper) throws Exception {
				return mapper.getError(errorId);
			}
		}, ErrorLogMapper.class);
	}

	@Override
	public PaginatedResult<LoggedError> getErrors(final int start, final int size)  throws Exception{
		return doInSession(new BaseMegdbService.DBTask<ErrorLogMapper, PaginatedResult<LoggedError>>() {

			@Override
			public PaginatedResult<LoggedError> execute(ErrorLogMapper mapper) throws Exception {
				return mapper.getErrors(start, size);
			}
			
		}, ErrorLogMapper.class);
	}

	@Override
	public int countErrors(final String user, final String ip, final Date timestamp)
			throws Exception {
		return doInSession(new BaseMegdbService.DBTask<ErrorLogMapper, Integer>() {

			@Override
			public Integer execute(ErrorLogMapper mapper) throws Exception {
				return mapper.countError(user, ip, timestamp);
			}
			
		}, ErrorLogMapper.class);
	}

}
