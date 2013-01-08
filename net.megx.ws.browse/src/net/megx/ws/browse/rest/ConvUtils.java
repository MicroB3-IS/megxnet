package net.megx.ws.browse.rest;

import java.util.ArrayList;
import java.util.List;

import net.megx.model.browse.MetagenomesRow;

public class ConvUtils {

	public static MetagenomesUiTblRow toUiTableRow(MetagenomesRow row) {
		MetagenomesUiTblRow rv = new MetagenomesUiTblRow();
		//TODO: check conversion ... 
		rv.setDatasetName(row.getEntityName());
		rv.setDateSampled(row.getDateTaken());
		rv.setDepth(row.getDepth());
		rv.setEnvOLite(row.getHabUri());
		String loc = String.format("%s (%s)", row.getSiteName(), row.getLatlon());
		rv.setLocation(loc);
		return rv;
	}
	
	public static List<MetagenomesUiTblRow> toUiTableList(
			List<MetagenomesRow> metagenomesList) {
		List<MetagenomesUiTblRow> rv = new ArrayList<MetagenomesUiTblRow>();
		for(MetagenomesRow r : metagenomesList) {
			rv.add(toUiTableRow(r));
		}
		return rv;
	}

}
