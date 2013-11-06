package net.megx.broadcast.proxy.impl;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.megx.broadcast.proxy.BroadcasterProxy;

public class AtmosphereBroadcastProxy implements BroadcasterProxy{
	
	protected Log log = LogFactory.getLog(getClass());
	
	@Override
	public void broadcastMessage(String path, String message){
		Class<?> broadcasterProxy;
		try {
			broadcasterProxy = Class.forName("net.megx.atmosphere.BroadcastProxy");
			Method broadcastMethod = broadcasterProxy.getMethod("broadcastMessage", String.class, String.class);
			broadcastMethod.invoke(broadcasterProxy.newInstance(), path, message);
		} catch (Exception e) {
			log.debug("Exception occured while trying to broadcast message to client. Details follow:");
			e.printStackTrace();
		}
	}

}
