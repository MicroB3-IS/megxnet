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


package net.megx.ws.core.ext;

//import java.util.List;

//import com.sun.jersey.api.wadl.config.WadlGeneratorConfig;
//import com.sun.jersey.api.wadl.config.WadlGeneratorDescription;
//import com.sun.jersey.server.wadl.generators.WadlGeneratorGrammarsSupport;

/**
 * Jersey-specific extension on WADL Generator to support adding grammar to 
 * the generated WADL document by Jersey.
 * @author pavle
 *
 */
//public class ExtendedWadlGeneratorConfig extends WadlGeneratorConfig{
//
//	public static String APPLICATION_GRAMMARS_FILE = "application-grammars.xml";
//	public static String RESOURCE_DOC_FILE = "resourcedoc.xml";
//	
//	@Override
//	public List<WadlGeneratorDescription> configure() {
//		
//		return generator(WadlGeneratorGrammarsSupport.class)
//				.prop("grammarsStream", APPLICATION_GRAMMARS_FILE)
//				//.generator(WadlGeneratorResourceDocSupport.class)
//				//.prop("resourceDocStream", RESOURCE_DOC_FILE)
//				.descriptions();
//	}
//
//}
