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
		
		try {
			ExtensionUtils.ensureExtenstionVisible("jquery", resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		@SuppressWarnings("unchecked")
		List<String> scrips = (List<String>) resp.getTemplateContext().get("head:scripts");
		@SuppressWarnings("unchecked")
		List<String> css = (List<String>) resp.getTemplateContext().get("head:css");
		
		css.add("net.megx.ui.table/css/data.table.css");
		
		scrips.add("net.megx.ui.table/js/json2.js");
		scrips.add("net.megx.ui.table/js/jquery.dataTables.js");
		scrips.add("net.megx.ui.table/js/table.utils.js");
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
