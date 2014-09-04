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


package net.megx.ws.core.utils;

import java.util.Set;
import java.util.LinkedHashSet;

/**
 * Class containing utility methods designed to help constructing SQL statements.
 * @author Mile.Stefanovski
 *
 */
public class SqlUtils {
	
	/**
	 * Utility method used for SELECT statements where column names are requested by the user, 
	 * this method protects the DB from SQL injections and user query errors like invalid column names etc... <br />
	 * In order to escape duplicate entries and to preserve order, this method internally uses {@link LinkedHashSet} implementation of the {@link Set} interface.
	 * @param allowable comma separated {@link String} with column names of the Table 
	 * @param proposed comma separated {@link String} with requested column names of the Table
	 * @return Comma separated {@link String} in the same order as <code>proposed</code> but cleared out of values that are not in <code>allowable</code> 
	 */
	public static String getAllowedColumns(String allowable, String proposed){
		if(allowable != null && allowable != "" && proposed != null && proposed != ""){
			Set<String> prop = stringToSet(proposed, ",");
			Set<String> all = stringToSet(allowable, ",");
			prop.retainAll(all);
			if(!prop.isEmpty()){
				StringBuffer sb = new StringBuffer();
				for(String str : prop){
					sb.append(str).append(",");
				}
				return sb.substring(0, sb.lastIndexOf(","));
			}
		}
		return "";
	}
	
	private static Set<String> stringToSet(String str, String regex){
		Set<String> ret = new LinkedHashSet<String>();
		if(str != null && str != "" && regex != null){
			for (String p : str.split(regex)) {
				if(p.trim() != ""){
					ret.add(p);
				}
			}
		}
		return ret;
	}
}
