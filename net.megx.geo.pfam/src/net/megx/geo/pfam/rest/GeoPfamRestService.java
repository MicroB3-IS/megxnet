package net.megx.geo.pfam.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.megx.megdb.geopfam.GeoPfamService;
import net.megx.model.geopfam.GeoPfamRow;
import net.megx.ui.table.json.TableDataResponse;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;

@Path("v1/geographic-pfam/" + GeoPfamRestService.VERSION )
public class GeoPfamRestService extends BaseRestService {
	public static final String VERSION = "v1.0.0";
	
	private GeoPfamService geoPfamService;

	public GeoPfamRestService(GeoPfamService geoPfamService) {
		this.geoPfamService = geoPfamService;
	}
	
	@GET
	@Path("geopfamTable")
	@Produces(MediaType.APPLICATION_JSON)
	public String geopfamTable() {
		try {
			List<GeoPfamRow> list = geoPfamService.getAll();
			TableDataResponse<GeoPfamRow> data = new TableDataResponse<GeoPfamRow>();
			data.setData(list);
			return toJSON(data);
		} catch (Exception e) {
			Result<Map<String, Object>> r = this.handleException(e);
			return toJSON(r);
		}
	}
}
