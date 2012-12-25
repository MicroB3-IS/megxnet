package net.megx.ws.common;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.megx.ws.core.CustomMediaType;

@Path("/testResource")
public class TestResource {
	
	public static class DataHolder{
		private String data;

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public DataHolder(String data) {
			super();
			this.data = data;
		}
		
		
	}
	
	@GET
	@Produces(CustomMediaType.TEXT_CSV)
	@Path("csv.txt")
	public DataHolder getData(){
		return new DataHolder("test-data");
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("txt.txt")
	public DataHolder getDataTxt(){
		return new DataHolder("test-data");
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("data.html")
	public DataHolder getDataHTML(){
		return new DataHolder("test-data");
	}
	
	@GET
	@Produces(CustomMediaType.APPLICATION_CSV)
	@Path("csv.app")
	public DataHolder getDataCSVAPP(){
		return new DataHolder("test-data");
	}
	
}
