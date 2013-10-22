package net.megx.security.logging.impl;

import java.util.Date;

import net.megx.megdb.logging.ErrorLogService;
import net.megx.model.logging.LoggedError;
import net.megx.security.auth.model.PaginatedResult;
import net.megx.security.crypto.KeySecretProvider;
import net.megx.security.logging.ErrorLog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ErrorLogger implements ErrorLog{
	private ErrorLogService service;
	private KeySecretProvider secretProvider;
	private int nonceLength = 18;
	
	private Log log = LogFactory.getLog(getClass());
	
	
	
	public int getNonceLength() {
		return nonceLength;
	}

	public ErrorLogger(ErrorLogService service, KeySecretProvider secretProvider, int nonceLength) {
		this.service = service;
		this.secretProvider = secretProvider;
		this.nonceLength = nonceLength;
	}

	public void setService(ErrorLogService service) {
		this.service = service;
	}

	@Override
	public LoggedError logError(int code, String message, String requestURI, String user, 
			String remoteIP, Throwable t, String feedback) {
		
		String javaType = t != null ? t.getClass().getName(): null;
		StringBuffer buffer = new StringBuffer();
		if(t != null)
			mapStackTrace(t, buffer);
		
		return logError(code, message, requestURI, user, remoteIP, javaType, buffer.toString(), feedback);
	}

	@Override
	public LoggedError logError(int code, String message, String requestURI, String user,
			String remoteIP, String javaType, String stackTrace, String feedback) {
		LoggedError error = new LoggedError();
		error.setId(secretProvider.getRandomSequence(getNonceLength()));
		error.setHttpCode(code);
		error.setMessage(message);
		error.setUser(user);
		error.setJavaType(javaType);
		error.setStackTrace(stackTrace);
		error.setFeedback(feedback);
		error.setRequestURI(requestURI);
		error.setTime(new Date());
		error.setRemoteIP(remoteIP);
		try {
			service.logError(error);
		} catch (Exception e) {
			log.error("Failed to log error",e);
			log.error("The server has encountered an error: " + error);
		}
		return error;
	}

	@Override
	public void addFeedback(String errorId, String feedback) throws Exception{
		service.addFeedback(errorId, feedback);
	}

	@Override
	public LoggedError retrieveError(String errorId) throws Exception {
		return service.getError(errorId);
	}

	@Override
	public PaginatedResult<LoggedError> retrieveErrors(int start, int pageSize)
			throws Exception {
		return service.getErrors(start, pageSize);
	}

	
	protected void mapStackTrace(Throwable t, StringBuffer buffer){
		buffer.append( String.format("%s%n",t) );
		do{
			StackTraceElement [] stackTrace = t.getStackTrace();
			for(StackTraceElement se: stackTrace){
				buffer.append(String.format("    at %s#%s [%s, %d]%n", 
						se.getClassName(),
						se.getMethodName(),
						se.getFileName(),
						se.getLineNumber()
						));
			}
			t = t.getCause();
			if(t != null){
				buffer.append("  Caused by: " + t + "%n");
			}
		}while(t != null);
	}

	@Override
	public int getErrorCount(String user, String remoteIP, Date afterTimestamp)
			throws Exception {
		return service.countErrors(remoteIP, remoteIP, afterTimestamp);
	}
	
}
