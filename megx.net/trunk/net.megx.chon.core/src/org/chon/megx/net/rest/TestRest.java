package org.chon.megx.net.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("test")
public class TestRest {
	@GET
	@Produces("text/html")
	public String test() {
		return "Hello TestRest";
	}
}
