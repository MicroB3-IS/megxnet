/*
 * Copyright 2011 Max Planck Institute for Marine Microbiology 

 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */


package net.megx.ws.core.providers.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ServiceException extends WebApplicationException{

	private static final long serialVersionUID = 7250344572758191446L;
	
	private String intendedMediaType;
	private ExceptionInfo exceptionInfo;
	
	
	public ServiceException(Throwable t, String message, String description, int status) {
		super(t, status);
		this.exceptionInfo = new ExceptionInfo(status, message, description);
	}
	public ServiceException(Throwable t, String message, String description) {
		this(t, message, description, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
	}
	public ServiceException(Throwable t, String message, String description, Response.Status status) {
		this(t, message, description, status.getStatusCode());
	}
	
	public ServiceException(Throwable t, String message, String description, int status, String intendedMediaType) {
		this(t, message, description, status);
		this.intendedMediaType = intendedMediaType;
	}
	public ServiceException(Throwable t, String message, String description, String intendedMediaType) {
		this(t, message, description, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		this.intendedMediaType = intendedMediaType;
	}
	public ServiceException(Throwable t, String message, String description, Response.Status status, String intendedMediaType) {
		this(t, message, description, status.getStatusCode());
		this.intendedMediaType = intendedMediaType;
	}
	
	
	public String getIntendedMediaType(){
		return intendedMediaType;
	}
	
	public ExceptionInfo getExceptionInfo(){
		return exceptionInfo;
	}
}
