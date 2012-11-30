package net.megx.content.imp.exp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.google.gson.Gson;

public class ZipInfo {
	private String version = "1.0.0";
	private Date exportDate = new Date();
	
	private Map<String, String> filesPaths;

	public Map<String, String> getFilesPaths() {
		return filesPaths;
	}

	public void setFilesPaths(Map<String, String> filesPaths) {
		this.filesPaths = filesPaths;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getExportDate() {
		return exportDate;
	}

	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}
	
	private static Gson gson = new Gson();
	public static ZipInfo fromJSON(String json) {
		ZipInfo zi = gson.fromJson(json, ZipInfo.class);
		return zi;
	}
	
	public String toJSONString() {
		String json = gson.toJson(this);
		try {
			return new JSONObject(json).toString(2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public static void main(String[] args) throws JSONException {
		
		ZipInfo inf = new ZipInfo();
		Map<String, String> map = new HashMap<String, String>();
		map.put("01.xml", "/www/joco/a");
		map.put("02.xml", "/usr/local/menu");
		inf.setFilesPaths(map);
		String json = inf.toJSONString();
		
		System.out.println(json);
		
		ZipInfo zi = ZipInfo.fromJSON(json);
		System.out.println(zi.getFilesPaths());
	}
}
