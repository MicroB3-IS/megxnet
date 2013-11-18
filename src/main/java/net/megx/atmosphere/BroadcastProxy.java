package net.megx.atmosphere;

import org.atmosphere.cpr.BroadcasterFactory;

public class BroadcastProxy {
	public void broadcastMessage(String path, String message){
		BroadcasterFactory.getDefault().lookup(path, true).broadcast(message);
	}
}
