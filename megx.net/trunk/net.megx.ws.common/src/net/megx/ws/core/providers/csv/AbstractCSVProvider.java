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


package net.megx.ws.core.providers.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import net.megx.ws.core.providers.csv.CSVMarshaller;
import net.megx.ws.core.providers.csv.CSVUnmarshaller;

/**
 * Abstract provider class that implements marshalling/unmarshalling of CVS documents.
 * @see also {@link CSVMarshaller} and {@link CSVUnmarshaller} for details on marshall/unmarshall 
 * @author Pavle Jonoski
 *
 */
public abstract class AbstractCSVProvider implements 
			MessageBodyWriter<Object>, MessageBodyReader<Object>{

	
	
	public long getSize(Object t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType) {
		if(type.equals(String.class) ||
				StreamingOutput.class.isAssignableFrom(type)){
			
			// if the Entity is plain string or a stream - just pass it through.
			return false;
		}
		return true;
	}

	
	public void writeTo(Object t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		
		try {
			@SuppressWarnings("unchecked")
			CSVMarshaller<Object> marshaller = (CSVMarshaller<Object>)getMarshaller();
			CSVDocumentInfo documentInfo = getCSVOutputDocument(t, type, genericType, annotations, mediaType, httpHeaders);
			marshaller.marshall(documentInfo, type, entityStream);
		}catch (IOException e) {
			throw e;
		}catch (WebApplicationException e) {
			throw e;
		}catch (Exception e) {
			throw new WebApplicationException(e); 
		}
	}

	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotatios,
			MediaType mediaType) {
		return true;
	}

	@SuppressWarnings("unchecked")
	public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
			InputStream entityStream) throws IOException, WebApplicationException {
		Object result = null;
		
		try {
			CSVUnmarshaller<Object> unmarshaller = (CSVUnmarshaller<Object>)getUnmarshaller(type, annotations, genericType);
			result = unmarshaller.unmarshall(type, entityStream);
		}catch (IOException e) {
			throw e;
		}catch (Exception e) {
			throw new WebApplicationException(e);
		}
		return result;
	}

	protected abstract  CSVMarshaller<?> getMarshaller() throws Exception;
	protected abstract  CSVDocumentInfo getCSVOutputDocument(Object t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders) throws Exception;
	protected abstract  CSVUnmarshaller<?> getUnmarshaller(Class<?> forType, Annotation [] annotations, Type genericType) throws Exception;
}
