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

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import net.megx.ws.core.CustomMediaType;
import net.megx.ws.core.providers.csv.annotations.CSVDocument;
import net.megx.ws.core.utils.ReflectionUtils;

public class SimpleCSVProvider extends AbstractCSVProvider{

	@Override
	protected CSVMarshaller<?> getMarshaller() throws Exception {
		return new SimpleCSVMarshaller();
	}

	@Override
	protected CSVDocumentInfo getCSVOutputDocument(Object t, Class<?> type,
			Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders) throws Exception {
		if(t instanceof CSVDocumentInfo){
			return (CSVDocumentInfo)t;
		}else{
			boolean writeHeaderColumns = false;
			char separator = CSVDocumentInfo.DEFAULT_SEPARATOR_CHAR;
			char quoteChar = CSVDocumentInfo.DEFAULT_QUOTE_CHAR;
			String lineEnd = CSVDocumentInfo.DEFAULT_LINE_END;
			String [] columns = null;
			CSVDocument documentAnnot = null;
			
			
			documentAnnot = getDocumentAnnotation(type, annotations, genericType);
			String format = null;
			if(documentAnnot != null){
				separator = documentAnnot.separator();
				lineEnd = documentAnnot.newLineSeparator();
				columns = documentAnnot.columnsOrder();
				writeHeaderColumns = documentAnnot.preserveHeaderColumns();
				format = documentAnnot.columnNameFormat();
			}
			
			return CSVDocumentBuilder.getInstance().createDocument(
					writeHeaderColumns, separator, quoteChar, lineEnd,
					ReflectionUtils.getEnclosedType(type, genericType),
					columns, t, format);
		}
		
	}

	@Override
	protected CSVUnmarshaller<?> getUnmarshaller(Class<?> forType,
			Annotation[] annotations, Type genericType) throws Exception {
		CSVDocument documentAnnot = getDocumentAnnotation(forType, annotations, genericType);
		String [] columns = null;
		if(documentAnnot != null){
			columns = documentAnnot.columnsOrder();
		}else{
			List<PropertyMapping> beanDefaultMapping = CSVDocumentBuilder.getInstance().getDefaultSortedMapping(forType);
			columns = new String[beanDefaultMapping.size()];
			int i = 0;
			for(PropertyMapping m: beanDefaultMapping){
				columns[i] = m.getDescriptor().getName();
				i++;
			}
		}
		return new SimpleCSVUnmarshaller(columns);
	}
	
	protected CSVDocument getDocumentAnnotation(Class<?> type,
			Annotation[] annotations, Type genericType) {
		CSVDocument documentAnnot = null;
		type = ReflectionUtils.getEnclosedType(type, genericType);
		for(Annotation annotation: annotations){
			if(annotation instanceof CSVDocument){
				documentAnnot = (CSVDocument)annotation;
				break;
			}
		}
		
		if(documentAnnot == null){
			documentAnnot = type.getAnnotation(CSVDocument.class);
		}
		
		return documentAnnot;
	}
	
	@Provider
	@Produces(CustomMediaType.APPLICATION_CSV)
	@Consumes(CustomMediaType.APPLICATION_CSV)
	public static class SimpleCSVProvider_App_CSV extends SimpleCSVProvider{ }
	
	@Provider
	@Produces(CustomMediaType.TEXT_CSV)
	@Consumes(CustomMediaType.TEXT_CSV)
	public static class SimpleCSVProvider_Text_CSV extends SimpleCSVProvider{ }
}
