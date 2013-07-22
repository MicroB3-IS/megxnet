package net.megx.megdb.browse;

import java.util.List;

import net.megx.model.browse.GenomesRow;
import net.megx.model.browse.MarkerGenesRow;
import net.megx.model.browse.MetagenomesRow;
import net.megx.model.browse.PhagesRow;
import net.megx.model.browse.SamplingSitesRow;

public interface BrowseService {
	public List<GenomesRow> getGenomes() throws Exception;
	public List<MetagenomesRow> getMetagenomes() throws Exception;
	public List<PhagesRow> getPhages() throws Exception;
	public List<MarkerGenesRow> getMarkerGenes() throws Exception;
	public List<SamplingSitesRow> getSamplingSites() throws Exception;
}
