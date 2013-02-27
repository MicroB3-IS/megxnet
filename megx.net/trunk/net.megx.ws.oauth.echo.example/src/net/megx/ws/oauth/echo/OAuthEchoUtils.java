package net.megx.ws.oauth.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class OAuthEchoUtils {

	public static final String HEADER_X_AUTH_SERVICE_PROVIDER = "X-Auth-Service-Provider";
	public static final String HEADER_X_VERIFY_CREDENTIALS_AUTH = "X-Verify-Credentials-Authorization";

	public static UserInfo validateCredentials(String serviceProviderURL, String auth)
			throws OAuthEchoException, IOException {
		UserInfo userInfo = null;

		DefaultHttpClient httpClient = new DefaultHttpClient();

		HttpGet httpGet = new HttpGet(serviceProviderURL);
		httpGet.addHeader("Authorization", auth);

		try {
			HttpResponse response = httpClient.execute(httpGet);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new OAuthEchoException(response.getStatusLine()
						.getReasonPhrase(), response.getStatusLine()
						.getStatusCode());
			}

			BufferedReader r = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = r.readLine()) != null) {
				sb.append(line);
			}

			try {
				JSONObject result = new JSONObject(sb.toString());
				userInfo = new UserInfo();
				userInfo.setUsername(result.getJSONObject("data").getString(
						"username"));
				userInfo.setFirstName(result.getJSONObject("data").getString(
						"firstName"));
				userInfo.setLastName(result.getJSONObject("data").getString(
						"lastName"));
				userInfo.setInitials(result.getJSONObject("data").getString(
						"initials"));
				userInfo.setEmail(result.getJSONObject("data").getString(
						"email"));

			} catch (JSONException e) {
				throw new OAuthEchoException(e);
			}

		} catch (ClientProtocolException e) {
			throw new OAuthEchoException(e);
		}
		return userInfo;
	}

	public static String getServiceProviderURL(HttpServletRequest request) {
		return request.getHeader(HEADER_X_AUTH_SERVICE_PROVIDER);
	}

	public static String getAuthorization(HttpServletRequest request) {
		return request.getHeader(HEADER_X_VERIFY_CREDENTIALS_AUTH);
	}

	public static UserInfo validateRequestCredentials(HttpServletRequest request)
			throws IOException, OAuthEchoException {
		String serviceProviderURL = getServiceProviderURL(request);
		if(serviceProviderURL == null){
			throw new OAuthEchoException("Service Provider HTTP Header missing.", 403);
		}
		
		String authorization = getAuthorization(request);
		if(authorization == null){
			throw new OAuthEchoException("Verify Credentials Authorization HTTP Header missing.", 403);
		}
		return validateCredentials(serviceProviderURL, authorization);
	}

}
