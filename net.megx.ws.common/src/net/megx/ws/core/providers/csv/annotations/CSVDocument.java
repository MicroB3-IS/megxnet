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


package net.megx.ws.core.providers.csv.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Comma Separated Values document configuration.
 * Annotates POJO class or method that is used to generate CVS document.
 * 
 * @author Pavle Jonoski
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface CSVDocument {
	/**
	 * 
	 * @return boolean representing whether the column names are preserved in the CVS document
	 */
	public boolean preserveHeaderColumns() default true;
	
	/**
	 * Format property to column name,
	 * camelCase, will convert camelCase to human readable text
	 * eg. properyName -> Property Name
	 * @return
	 */
	public String columnNameFormat() default "[default]";
	
	/**
	 * 
	 * @return 
	 */
	public char separator() default  ',';
	public String newLineSeparator() default "\n";
	public String [] columnsOrder() default {};
}
