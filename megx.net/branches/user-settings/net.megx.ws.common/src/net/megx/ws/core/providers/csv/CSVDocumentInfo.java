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

import java.util.List;

import net.megx.ws.core.providers.txt.ColumnNameFormat;

/**
 * CSV Document representation holding the data and the additional info needed
 * for serialization of CSV document
 * 
 * @author Pavle.Jonoski
 * 
 */
public class CSVDocumentInfo {

	private ColumnNameFormat format = ColumnNameFormat.NONE;

	/**
	 * Constructs new CSV Document representation object
	 * @param separator separator character
	 * @param quoteChar quote character
	 * @param lineEnd EOL 
	 * @param writeHeaderColumns boolean indicating if the header columns should be serialized as first row in the CSV document
	 * @param data {@link List} of POJO beans containing the actual data for the rows
	 * @param columnsMapping mappings for column-to-property in the exact order. This list should be sorted.
	 */
	public CSVDocumentInfo(char separator, char quoteChar, String lineEnd,
			boolean writeHeaderColumns, List<?> data,
			List<PropertyMapping> columnsMapping, ColumnNameFormat format) {
		super();
		this.separator = separator;
		this.quoteChar = quoteChar;
		this.lineEnd = lineEnd;
		this.writeHeaderColumns = writeHeaderColumns;
		this.data = data;
		this.columnsMapping = columnsMapping;
		this.format = format;
	}

	/**
	 * Default separator character
	 */
	public static final char DEFAULT_SEPARATOR_CHAR = ',';
	
	/**
	 * Default quote character
	 */
	public static final char DEFAULT_QUOTE_CHAR = '"';
	
	/**
	 * Default EOL - End Of Line
	 */
	public static final String DEFAULT_LINE_END = "\r\n";

	private char separator = DEFAULT_SEPARATOR_CHAR;
	private char quoteChar = DEFAULT_QUOTE_CHAR;
	private String lineEnd = DEFAULT_LINE_END;
	private boolean writeHeaderColumns = false;

	private List<?> data;
	private List<PropertyMapping> columnsMapping;

	public List<PropertyMapping> getColumnsMapping() {
		return columnsMapping;
	}

	public void setColumnsMapping(List<PropertyMapping> columnsMapping) {
		this.columnsMapping = columnsMapping;
	}

	public boolean isWriteHeaderColumns() {
		return writeHeaderColumns;
	}

	public void setWriteHeaderColumns(boolean writeHeaderColumns) {
		this.writeHeaderColumns = writeHeaderColumns;
	}

	public char getSeparator() {
		return separator;
	}

	public void setSeparator(char separator) {
		this.separator = separator;
	}

	public char getQuoteChar() {
		return quoteChar;
	}

	public void setQuoteChar(char quoteChar) {
		this.quoteChar = quoteChar;
	}

	public String getLineEnd() {
		return lineEnd;
	}

	public void setLineEnd(String lineEnd) {
		this.lineEnd = lineEnd;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public ColumnNameFormat getFormat() {
		return format;
	}

	public void setFormat(ColumnNameFormat format) {
		this.format = format;
	}
}
