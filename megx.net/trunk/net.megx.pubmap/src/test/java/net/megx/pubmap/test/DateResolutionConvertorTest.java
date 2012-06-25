package net.megx.pubmap.test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import net.megx.model.DateResolution;
import net.megx.model.ModelFactory;
import net.megx.model.Sample;
import net.megx.pubmap.rest.json.SampleDTO;
import net.megx.pubmap.rest.json.SampleDTO.DateRes;

import org.junit.Test;

public class DateResolutionConvertorTest {
	

	@Test
	public void testFromDTOtoDAO() {
		SampleDTO dto = new SampleDTO();
		dto.setSamyear("2012");
		dto.setSammonth("01");
		dto.setSamday("28");
		dto.setSamhour("15");
		
		try {
			DateRes d = SampleDTO.calcDateRes(dto);
			Calendar c = Calendar.getInstance();
			c.setTime(d.getDate());
			
			// year
			Assert.assertEquals(2012, c.get(Calendar.YEAR));
			
			//zero index for month in calendar
			Assert.assertEquals(0, c.get(Calendar.MONTH));
			
			//day
			Assert.assertEquals(28, c.get(Calendar.DAY_OF_MONTH));
			
			
			// resolution
			Assert.assertEquals(DateResolution.HOUR, d.getResolution());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test 
	public void testDAOtoDTO() throws ParseException {
		Sample sample = ModelFactory.createSample();
		Date date = SampleDTO.DATE_FORMAT.parse("2012/01/28 15:00:00");
		sample.setDateTaken(date);
		sample.setDateRes(DateResolution.HOUR);
		SampleDTO dto = SampleDTO.fromDAO(sample);
		Assert.assertEquals("2012", dto.getSamyear());
		Assert.assertEquals("01", dto.getSammonth());
		Assert.assertEquals("28", dto.getSamday());
		Assert.assertEquals("15", dto.getSamhour());
		Assert.assertEquals(null, dto.getSammin());
		Assert.assertEquals(null, dto.getSamsec());
		
	}
	
}
