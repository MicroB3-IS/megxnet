package net.megx.ws.browse.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.megx.megdb.browse.BrowseService;
import net.megx.model.browse.MetagenomesRow;
import net.megx.ui.table.json.TableDataRequest;
import net.megx.ui.table.json.TableDataResponse;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;

@Path("browse")
public class BrowseRestService extends BaseRestService {

	private BrowseService browseService;

	public BrowseRestService(BrowseService browseService) {
		this.browseService = browseService;
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
	@Path("metagenomesTable")
	@Produces(MediaType.APPLICATION_JSON)
	public String metagenomesTable(@QueryParam("data") String reqData) {
		TableDataRequest req = fromJSON(reqData, TableDataRequest.class);
		try {
			List<MetagenomesRow> metagenomesList = browseService.getMetagenomes();
			List<MetagenomesUiTblRow> list = ConvUtils.toUiTableList(metagenomesList);
			TableDataResponse<MetagenomesUiTblRow> data = new TableDataResponse<MetagenomesUiTblRow>();
			data.setData(list);
			return toJSON(data);
		} catch (Exception e) {
			Result<Map<String, Object>> r = this.handleException(e);
			return toJSON(r);
		}
	}
}
