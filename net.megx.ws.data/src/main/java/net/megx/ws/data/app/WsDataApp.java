package net.megx.ws.data.app;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import net.megx.megdb.ws.data.WSDataService;
import net.megx.ws.core.BaseRestService;

@Path("v1/WsDataApp/v1.0.0")
public class WsDataApp extends BaseRestService {
	
	private WSDataService service;
	
	public WsDataApp(WSDataService service){
		this.service = service;
	}
	
	@Path("{schemaName}/{tableName}")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public int saveTableCellInput(@PathParam("schemaName") String schemaName,
			@PathParam("tableName") String tableName, @FormParam("updatePayload") String updatePayload) throws Throwable{
		return service.savetableCellInput(schemaName, tableName, updatePayload);
	}	
}
