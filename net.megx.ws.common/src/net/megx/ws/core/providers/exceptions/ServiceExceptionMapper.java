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

import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;

/**
 * 
 * @author Pavle.Jonoski
 *
 */

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<Exception>{
	
	private Gson gson = new Gson();
	
	protected static Log log = LogFactory.getLog(ServiceExceptionMapper.class);
	
	//@Context
	//private HttpServletResponse servletResponse;
	
	public Response toResponse(Exception e) {
		Result<Map<String, Object>> exception = 
				BaseRestService.exceptionToResult(e, false);
		if(e  instanceof WebApplicationException){
			return Response.
					status( ((WebApplicationException)e).getResponse().getStatus()).
					entity(gson.toJson(exception)).
					type(MediaType.APPLICATION_JSON).
					build();
		}else{
			return Response.
					status(500).
					entity(gson.toJson(exception)).
					type(MediaType.APPLICATION_JSON).
					build();
		}
	}
	
}
