package net.megx.prox.rest;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import net.megx.ws.core.BaseRestService;

import org.apache.commons.io.IOUtils;

@Path("v1/PfamProxy/v1.0.0")
public class PfamProxy extends BaseRestService{
	
	private static final String baseURL = "http://pfam.sanger.ac.uk/family/";
	private static final String outputFormat = "?output=xml";
	
	public PfamProxy(){
	}
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String pfamProxy(@PathParam("id") final String id){
			URL url;
			try {
				url = new URL(baseURL + id + outputFormat);
				HttpURLConnection connection;
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Accept", "application/xml");
				InputStream xml = connection.getInputStream();
				StringWriter writer = new StringWriter();
				IOUtils.copy(xml, writer, "UTF-8");
				String theString = writer.toString();
				connection.disconnect();
				return theString;
			} catch (Exception e) {
				throw new WebApplicationException(e);
			}
			
	}
}
