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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReflectionUtils {
	
	private static Set<Class<?>> primitiveTypes = new HashSet<Class<?>>();
	static{
		primitiveTypes.add(String.class);
		primitiveTypes.add(Integer.class);
		primitiveTypes.add(Long.class);
		primitiveTypes.add(Double.class);
		primitiveTypes.add(Float.class);
		primitiveTypes.add(Boolean.class);
		primitiveTypes.add(Character.class);
		primitiveTypes.add(Short.class);
		primitiveTypes.add(Byte.class);
		primitiveTypes.add(Void.class);
	}
	
	public static Class<?> getEnclosedType(Class<?> type, Type genericType){
		if(List.class.isAssignableFrom(type) || Set.class.isAssignableFrom(type)){
			ParameterizedType pm = (ParameterizedType)genericType;
			Type [] argtps = pm.getActualTypeArguments();
			if(argtps.length > 0){
				return (Class<?>)argtps[0];
			}else{
				return Object.class;
			}
		}else if(type.isArray()){
			return type.getComponentType();
		}
		return type;
	}
	
	public static boolean isPrimitive(Class<?> type){
		return primitiveTypes.contains(type);
	}
	
	public static boolean isCollection(Class<?> type){
		return List.class.isAssignableFrom(type) || Set.class.isAssignableFrom(type) || type.isArray();
	}
}
