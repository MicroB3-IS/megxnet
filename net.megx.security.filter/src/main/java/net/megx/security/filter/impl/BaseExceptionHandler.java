package net.megx.security.filter.impl;

import net.megx.security.filter.ExceptionHandler;

import org.json.JSONObject;

public abstract class BaseExceptionHandler implements ExceptionHandler{
	protected JSONObject config;
	
	public void init(JSONObject handlerConfig){
		this.config = handlerConfig;
		init();
	}
	
	public void init(){}
}
