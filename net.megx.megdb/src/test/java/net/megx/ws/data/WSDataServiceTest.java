package net.megx.ws.data;

import net.megx.megdb.ws.data.WSDataService;
import net.megx.megdb.ws.data.impl.PostgreSQLWSDataServiceImpl;
import net.megx.security.services.DBServiceTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WSDataServiceTest extends DBServiceTest{

	private WSDataService service;
	
	@Before
	public void setup() throws Throwable {
		this.service = buildService(PostgreSQLWSDataServiceImpl.class);
	}
	
	@Test
	public void testUpdateStringCell() throws Throwable{
		int nbUpdatedRecords = service.savetableCellInput(
				"osdregistry", 
				"osd_participants", 
				"{\"idCol\":\"osd_id\",\"idVal\":\"OSD 3\",\"site_name\":\"Roscofff\"}");
		
		Assert.assertTrue(nbUpdatedRecords > 0);
	}
	
	@Test
	public void testUpdateIntCell() throws Throwable{
		int nbUpdatedRecords = service.savetableCellInput(
				"osdregistry", 
				"osd_participants", 
				"{\"idCol\":\"osd_id\",\"idVal\":\"OSD 3\",\"site_long\" : 100}");
		
		Assert.assertTrue(nbUpdatedRecords > 0);
	}
	
}
