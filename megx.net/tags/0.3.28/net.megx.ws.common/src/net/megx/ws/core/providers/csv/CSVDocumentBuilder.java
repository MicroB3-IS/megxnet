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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.megx.ws.core.providers.csv.annotations.CSVColumn;

/**
 * CSV document representation builder. Exposes methods for building
 * CSVDocumentInfo. The info about the CSV document is built according to the
 * bean type which holds the data and any annotation added to that bean.
 * 
 * @see CSVColumn
 * @author Pavle.Jonoski
 * 
 */
public class CSVDocumentBuilder {

	private ConcurrentMap<Class<?>, _CachedDescriptors> cache = new ConcurrentHashMap<Class<?>, _CachedDescriptors>();

	// Thread-safe
	private _CachedDescriptors getDefaultMapping(Class<?> type)
			throws IntrospectionException {
		_CachedDescriptors mapping = cache.get(type);
		if (mapping == null) {
			_CachedDescriptors newMappings = generateMappingForType(type);
			mapping = cache.putIfAbsent(type, newMappings);
			if (mapping == null) {
				mapping = newMappings;
			}
		}
		return mapping;
	}

	/**
	 * Returns hashed-map of the mapping: property to csv column. The mapping is
	 * retrieved from cache (or created if non-existent). Use the hashed
	 * structure of the mapping if you need random access to the
	 * columns-to-properties mapping
	 * 
	 * @param type
	 *            the Type of the bean that holds the data for single row in the
	 *            CSV document (POJO)
	 * @return hashed map representation of the mappings
	 * @throws IntrospectionException
	 */
	public Map<String, PropertyMapping> getDefaultHashedMapping(Class<?> type)
			throws IntrospectionException {
		_CachedDescriptors cachedDescriptors = getDefaultMapping(type);
		return cachedDescriptors.hashed;
	}

	/**
	 * Retrieves the mappings as sorted list. The sorting order is according to
	 * the column index (as annotated). Use this structure for sequential access
	 * to the mappings and also to get the proper order of the CSV columns.
	 * 
	 * @param type
	 *            the Type of the bean that holds the data for single row in the
	 *            CSV document (POJO)
	 * @return sorted list of mappings
	 * @throws IntrospectionException
	 */
	public List<PropertyMapping> getDefaultSortedMapping(Class<?> type)
			throws IntrospectionException {
		_CachedDescriptors cachedDescriptors = getDefaultMapping(type);
		return cachedDescriptors.sorted;
	}

	private _CachedDescriptors generateMappingForType(Class<?> type)
			throws IntrospectionException {
		BeanInfo info = Introspector.getBeanInfo(type);
		List<PropertyMapping> mappings = new LinkedList<PropertyMapping>();
		List<IndexColumn> indexed = new LinkedList<CSVDocumentBuilder.IndexColumn>();
		Map<String, PropertyDescriptor> hashedDescriptors = new HashMap<String, PropertyDescriptor>();

		PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
		for (PropertyDescriptor descriptor : descriptors) {
			hashedDescriptors.put(descriptor.getName(), descriptor);
		}

		Field[] fields = type.getDeclaredFields();

		for (Field field : fields) {
			CSVColumn columnAnnot = field.getAnnotation(CSVColumn.class);
			if (columnAnnot != null) {
				String columnName = columnAnnot.name();
				int index = columnAnnot.index();
				if (columnName == null)
					columnName = field.getName();
				if (index < 0)
					index = Integer.MAX_VALUE;
				indexed.add(new IndexColumn(columnName, field.getName(), index));
			} else {
				indexed.add(new IndexColumn(field.getName(), field.getName(),
						Integer.MAX_VALUE));
			}
		}

		Collections.sort(indexed, comparator);

		Map<String, PropertyMapping> hashed = new HashMap<String, PropertyMapping>();
		for (IndexColumn indexColumn : indexed) {
			PropertyDescriptor descriptor = hashedDescriptors
					.get(indexColumn.fieldName);
			if (descriptor != null) {
				PropertyMapping propertyMapping = new PropertyMapping(
						indexColumn.columnName, descriptor);
				mappings.add(propertyMapping);
				hashed.put(indexColumn.columnName, propertyMapping);
			}
		}

		return new _CachedDescriptors(mappings, hashed);
	}

