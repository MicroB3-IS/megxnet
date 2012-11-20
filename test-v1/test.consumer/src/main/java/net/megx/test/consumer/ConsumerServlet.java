package net.megx.test.consumer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 * Servlet implementation class ConsumerServlet
 */
public class ConsumerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * Default constructor.
	 */
	public ConsumerServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doProcessRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doProcessRequest(request, response);
	}

	private void doProcessRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String isCallback = request.getParameter("isCallback");
		if("true".equals(isCallback)){
			OAuthService service = (OAuthService)request.getSession(true).getAttribute("oAuthService");
			if(service != null){
				System.out.println("PARAMETERS: ");
				System.out.println(request.getParameterMap());
				Token requestToken = (Token)request.getSession().getAttribute("requestToken");
				
				Token accessToken = service.getAccessToken(requestToken, new Verifier("none"));
				System.out.println("ACCESS-TOKEN: " + accessToken);
				
				OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, "http://localhost:8080/megx.net/pubmap/some/resource");
				service.signRequest(accessToken, oAuthRequest);
				Response r = oAuthRequest.send();
				Writer writer = response.getWriter();
				Reader reader = new InputStreamReader(r.getStream());
				int data = -1;
				writer.write("<h1>THE RESOURCE:</h1>");
				while((data = reader.read()) >= 0){
					writer.write(data);
				}
				reader.close();
				writer.close();
			}
		}else{
			String apiKey = request.getParameter("apiKey");
			String apiSecret = request.getParameter("apiSecret");
			
			String requestTokenEndpoint = request.getParameter("requestTokenEndpoint");
			String accessTokenEndpoint =  request.getParameter("accessTokenEndpoint");
			String authorizationURL =  request.getParameter("authorizationURL");
			
			
			String resourceURL = request.getParameter("resourceURL");
			
			OAuthService service = new ServiceBuilder()
				.provider(new MegxNetAPI(
						requestTokenEndpoint, 
						accessTokenEndpoint, 
						authorizationURL))
				.apiKey(apiKey)
				.apiSecret(apiSecret)
				.callback(request.getRequestURL().toString()+"?isCallback=true")
				.debug().build();
			System.out.println("}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}");
			Token requestToken = service.getRequestToken();
			System.out.println("}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}");
			String authURL = service.getAuthorizationUrl(requestToken);
			HttpSession session = request.getSession(true);
			session.setAttribute("oAuthService", service);
			session.setAttribute("requestToken", requestToken);
			System.out.println(" >>> Authorization URL (redirect): " + authURL);
			response.sendRedirect(authURL);
		}
	}

}
