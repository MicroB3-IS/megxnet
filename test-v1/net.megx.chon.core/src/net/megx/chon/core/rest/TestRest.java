package net.megx.chon.core.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Path("test")
public class TestRest {
	private static final Log log = LogFactory.getLog(TestRest.class);
	
	@GET
	@Produces("text/html")
	public String test() {
		log.debug("Entering test metod in " + TestRest.class);
		return "Hello TestRest";
	}
}