	/**
	 * Creates new CSV Document representation, ready for serialization
	 * 
	 * @param writeHeaderColumns
	 *            boolean indicating if the header columns should be written to
	 *            the document when serializing
	 * @param separator
	 *            separator character, defaults to ','
	 * @param quoteChar
	 *            quote characte, defaults to '"'
	 * @param lineEnd
	 *            EOL (End Of Line) - defaults to "\r\n"
	 * @param beanType
	 *            the Type of the bean holding the data for a single row in the
	 *            CSV document (POJO)
	 * @param columns
	 *            array of column names (the mapped names of the columns if
	 *            annotated, NOT the raw field name - of course the mapped name
	 *            and the field/property name will be the same if the field is
	 *            not annotated)
	 * @param data {@link List} of beans holding the actual data
	 * @return {@link CSVDocumentInfo} representing the real CSV document and additional info needed for serialization
	 * @throws IntrospectionException
	 */
	public CSVDocumentInfo createDocument(boolean writeHeaderColumns,
			char separator, char quoteChar, String lineEnd, Class<?> beanType,
			String[] columns, List<?> data) throws IntrospectionException {
		return createDocument(writeHeaderColumns, separator, quoteChar, lineEnd, beanType, columns, data, ColumnNameFormat.NONE);
	}
	/**
	 * Creates new CSV Document representation, ready for serialization
	 * 
	 * @param writeHeaderColumns
	 *            boolean indicating if the header columns should be written to
	 *            the document when serializing
	 * @param separator
	 *            separator character, defaults to ','
	 * @param quoteChar
	 *            quote characte, defaults to '"'
	 * @param lineEnd
	 *            EOL (End Of Line) - defaults to "\r\n"
	 * @param beanType
	 *            the Type of the bean holding the data for a single row in the
	 *            CSV document (POJO)
	 * @param columns
	 *            array of column names (the mapped names of the columns if
	 *            annotated, NOT the raw field name - of course the mapped name
	 *            and the field/property name will be the same if the field is
	 *            not annotated)
	 * @param data any type of bean representing the data for this CSV document
	 * @param format Column name format. See {@link ColumnNameFormat} for more details.
	 * @return {@link CSVDocumentInfo} representing the real CSV document and additional info needed for serialization
	 * @throws IntrospectionException
	 */
	@SuppressWarnings("unchecked")
	public CSVDocumentInfo createDocument(boolean writeHeaderColumns,
			char separator, char quoteChar, String lineEnd, Class<?> beanType,
			String[] columns, Object data, ColumnNameFormat format) throws IntrospectionException {
		List<Object> ld = null;
		if (data.getClass().isArray()) {
			ld = Arrays.asList(data);
		} else if (List.class.isAssignableFrom(data.getClass())) {
			ld = (List<Object>) data;
		} else if (Set.class.isAssignableFrom(data.getClass())) {
			Set<?> sd = (Set<?>) data;
			ld = new ArrayList<Object>(sd.size());
			ld.addAll(sd);
		} else {
			ld = new ArrayList<Object>(1);
			ld.add(data);
		}
		return createDocument(writeHeaderColumns, separator, quoteChar,
				lineEnd, beanType, columns, ld, format);
	}
	
	
	/**
	 * Creates new CSV Document representation, ready for serialization
	 * 
	 * @param writeHeaderColumns
	 *            boolean indicating if the header columns should be written to
	 *            the document when serializing
	 * @param separator
	 *            separator character, defaults to ','
	 * @param quoteChar
	 *            quote characte, defaults to '"'
	 * @param lineEnd
	 *            EOL (End Of Line) - defaults to "\r\n"
	 * @param beanType
	 *            the Type of the bean holding the data for a single row in the
	 *            CSV document (POJO)
	 * @param columns
	 *            array of column names (the mapped names of the columns if
	 *            annotated, NOT the raw field name - of course the mapped name
	 *            and the field/property name will be the same if the field is
	 *            not annotated)
	 * @param data {@link List} of beans holding the actual data
	 * @return {@link CSVDocumentInfo} representing the real CSV document and additional info needed for serialization
	 * @throws IntrospectionException
	 */
	public CSVDocumentInfo createDocument(boolean writeHeaderColumns,
			char separator, char quoteChar, String lineEnd, Class<?> beanType,
			String[] columns, List<?> data, ColumnNameFormat format) throws IntrospectionException {
		boolean useAllColumns = true;
		if (columns != null && columns.length > 0)
			useAllColumns = false;

		List<PropertyMapping> mappings = new ArrayList<PropertyMapping>();

		if (useAllColumns) {
			List<PropertyMapping> cachedMappings = getDefaultSortedMapping(beanType);
			mappings.addAll(cachedMappings);
		} else {
			Map<String, PropertyMapping> hashedMappings = getDefaultHashedMapping(beanType);
			for (String column : columns) {
				PropertyMapping mapping = hashedMappings.get(column);
				if (mapping != null) {
					mappings.add(mapping);
				}
			}
		}

		return new CSVDocumentInfo(separator, quoteChar, lineEnd,
				writeHeaderColumns, data, mappings, format);
	}
	
	
	/**
	 * Creates new CSV Document representation, ready for serialization
	 * 
	 * @param writeHeaderColumns
	 *            boolean indicating if the header columns should be written to
	 *            the document when serializing
	 * @param separator
	 *            separator character, defaults to ','
	 * @param quoteChar
	 *            quote characte, defaults to '"'
	 * @param lineEnd
	 *            EOL (End Of Line) - defaults to "\r\n"
	 * @param beanType
	 *            the Type of the bean holding the data for a single row in the
	 *            CSV document (POJO)
	 * @param columns
	 *            array of column names (the mapped names of the columns if
	 *            annotated, NOT the raw field name - of course the mapped name
	 *            and the field/property name will be the same if the field is
	 *            not annotated)
	 * @param data any type of bean representing the data for this CSV document
	 * @return {@link CSVDocumentInfo} representing the real CSV document and additional info needed for serialization
	 * @throws IntrospectionException
	 */
	@SuppressWarnings("unchecked")
	public CSVDocumentInfo createDocument(boolean writeHeaderColumns,
			char separator, char quoteChar, String lineEnd, Class<?> beanType,
			String[] columns, Object data) throws IntrospectionException {
		List<Object> ld = null;
		if (data.getClass().isArray()) {
			ld = Arrays.asList(data);
		} else if (List.class.isAssignableFrom(data.getClass())) {
			ld = (List<Object>) data;
		} else if (Set.class.isAssignableFrom(data.getClass())) {
			Set<?> sd = (Set<?>) data;
			ld = new ArrayList<Object>(sd.size());
			ld.addAll(sd);
		} else {
			ld = new ArrayList<Object>(1);
			ld.add(data);
		}
		return createDocument(writeHeaderColumns, separator, quoteChar,
				lineEnd, beanType, columns, ld, ColumnNameFormat.NONE);
	}

	private class IndexColumn {
		public String columnName;
		public String fieldName;
		public int index;

		public IndexColumn(String columnName, String fieldName, int index) {
			super();
			this.columnName = columnName;
			this.fieldName = fieldName;
			this.index = index;
		}

	}

	private class _CachedDescriptors {
		public List<PropertyMapping> sorted;
		public Map<String, PropertyMapping> hashed;

		public _CachedDescriptors(List<PropertyMapping> sorted,
				Map<String, PropertyMapping> hashed) {
			super();
			this.sorted = sorted;
			this.hashed = hashed;
		}
	}

	private static class IndexColumnComparator implements
			Comparator<IndexColumn> {

		public int compare(IndexColumn o1, IndexColumn o2) {
			return o1.index - o2.index;
		}
	}

	private static IndexColumnComparator comparator = new CSVDocumentBuilder.IndexColumnComparator();

	private static CSVDocumentBuilder documentBuilder = new CSVDocumentBuilder();

	/**
	 * 
	 * @return static instance of the {@link CSVDocumentBuilder}
	 */
	public static CSVDocumentBuilder getInstance() {
		return documentBuilder;
	}
}
