package net.megx.ui.table.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import net.megx.ui.table.json.TableDataRequest;
import net.megx.ui.table.json.TableDataResponse;

import com.google.gson.Gson;

@Path("ui.table.demo")
public class TableRestService {
	
	private Gson gson = new Gson();
	
	@GET
	@Path("ping")
	@Produces("application/json")
	public String ping() {
		Map<String, String> json = new HashMap<String, String>();
		json.put("ping", "pong");
		return gson.toJson(json);
	}
	
	/**
	 * Sample table rest that returns all table data on client
	 * Processing (pagination, filter) is done on client
	 * @return
	 */
	@GET
	@Path("table-1")
	@Produces("application/json")
	public String table1() {
		List<MyData> allRows = MyDataProvider.getDataRows();
		TableDataResponse<MyData> resp = new TableDataResponse<MyData>();
		resp.setData(allRows);
		return gson.toJson(resp);
	}
	
	/**
	 * Sample table rest that returns only a page of data to client
	 * Processing (pagination, filter) is done on server
	 * 
	 * @param data
	 * @return
	 */
	@GET
	@Path("table-2")
	@Produces("application/json")
	public String table2(@QueryParam("data") String data) {
		TableDataRequest req = gson.fromJson(data, TableDataRequest.class);
		List<MyData> allRows = MyDataProvider.getDataRows();
		List<MyData> filtered = filter(allRows, req.getsSearch());
		List<MyData> list = sublist(filtered, req.getiDisplayStart(), req.getiDisplayLength()); 
		
		TableDataResponse<MyData> resp = new TableDataResponse<MyData>();
		resp.setiTotalRecords(allRows.size());
		resp.setiTotalDisplayRecords(filtered.size());
		resp.setData(list);
		resp.setsEcho(Integer.valueOf(req.getsEcho()).toString());
		return gson.toJson(resp);
	}

	private List<MyData> sublist(List<MyData> list,
			Integer start, Integer maxResults) {
		List<MyData> ls = new ArrayList<MyData>();
		for(int i=start; i<list.size(); i++) {
			ls.add(list.get(i));
			if(ls.size()>=maxResults) break;
		}
		return ls;
	}

	private List<MyData> filter(List<MyData> allRows, String search) {
		List<MyData> ls = new ArrayList<MyData>();
		for (MyData d : allRows) {
			if (d.getName().indexOf(search) >= 0
					|| d.getDescription().indexOf(search) >= 0) {
				ls.add(d);
			}
		}
		return ls;
	}
	
	
	@GET
	@Path("table-countries")
	@Produces("application/json")
	public String tableCountries() {
		List<Country> allRows = CountriesData.getCountries();
		TableDataResponse<Country> resp = new TableDataResponse<Country>();
		resp.setData(allRows);
		return gson.toJson(resp);
	}
	
}
