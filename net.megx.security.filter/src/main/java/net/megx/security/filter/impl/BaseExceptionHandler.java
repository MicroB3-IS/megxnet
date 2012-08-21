package net.megx.security.filter.impl;

import javax.ws.rs.core.Response.Status;

import net.megx.security.filter.ExceptionHandler;

import org.json.JSONObject;

public abstract class BaseExceptionHandler implements ExceptionHandler{
	protected JSONObject config;
	
	public void init(JSONObject handlerConfig){
		this.config = handlerConfig;
		init();
	}
	
	public void init(){}
	
	
	protected String fromHttpCode(int httpCode){
		Status status = Status.fromStatusCode(httpCode);
		if(status != null){
			return status.getReasonPhrase();
		}
		return null;
	}
}
