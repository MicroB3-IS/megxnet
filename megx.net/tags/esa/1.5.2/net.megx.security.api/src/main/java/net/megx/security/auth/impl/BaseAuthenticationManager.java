package net.megx.security.auth.impl;

import java.util.Iterator;

import net.megx.security.auth.AccessDeniedException;
import net.megx.security.auth.Authentication;
import net.megx.security.auth.AuthenticationManager;
import net.megx.security.auth.InsufficientAuthenticationException;

public abstract class BaseAuthenticationManager implements AuthenticationManager{

	@SuppressWarnings("unchecked")
	@Override
	public void checkAuthentication(Authentication authentication, Object object)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if(Utils.isArray(object)){
			Object [] arr = (Object[])object;
			for(Object o: arr){
				doCheckAuthentication(authentication, o);
			}
		}else if(Utils.isIterable(object)){
			Iterable<Object> i = (Iterable<Object>)object;
			Iterator<Object> it = i.iterator();
			while(it.hasNext()){
				doCheckAuthentication(authentication, it.next());
			}
		}else{
			doCheckAuthentication(authentication, object);
		}
	}

	
	
	
	@Override
	public boolean accepts(Class<?> clazz) {
		if(clazz == null)
			return false;
		return getProtectedObjectBaseClass().isAssignableFrom(clazz);
	}

	protected abstract Class<?> getProtectedObjectBaseClass();
	
	protected abstract void doCheckAuthentication(Authentication authentication, Object object)
			throws AccessDeniedException, InsufficientAuthenticationException;
}
