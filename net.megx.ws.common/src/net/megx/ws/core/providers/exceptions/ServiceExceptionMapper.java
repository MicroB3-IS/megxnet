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

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Pavle.Jonoski
 *
 */

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<WebApplicationException>{
	
	@Context
	private UriInfo uriInfo;
	
	//@Context
	//private HttpHeaders headers;
	
	@Context
	private HttpServletRequest request;
	
	
	
	
	protected static Log log = LogFactory.getLog(ServiceExceptionMapper.class);
	
	
	private static Properties properties;
	private static final String STATUS_CODES_MAPPINGS_LOCATION = "com/iw/megx/ws/resources/httpStatusCodes.properties";
	static{
		properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(STATUS_CODES_MAPPINGS_LOCATION));
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	private static String getDescriptionForStatusCode(int status){
		return properties.getProperty(""+status);
	}
	
	public Response toResponse(WebApplicationException e) {
		String mediaType = null;//getErrorResponseMediaType();
		String intendedMediaType = (String)request.getAttribute("IntendedMediaType");
		if(e instanceof ServiceException){
			ServiceException se = (ServiceException)e;
			if(intendedMediaType != null){
				mediaType = intendedMediaType;
			}else if(se.getIntendedMediaType()!=null){
				mediaType = se.getIntendedMediaType();
			}else{
				mediaType = getErrorResponseMediaType();
			}
			log.error("Error: (MediaType=" + mediaType + ") ",e);
			return Response.status(e.getResponse().getStatus()).entity(se.getExceptionInfo()).type(mediaType).build();
		}else{
			return Response.fromResponse(e.getResponse()).entity(mapException(e)).type(intendedMediaType != null ? intendedMediaType : getErrorResponseMediaType()).build();
		}
	}

	
	private String getErrorResponseMediaType(){
		String mimeType = new MimetypesFileTypeMap().getContentType(uriInfo.getPath());
		if(!mimeType.equals("application/octet-stream")){
			return mimeType;
		}
		/* FIXME: take into consideration client preferred media-types - HTTP Negotiation algoritm
		// test for json
		List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
		if(acceptableMediaTypes.contains(MediaType.APPLICATION_JSON)){
			return MediaType.APPLICATION_JSON;
		}
		
		// test for XML
		if(acceptableMediaTypes.contains(MediaType.APPLICATION_XML) || acceptableMediaTypes.contains(MediaType.TEXT_XML)){
			return MediaType.TEXT_XML;
		}
		*/
		return MediaType.TEXT_PLAIN;
	}
	
	
	private ExceptionInfo mapException(WebApplicationException e) {
		int status = e.getResponse().getStatus();
		Response.Status statusEn = Response.Status.fromStatusCode(status);
		String message = statusEn.getReasonPhrase();
		String description = null;
		
		description = getDescriptionForStatusCode(status);
		if(description != null){
			MessageFormat format = new MessageFormat(description);
			String path = uriInfo.getPath();
			String excMessage = e.getMessage();
			String cause = null;
			if(e.getCause() != null){
				cause = e.getCause().getMessage();
			}
			
			description = format.format(new Object[]{status,path, excMessage, cause});
		}
		return new ExceptionInfo(status, message, description);
	}
}
