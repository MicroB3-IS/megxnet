package net.megx.pubmap.rest.json;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.megx.model.DateResolution;
import net.megx.model.ModelFactory;
import net.megx.model.Sample;

public class SampleDTO {
	private String label;
	private String region;
	private String longitude;
	private String latitude;
	private String material;
	private String depth;
	private String depthunit;
	private String samyear;
	private String sammonth;
	private String samday;
	private String samhour;
	private String sammin;
	private String samsec;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getDepthunit() {
		return depthunit;
	}
	public void setDepthunit(String depthunit) {
		this.depthunit = depthunit;
	}
	public String getSamyear() {
		return samyear;
	}
	public void setSamyear(String samyear) {
		this.samyear = samyear;
	}
	public String getSammonth() {
		return sammonth;
	}
	public void setSammonth(String sammonth) {
		this.sammonth = sammonth;
	}
	public String getSamday() {
		return samday;
	}
	public void setSamday(String samday) {
		this.samday = samday;
	}
	public String getSamhour() {
		return samhour;
	}
	public void setSamhour(String samhour) {
		this.samhour = samhour;
	}
	public String getSammin() {
		return sammin;
	}
	public void setSammin(String sammin) {
		this.sammin = sammin;
	}
	public String getSamsec() {
		return samsec;
	}
	public void setSamsec(String samsec) {
		this.samsec = samsec;
	}
	
	public Sample toDAO() {
		Sample sample = ModelFactory.createSample();
		sample.setLabel(this.label);
		sample.setRegion(this.region);
		BigDecimal lat = new BigDecimal(latitude);
		BigDecimal lon = new BigDecimal(longitude);
		sample.setLatitude(lat);
		sample.setLongitude(lon);
		
		
		sample.setMaterial(this.material);
		try {
			DateRes dtr = calcDateRes(this);
			sample.setDateRes(dtr.getResolution());
			sample.setDateTaken(dtr.getDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BigDecimal depthDecimal = new BigDecimal(this.depth);
		sample.setDepth(depthDecimal, this.depthunit);
		
		return sample;
	}
	
	public static SampleDTO fromDAO(Sample sample) {
		SampleDTO rv = new SampleDTO();
		rv.label = sample.getLabel();
		rv.region = sample.getRegion();
		BigDecimal lat = sample.getLatitude();
		if(lat != null) {
			rv.latitude = lat.toPlainString();
		}
		BigDecimal lon = sample.getLongitude();
		if(lon != null) {
			rv.longitude = lon.toPlainString();
		}
		
		DateResolution res = sample.getDateRes();
		int idx = 0;
		for(int i=0; i<dr_arr.length; i++) {
			if(dr_arr[i].equals(res)) {
				idx = i;
			}
		}
		
		Date date = sample.getDateTaken();
		if (date != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			rv.samyear = String.valueOf(c.get(Calendar.YEAR));
			if (idx > 0) {
				rv.sammonth = String.format("%02d", c.get(Calendar.MONTH) + 1);
			}
			if (idx > 1) {
				rv.samday = String.format("%02d", c.get(Calendar.DATE));
			}
			if (idx > 2) {
				rv.samhour = String.format("%02d", c.get(Calendar.HOUR_OF_DAY));
			}
			if (idx > 3) {
				rv.sammin = String.format("%02d", c.get(Calendar.MINUTE));
			}
			if (idx > 4) {
				rv.samsec = String.format("%02d", c.get(Calendar.SECOND));
			}
		}
		
		if(sample.getDepth() != null) {
			rv.depth = sample.getDepth().toPlainString();
			rv.depthunit = sample.getDepthUnit();
		}
		
		return rv;
	}	
	
	// date resolution conversions 
	public static class DateRes {
		private DateResolution resolution;
		private Date date;
		
		public DateRes(DateResolution resolution, Date date) {
			this.resolution = resolution;
			this.date = date;
		}
		public DateResolution getResolution() {
			return resolution;
		}
		public Date getDate() {
			return date;
		}
	}
	
	private static final DateResolution [] dr_arr = new DateResolution[] {
			DateResolution.YEAR,
			DateResolution.MONTH,
			DateResolution.DAY,
			DateResolution.HOUR,
			DateResolution.MINUTE,
			DateResolution.SECOND
	};
	
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public static DateRes calcDateRes(SampleDTO dto) throws ParseException {
		String[] arr = new String [] {
				dto.getSamyear(), 
				dto.getSammonth(),
				dto.getSamday(),
				dto.getSamhour(),
				dto.getSammin(),
				dto.getSamsec()
		};
		
		DateResolution res = null;		
		for(int i=0; i<arr.length; i++) {
			if(arr[i] == null || arr[i].trim().length()==0) {
				break;
			} else {
				res = dr_arr[i];
			}
		}
		
		String s = String.format("%04d/%02d/%02d %02d:%02d:%02d",
				parseInt(dto.getSamyear(), 2012), parseInt(dto.getSammonth(), 1),
				parseInt(dto.getSamday(), 1), parseInt(dto.getSamhour(), 0),
				parseInt(dto.getSammin(), 0), parseInt(dto.getSamsec(), 0));
		Date date = DATE_FORMAT.parse(s);
		return new DateRes(res, date);
	}
	
	private static Integer parseInt(String str, Integer def) {
		try {
			return Integer.parseInt(str, 10);
		} catch (NumberFormatException e) {
		}
		return def;
	}
}
