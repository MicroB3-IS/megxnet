package net.megx.geo.pfam.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.megx.megdb.geopfam.GeoPfamService;
import net.megx.ws.core.BaseRestService;

@Path("v1/geographic-pfam/v1.0.0")
public class GeoPfamRestService extends BaseRestService {

	private GeoPfamService geoPfamService;

	public GeoPfamRestService(GeoPfamService geoPfamService) {
		this.geoPfamService = geoPfamService;
	}
	
	@GET
	@Path("geopfamTable")
	@Produces(MediaType.APPLICATION_JSON)
	public String geopfamTable() {
		return "TODO";
	}
	
	
}
