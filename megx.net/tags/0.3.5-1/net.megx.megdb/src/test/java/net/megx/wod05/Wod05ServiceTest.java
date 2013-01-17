package net.megx.wod05;

import java.util.List;

import junit.framework.Assert;
import net.megx.megdb.wod05.Wod05Service;
import net.megx.megdb.wod05.impl.DBWod05Service;
import net.megx.model.wod05.Wod05;
import net.megx.security.services.DBServiceTest;

import org.junit.Before;
import org.junit.Test;

public class Wod05ServiceTest extends DBServiceTest {
	private Wod05Service wod05Service;

	@Before
	public void setup() throws Exception{
		wod05Service = buildService(DBWod05Service.class);
	}
	
	@Test
	public void testServiceNotNull() {
		Assert.assertNotNull(wod05Service);
	}
	
	@Test
	public void testGetWod05Data() throws Exception {
		List<Wod05> ls = wod05Service.getWod05Data(1d, 1d, 1d, 1d);
		Assert.assertNotNull(ls);
	}

}
