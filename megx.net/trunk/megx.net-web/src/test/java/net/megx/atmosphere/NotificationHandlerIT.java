package net.megx.atmosphere;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import junit.framework.Assert;

import org.atmosphere.wasync.ClientFactory;
import org.atmosphere.wasync.Event;
import org.atmosphere.wasync.Function;
import org.atmosphere.wasync.Request;
import org.atmosphere.wasync.RequestBuilder;
import org.atmosphere.wasync.Socket;
import org.atmosphere.wasync.impl.AtmosphereClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NotificationHandlerIT {

	private AtmosphereClient client = ClientFactory.getDefault().newClient(
			AtmosphereClient.class);
	@SuppressWarnings("rawtypes")
	private RequestBuilder request;
	private static final String CHANNEL_ESTABLISHED = "OPEN";
	private Properties endPointProperties = new Properties();
	private Map<String, String> errorMap = new HashMap<String, String>();
	
	@Before
	public void setup() {
		this.request = client.newRequestBuilder().method(Request.METHOD.GET);
		try {
			endPointProperties.load(NotificationHandlerTest.class.getClassLoader().getResourceAsStream("endpoints.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@Test
	public void testWebSocketChannel() {

		String[] webSocketEndpoints = endPointProperties.getProperty("websocket.endpoints").split(",");

		this.request.transport(Request.TRANSPORT.WEBSOCKET);

		for (String endPoint : webSocketEndpoints) {
			this.request.uri(endPoint.trim());

			Socket socket = client.create();

			try {
				socket.on(Event.OPEN, new Function<String>() {
					@Override
					public void on(String t) {
						// WebSocket channel has been successfully established
						Assert.assertEquals(t, CHANNEL_ESTABLISHED);
					}
				}).open(request.build());
			} catch (IOException e) {
				errorMap.put(endPoint, getErrorMessage(e, "websocket", endPoint));
			} finally {
				socket.close();
			}
		}
		
		Assert.assertTrue(printAllErrors(errorMap), errorMap.size() == 0);
	}

	@After
	public void tearDown(){
		errorMap.clear();
	}

	private String getErrorMessage(IOException e, String channelType,
			String endPoint) {
		StringBuilder sb = new StringBuilder();
		sb.append("Client cannot establish " + channelType.toLowerCase()
				+ " channel with the specified endpoint: ");
		sb.append(endPoint);
		sb.append(" Cause of error: ");
		sb.append(e.getCause().toString());

		return sb.toString();
	}
	
	private String printAllErrors(Map<String, String> errorMap){
		StringBuilder sb = new StringBuilder();
		for (String errorKey : errorMap.keySet()) {
			sb.append(errorMap.get(errorKey));
			sb.append("\n");
		}
		
		return sb.toString();
	}

}
