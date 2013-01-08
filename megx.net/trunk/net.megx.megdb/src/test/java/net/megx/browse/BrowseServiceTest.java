package net.megx.browse;

import java.util.List;

import junit.framework.Assert;
import net.megx.megdb.browse.BrowseService;
import net.megx.megdb.browse.impl.DBBrowseService;
import net.megx.model.browse.MetagenomesRow;
import net.megx.security.services.DBServiceTest;

import org.junit.Before;
import org.junit.Test;

public class BrowseServiceTest extends DBServiceTest {
	private BrowseService browseService;

	@Before
	public void setup() throws Exception{
		browseService = buildService(DBBrowseService.class);
	}
	
	@Test
	public void testServiceNotNull() {
		Assert.assertNotNull(browseService);
	}
	
	@Test
	public void testGetMetagenomes() throws Exception {
		List<MetagenomesRow> ls = browseService.getMetagenomes();
		Assert.assertNotNull(ls);
	}

}
