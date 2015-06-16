package net.megx.ui.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;

/**
 * Object passed in templates for $ext.dataTable
 * 
 * @author Jovica
 *
 */
public class DataTableVelocity {

	private Response resp;

	public DataTableVelocity(Request req, Response resp, IContentNode node) {
		this.resp = resp;
	}
	
	public String render(String tableId) {
		return render(tableId, null);
	}
	
	public String render(String tableId, String tableConfig) {
		return render(tableId, tableConfig, null, false);
	}
	
	private String render(String tableId, String tableConfig, String url, boolean serverSideProcessing) {
		if (tableConfig == null) {
			tableConfig = "{}";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tableId", tableId);
		params.put("tableConfig", tableConfig);
		params.put("url", url);
		params.put("serverSideProcessing", serverSideProcessing);
		return this.resp.formatTemplate("net.megx.ui.table/render.tpl.script.js.html", params);
	}
	
	public String renderServerSide(List<String> columns, String url, boolean serverSideProcessing) {
		return renderServerSide(columns, url, serverSideProcessing, null);
	}
	
	public String renderServerSide(List<String> columns, String url, boolean serverSideProcessing, String tableConfig) {
		Map<String, Object> params = new HashMap<String, Object>();
		String tableId = ExtensionUtils.rndId("tbl_");
		params.put("tableId", tableId);
		params.put("columns", columns);
		String jsData = render(tableId, tableConfig, url, serverSideProcessing);
		params.put("jsData", jsData);
		return this.resp.formatTemplate("net.megx.ui.table/generate.table.html", params);
	}

}
