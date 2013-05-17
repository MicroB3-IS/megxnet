package net.megx.geopfam;

import java.util.List;

import junit.framework.Assert;
import net.megx.megdb.geopfam.GeoPfamService;
import net.megx.megdb.geopfam.impl.DBGeoPfamService;
import net.megx.model.geopfam.GeoPfamRow;
import net.megx.security.services.DBServiceTest;

import org.junit.Before;
import org.junit.Test;

public class GeoPfamServiceTest extends DBServiceTest {
	private GeoPfamService geoPfamService;

	@Before
	public void setup() throws Exception{
		geoPfamService = buildService(DBGeoPfamService.class);
	}
	
	@Test
	public void testServiceNotNull() {
		Assert.assertNotNull(geoPfamService);
	}
	
	@Test
	public void getByTargetAccession() throws Exception {
		List<GeoPfamRow> ls = geoPfamService.getByTargetAccession("PF0002");
		Assert.assertNotNull(ls);
	}
}
