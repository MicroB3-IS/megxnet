package net.megx.security.auth.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.megx.security.auth.SecurityContext;

public class Utils {
	public static boolean isArray(Object object){
		return object != null ? object.getClass().isArray() : false;
	}
	
	public static boolean isIterable(Object object){
		return object != null ? object instanceof Iterable : false;
	}
	
	@SuppressWarnings("rawtypes")
	public static Class<?> getBaseType(Object object){
		if(isArray(object)){
			return object.getClass().getComponentType();
		}else if(isIterable(object)){
			Object ch = null;
			if(object instanceof List && ((List)object).size() > 0){
				ch = ((List)object).get(0);
			}else if(object instanceof Set){
				ch = ((Set)object).iterator().next();
			}
			if(ch != null){
				return ch.getClass();
			}
		}
		return null;
	}
}
