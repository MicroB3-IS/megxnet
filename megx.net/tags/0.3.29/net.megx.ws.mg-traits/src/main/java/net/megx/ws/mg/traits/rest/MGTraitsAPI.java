package net.megx.ws.mg.traits.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.megx.megdb.mgtraits.MGTraitsService;

@Path("v1/mg-traits/v1.0.0")
public class MGTraitsAPI extends BaseRestService{
	private MGTraitsService service;
	
	public MGTraitsAPI(MGTraitsService service){
		this.service = service;
	}
	
	@Path("job")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String insertMGTraitsJob(@QueryParam("infile") String inFile, @QueryParam("sample_label") String sampleLabel, @QueryParam("env") String environment){
		try {
			if(inFile == "" || sampleLabel == "" || environment == ""){
				throw new Exception();
			}
			service.insertMGJob(inFile, sampleLabel, environment);
			return toJSON("Successfull insert");
		} catch (Exception e) {
			return toJSON(handleException(e));
		}
	}
}
