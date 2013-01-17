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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import net.megx.ws.core.providers.csv.CSVUnmarshaller;

public class SimpleCSVUnmarshaller implements CSVUnmarshaller<Object>{

	private String [] columnsMapping;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object unmarshall(Class type, InputStream stream)
			throws Exception {
		ColumnPositionMappingStrategy<?> strategy = new ColumnPositionMappingStrategy();
		strategy.setType(type);
		strategy.setColumnMapping(columnsMapping);
		CsvToBean csvToBean = new CsvToBean();
		List<Object> retVal = csvToBean.parse(strategy, new CSVReader(new InputStreamReader(stream)));
		return retVal;
	}

	public SimpleCSVUnmarshaller(String [] columnsMapping) {
		this.columnsMapping = columnsMapping;
	}
	
}
