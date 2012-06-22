package net.megx.pubmap.test;

import java.util.Calendar;
import java.util.Date;

import net.megx.model.DateResolution;
import net.megx.pubmap.rest.json.SampleDTO;

import org.junit.Test;

public class DateResolutionConvertorTest {
	
	class DateRes {
		DateResolution res;
		Date date;
	}
	
	public DateRes getDateRes(DateResolution [] ar_arr, String [] fields) {
		return new DateRes();
	}
	
	@Test
	public void testFromDTOtoDAO() {
		SampleDTO dto = new SampleDTO();
		dto.setSamyear("2012");
		dto.setSammonth("09");
		dto.setSamday("1");
		
		
		DateResolution [] dr_arr = new DateResolution[] {
				DateResolution.YEAR,
				DateResolution.MONTH,
				DateResolution.DAY,
				DateResolution.HOUR,
				DateResolution.MINUTE,
				DateResolution.SECOND
		};
		
		int [] cal_vals = new int[] {
				Calendar.YEAR,
				Calendar.MONTH,
				Calendar.DATE,
				Calendar.HOUR,
				Calendar.MINUTE,
				Calendar.SECOND
		};
		
		String[] arr = new String [] {
				dto.getSamyear(),
				dto.getSammonth(),
				dto.getSamday(),
				dto.getSamhour(),
				dto.getSammin(),
				dto.getSamsec()
		};
		
		DateResolution res = null;
		Calendar cal = Calendar.getInstance();
		cal.set(0, 0, 1, 0, 0, 0);
		for(int i=0; i<arr.length; i++) {
			if(arr[i] == null || arr[i].trim().length()==0) {
				break;
			} else {
				res = dr_arr[i];
				cal.set(cal_vals[i], cal.get(cal_vals[i]) + Integer.valueOf(arr[i]));
			}
		}
		
		
		System.out.println("Resolution: " + res + " Time: " + cal.getTime());
	}
	
}
