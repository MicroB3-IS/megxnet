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
import net.megx.model.browse.GenomesRow;
import net.megx.model.browse.MetagenomesRow;
import net.megx.model.browse.PhagesRow;
import net.megx.ui.table.json.TableDataRequest;
import net.megx.ui.table.json.TableDataResponse;
import net.megx.ws.browse.rest.tables.GenomesUiTblRow;
import net.megx.ws.browse.rest.tables.MetagenomesUiTblRow;
import net.megx.ws.browse.rest.tables.PhagesUiTblRow;
import net.megx.ws.browse.rest.utils.ConvUtils;
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
	@Path("genomesTable")
	@Produces(MediaType.APPLICATION_JSON)
	public String genomesTable(@QueryParam("data") String reqData) {
		TableDataRequest req = fromJSON(reqData, TableDataRequest.class);
		try {
			List<GenomesRow> genomesList = browseService.getGenomes();
			List<GenomesUiTblRow> list = ConvUtils.dbGenomesToUiList(genomesList);
			TableDataResponse<GenomesUiTblRow> data = new TableDataResponse<GenomesUiTblRow>();
			data.setData(list);
			return toJSON(data);
		} catch (Exception e) {
			Result<Map<String, Object>> r = this.handleException(e);
			return toJSON(r);
		}
	}
	
	@GET
	@Path("phagesTable")
	@Produces(MediaType.APPLICATION_JSON)
	public String phagesTable(@QueryParam("data") String reqData) {
		TableDataRequest req = fromJSON(reqData, TableDataRequest.class);
		try {
			List<PhagesRow> phagesList = browseService.getPhages();
			List<PhagesUiTblRow> list = ConvUtils.dbPhagesToUiList(phagesList);
			TableDataResponse<PhagesUiTblRow> data = new TableDataResponse<PhagesUiTblRow>();
			data.setData(list);
			return toJSON(data);
		} catch (Exception e) {
			Result<Map<String, Object>> r = this.handleException(e);
			return toJSON(r);
		}
	}
	
	@GET
	@Path("metagenomesTable")
	@Produces(MediaType.APPLICATION_JSON)
	public String metagenomesTable(@QueryParam("data") String reqData) {
		TableDataRequest req = fromJSON(reqData, TableDataRequest.class);
		try {
			List<MetagenomesRow> metagenomesList = browseService.getMetagenomes();
			List<MetagenomesUiTblRow> list = ConvUtils.dbMetagenomesToUiList(metagenomesList);
			TableDataResponse<MetagenomesUiTblRow> data = new TableDataResponse<MetagenomesUiTblRow>();
			data.setData(list);
			return toJSON(data);
		} catch (Exception e) {
			Result<Map<String, Object>> r = this.handleException(e);
			return toJSON(r);
		}
	}
}
