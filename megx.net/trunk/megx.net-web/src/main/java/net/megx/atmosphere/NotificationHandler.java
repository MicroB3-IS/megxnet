package net.megx.atmosphere;

import java.io.IOException;

import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.DefaultBroadcaster;

@ManagedService(path = "/topic/notifications", broadcaster = DefaultBroadcaster.class)
public class NotificationHandler{
	
	/**
     * Invoked when the connection as been fully established and suspended, e.g ready for receiving messages.
     *
     * @param r
     */
    @Ready
    public void onReady(final AtmosphereResource r) {
        System.out.println("Browser {} connected." + r.uuid());
    }
    
    /**
     * Invoked when the client disconnect or when an unexpected closing of the underlying connection happens.
     *
     * @param event
     */
    @Disconnect
    public void onDisconnect(AtmosphereResourceEvent event) {
        if (event.isCancelled()) {
            System.out.println("Browser {} unexpectedly disconnected" + event.getResource().uuid());
        } else if (event.isClosedByClient()) {
            System.out.println("Browser {} closed the connection" + event.getResource().uuid());
        }
    }
    
    /**
     * Simple annotated class that demonstrate how {@link org.atmosphere.config.managed.Encoder} and {@link org.atmosphere.config.managed.Decoder
     * can be used.
     *
     * @param message an instance of {@link Message}
     * @return
     * @throws IOException
     */
    @Message
    public String onMessage(String message) throws IOException {
        System.out.println(message);
        return message;
    }
}
