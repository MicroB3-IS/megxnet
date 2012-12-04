package net.megx.ws.wod.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.megx.megdb.wod05.Wod05Service;
import net.megx.model.wod05.Wod05;
import net.megx.ui.table.json.TableDataResponse;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;

@Path("wod05")
public class Wod05RestService extends BaseRestService {
	private Wod05Service service;

	public Wod05RestService(Wod05Service service) {
		this.service = service;
	}
	@GET
	@Path("ping")
	@Produces(MediaType.APPLICATION_JSON)
	public String ping() {
		Map<String, String> json = new HashMap<String, String>();
		json.put("ping", "pong");
		return toJSON(json);
	}
	
	@GET
	@Path("interpolate")
	@Produces(MediaType.APPLICATION_JSON)
	public String interpolate(
			@QueryParam("lat") double lat, 
			@QueryParam("lon") double lon, 
			@QueryParam("buffer") double buffer, 
			@QueryParam("depth") double depth, String data) {
		try {
			List<Wod05> list = service.getWod05Data(lat, lon, depth, buffer);
			return toJSON(list);
		} catch (Exception e) {
			Result<Map<String, Object>> r = this.handleException(e);
			return toJSON(r);
		}
	}
	
	@GET
	@Path("table")
	@Produces(MediaType.APPLICATION_JSON)
	public String table(
			@QueryParam("lat") double lat,
			@QueryParam("lon") double lon, 
			@QueryParam("buffer") double buffer,
			@QueryParam("depth") double depth) {
		try {
			List<Wod05> list = service.getWod05Data(lat, lon, depth, buffer);
			TableDataResponse<Wod05> data = new TableDataResponse<Wod05>();
			data.setData(list);
			return toJSON(data);
		} catch (Exception e) {
			Result<Map<String, Object>> r = this.handleException(e);
			return toJSON(r);
		}
	}
}
