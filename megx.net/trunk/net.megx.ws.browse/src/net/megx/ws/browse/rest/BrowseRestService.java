package net.megx.ws.browse.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.megx.megdb.browse.BrowseService;
import net.megx.model.browse.GenomesRow;
import net.megx.model.browse.MarkerGenesRow;
import net.megx.model.browse.MetagenomesRow;
import net.megx.model.browse.PhagesRow;
import net.megx.model.browse.SamplingSitesRow;
import net.megx.ui.table.json.TableDataResponse;
import net.megx.ws.browse.rest.tables.GenomesUiTblRow;
import net.megx.ws.browse.rest.tables.MarkerGenesUiTblRow;
import net.megx.ws.browse.rest.tables.MetagenomesUiTblRow;
import net.megx.ws.browse.rest.tables.PhagesUiTblRow;
import net.megx.ws.browse.rest.tables.SamplingSitesUiTblRow;
import net.megx.ws.browse.rest.utils.ConvUtils;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.CustomMediaType;
import net.megx.ws.core.Result;
import net.megx.ws.core.providers.csv.annotations.CSVDocument;
import net.megx.ws.core.providers.txt.ColumnNameFormat;

@Path("browse")
public class BrowseRestService extends BaseRestService {

	private BrowseService browseService;

	public BrowseRestService(BrowseService browseService) {
		this.browseService = browseService;
	}
	
	
	// Genomes
	
	@GET
	@Path("genomes.csv")
	@Produces(CustomMediaType.APPLICATION_CSV)
	@CSVDocument(preserveHeaderColumns = true, columnNameFormat = ColumnNameFormat.FROM_CAMEL_CASE)
	public List<GenomesUiTblRow> genomesCSV() throws Exception {
		List<GenomesRow> genomesList = browseService.getGenomes();
		List<GenomesUiTblRow> list = ConvUtils.dbGenomesToUiList(genomesList);
		return list;
	}
	
	@GET
	@Path("genomesTable")
	@Produces(MediaType.APPLICATION_JSON)
	public String genomesTable(@QueryParam("data") String reqData) {
		//TODO: we would need req for server pagination, search, sort
		//TableDataRequest req = fromJSON(reqData, TableDataRequest.class);
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
	
	
	// Phages
	
	@GET
	@Path("phages.csv")
	@Produces(CustomMediaType.APPLICATION_CSV)
	@CSVDocument(preserveHeaderColumns = true, columnNameFormat = ColumnNameFormat.FROM_CAMEL_CASE)
	public List<PhagesUiTblRow> phagesCSV() throws Exception {
		List<PhagesRow> phagesList = browseService.getPhages();
		List<PhagesUiTblRow> list = ConvUtils.dbPhagesToUiList(phagesList);
		return list;
	}
	
	@GET
	@Path("phagesTable")
	@Produces(MediaType.APPLICATION_JSON)
	public String phagesTable(@QueryParam("data") String reqData) {
		//TODO: we would need req for server pagination, search, sort
		//TableDataRequest req = fromJSON(reqData, TableDataRequest.class);
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
	
	
	// Metagenomes
	
	@GET
	@Path("metagenomes.csv")
	@Produces(CustomMediaType.APPLICATION_CSV)
	@CSVDocument(preserveHeaderColumns = true, columnNameFormat = ColumnNameFormat.FROM_CAMEL_CASE)
	public List<MetagenomesUiTblRow> metagenomeCSV() throws Exception {
		List<MetagenomesRow> metagenomesList = browseService.getMetagenomes();
		List<MetagenomesUiTblRow> list = ConvUtils.dbMetagenomesToUiList(metagenomesList);
		return list;
	}
	
	@GET
	@Path("metagenomesTable")
	@Produces(MediaType.APPLICATION_JSON)
	public String metagenomesTable(@QueryParam("data") String reqData) {
		//TODO: we would need req for server pagination, search, sort
		//TableDataRequest req = fromJSON(reqData, TableDataRequest.class);
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
	
	
	// Marker Genes
	
	@GET
	@Path("rrna.csv")
	@Produces(CustomMediaType.APPLICATION_CSV)
	@CSVDocument(preserveHeaderColumns = true, columnNameFormat = ColumnNameFormat.FROM_CAMEL_CASE)
	public List<MarkerGenesUiTblRow> markerGenesCSV() throws Exception {
		List<MarkerGenesRow> markerGenesList = browseService.getMarkerGenes();
		List<MarkerGenesUiTblRow> list = ConvUtils.dbMarkerGenesToUiList(markerGenesList);
		return list;
	}
	
	@GET
	@Path("markerGenesTable")
	@Produces(MediaType.APPLICATION_JSON)
	public String markerGenesTable(@QueryParam("data") String reqData) {
		//TODO: we would need req for server pagination, search, sort
		//TableDataRequest req = fromJSON(reqData, TableDataRequest.class);
		try {
			List<MarkerGenesRow> markerGenesList = browseService.getMarkerGenes();
			List<MarkerGenesUiTblRow> list = ConvUtils.dbMarkerGenesToUiList(markerGenesList);
			TableDataResponse<MarkerGenesUiTblRow> data = new TableDataResponse<MarkerGenesUiTblRow>();
			data.setData(list);
			return toJSON(data);
		} catch (Exception e) {
			Result<Map<String, Object>> r = this.handleException(e);
			return toJSON(r);
		}
	}
	
	
	// Sampling Sites
	
	@GET
	@Path("samplingSites.csv")
	@Produces(CustomMediaType.APPLICATION_CSV)
	@CSVDocument(preserveHeaderColumns = true, columnNameFormat = ColumnNameFormat.FROM_CAMEL_CASE)
	public List<SamplingSitesUiTblRow> samplingSitesCSV() throws Exception {
		List<SamplingSitesRow> samplingSitesList = browseService.getSamplingSites();
		List<SamplingSitesUiTblRow> list = ConvUtils.dbSamplingSitesToUiList(samplingSitesList);
		return list;
	}
	
	@GET
	@Path("samplingSitesTable")
	@Produces(MediaType.APPLICATION_JSON)
	public String samplingSitesTable(@QueryParam("data") String reqData) {
		//TODO: we would need req for server pagination, search, sort
		//TableDataRequest req = fromJSON(reqData, TableDataRequest.class);
		try {
			List<SamplingSitesRow> samplingSitesList = browseService.getSamplingSites();
			List<SamplingSitesUiTblRow> list = ConvUtils.dbSamplingSitesToUiList(samplingSitesList);
			TableDataResponse<SamplingSitesUiTblRow> data = new TableDataResponse<SamplingSitesUiTblRow>();
			data.setData(list);
			return toJSON(data);
		} catch (Exception e) {
			Result<Map<String, Object>> r = this.handleException(e);
			return toJSON(r);
		}
	}
	
	
}
