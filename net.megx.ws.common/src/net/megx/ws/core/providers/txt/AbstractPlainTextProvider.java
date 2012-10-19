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


package net.megx.ws.core.providers.txt;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import net.megx.ws.core.utils.ReflectionUtils;

public abstract class AbstractPlainTextProvider implements MessageBodyWriter<Object>{

	
	public long getSize(Object t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType) {
		return true;
	}

	@SuppressWarnings("unchecked")
	public void writeTo(Object t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		Writer writer = new OutputStreamWriter(entityStream);
		if(ReflectionUtils.isPrimitive(type)){
			writer.write(t.toString());
			writer.flush();
			return;
		}
		
		if(ReflectionUtils.isCollection(type)){
			Class<?> enclosedType = ReflectionUtils.getEnclosedType(type, genericType);
			Iterable<Object> it = (Iterable<Object>)t;
			for(Object r: it){
				BeanInfo beanInfo;
				try {
					beanInfo = Introspector.getBeanInfo(r.getClass());
					formatBean(r, beanInfo.getPropertyDescriptors(), annotations, enclosedType, genericType, writer);
				} catch (IntrospectionException e) {
					throw new WebApplicationException(e);
				}
				
			}
		}else{
			BeanInfo beanInfo = null;;
			try {
				beanInfo = Introspector.getBeanInfo(type);
				formatBean(t, beanInfo.getPropertyDescriptors(), annotations, type, genericType, writer);
			} catch (IntrospectionException e) {
				throw new WebApplicationException(e);
			}
			
		}
		
		writer.flush();
	}
	
	@SuppressWarnings("unchecked")
	protected void formatBean(Object t, PropertyDescriptor [] properties, Annotation [] annotations, Class<?> type, Type genericType, Writer writer) throws IOException{
		if(ReflectionUtils.isCollection(type)){
			if(type.isArray()){
				writer.write(Arrays.toString((Object[]) t));
			}else{
				Collection<Object> collection = (Collection<Object>)t;
				writer.write(Arrays.toString((Object[]) collection.toArray(new Object[collection.size()])));
			}
		}else
			writer.write(t.toString());
		writer.flush();
	}
	
	@Provider
	@Produces(MediaType.TEXT_PLAIN)
	public static class SimplePlainTextProvider extends AbstractPlainTextProvider{}
	
}
