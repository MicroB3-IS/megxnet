package net.megx.ws.browse.rest.utils;

import java.util.ArrayList;
import java.util.List;

import net.megx.model.browse.GenomesRow;
import net.megx.model.browse.MarkerGenesRow;
import net.megx.model.browse.MetagenomesRow;
import net.megx.model.browse.PhagesRow;
import net.megx.model.browse.SamplingSitesRow;
import net.megx.ws.browse.rest.tables.GenomesUiTblRow;
import net.megx.ws.browse.rest.tables.MarkerGenesUiTblRow;
import net.megx.ws.browse.rest.tables.MetagenomesUiTblRow;
import net.megx.ws.browse.rest.tables.PhagesUiTblRow;
import net.megx.ws.browse.rest.tables.SamplingSitesUiTblRow;

public class ConvUtils {

	public static MetagenomesUiTblRow dbMetagenomesToUi(MetagenomesRow row) {
		MetagenomesUiTblRow rv = new MetagenomesUiTblRow();
		//TODO: check conversion ... 
		rv.setDatasetName(row.getEntityName());
		rv.setDateSampled(row.getDateTaken());
		rv.setDepth(row.getDepth());
		rv.setEnvOLite(row.getHabLite());
		String loc = String.format("%s (%s)", row.getSiteName(), row.getLatlon());
		rv.setLocation(loc);
		return rv;
	}
	
	public static List<MetagenomesUiTblRow> dbMetagenomesToUiList(
			List<MetagenomesRow> metagenomesList) {
		List<MetagenomesUiTblRow> rv = new ArrayList<MetagenomesUiTblRow>();
		for(MetagenomesRow r : metagenomesList) {
			rv.add(dbMetagenomesToUi(r));
		}
		return rv;
	}

	
	public static List<PhagesUiTblRow> dbPhagesToUiList(List<PhagesRow> phagesList) {
		List<PhagesUiTblRow> rv = new ArrayList<PhagesUiTblRow>();
		for(PhagesRow r : phagesList) {
			rv.add(dbPhagesToUi(r));
		}
		return rv;
	}

	private static PhagesUiTblRow dbPhagesToUi(PhagesRow r) {
		//TODO: check conversion ... 
		PhagesUiTblRow rv = new PhagesUiTblRow();
		rv.setDateSampled(r.getDateTaken());
		rv.setDepth(r.getDepth());
		rv.setEnvOLite(r.getHabLite());
		rv.setGenomePID(r.getPhgGprjId());
		rv.setLocation(r.getLatlon());
		rv.setOrganismName(r.getEntityName());
		rv.setTaxID(r.getPhgTaxid());
		return rv;
	}

	public static List<GenomesUiTblRow> dbGenomesToUiList(
			List<GenomesRow> genomesList) {
		List<GenomesUiTblRow> rv = new ArrayList<GenomesUiTblRow>();
		for(GenomesRow r : genomesList) {
			rv.add(dbGenomesToUi(r));
		}
		return rv;
	}

	private static GenomesUiTblRow dbGenomesToUi(GenomesRow r) {
		//TODO: check conversion ...
		GenomesUiTblRow rv = new GenomesUiTblRow();
		rv.setDateSampled(r.getDateTaken());
		rv.setDepth(r.getDepth());
		rv.setEnvOLite(r.getHabLite());
		rv.setGenomePID(r.getGpid());
		rv.setLocation(r.getLatlon());
		rv.setOrganismName(r.getEntityName());
		rv.setTaxID(r.getTaxid());
		return rv;
	}
	
	private static MarkerGenesUiTblRow dbMarkerGenesToUi(MarkerGenesRow r) {
		//TODO: check conversion ...
		MarkerGenesUiTblRow rv = new MarkerGenesUiTblRow();
		rv.setDateSampled(r.getDateTaken());
		rv.setDepth(r.getDepth());
		rv.setEnvOLite(r.getHabLite());
		rv.setLocation(r.getLatlon());
		rv.setNoOfSequences(r.getNumSeq());
		rv.setSamplingSite(r.getSiteName());
		return rv;
	}

	public static List<MarkerGenesUiTblRow> dbMarkerGenesToUiList(
			List<MarkerGenesRow> markerGenesList) {
		List<MarkerGenesUiTblRow> rv = new ArrayList<MarkerGenesUiTblRow>();
		for(MarkerGenesRow r : markerGenesList) {
			rv.add(dbMarkerGenesToUi(r));
		}
		return rv;
	}
	
	private static SamplingSitesUiTblRow dbSamplingSitesToUi(SamplingSitesRow r) {
		//TODO: check conversion ...
		SamplingSitesUiTblRow rv = new SamplingSitesUiTblRow();
		rv.setLocation(r.getLatlon());
		rv.setNoOfSamples(r.getNumSamples());
		rv.setSamplingSite(r.getSamplingSite());
		return rv;
	}

	public static List<SamplingSitesUiTblRow> dbSamplingSitesToUiList(
			List<SamplingSitesRow> samplingSitesList) {
		List<SamplingSitesUiTblRow> rv = new ArrayList<SamplingSitesUiTblRow>();
		for(SamplingSitesRow r : samplingSitesList) {
			rv.add(dbSamplingSitesToUi(r));
		}
		return rv;
	}

}
