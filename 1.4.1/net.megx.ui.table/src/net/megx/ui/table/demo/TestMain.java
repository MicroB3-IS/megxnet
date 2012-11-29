package net.megx.ui.table.demo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.chon.core.common.util.FileUtils;
import org.json.JSONObject;
import org.json.XML;

import com.google.gson.Gson;

public class TestMain {
	static class Data {
		private List<Country> list;

		public List<Country> getList() {
			return list;
		}

		public void setList(List<Country> list) {
			this.list = list;
		}
		
	}
	private static Data data = null;
	public static List<Country> getCountries() {
		if(data == null) {
			File file = new File("c:/temp/countries.json");
			String json;
			try {
				json = FileUtils.readFileToString(file);
				Gson gson = new Gson();
				data = gson.fromJson(json, Data.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data.list;
	}
	
	public static void main(String[] args) throws Exception {
		File file = new File("c:/temp/countries.json");
		String json = FileUtils.readFileToString(file);
		Gson gson = new Gson();
		Data data = gson.fromJson(json, Data.class);
		for(Country c : data.list) {
			String s = String.format("ls.add(new Country(\"%s\", %d, %d, %d));", c.getName(), c.getPopulation(), c.getGdpPPP(), c.getUnemploymentRate());
			System.out.println(s);
		}
	}
}
