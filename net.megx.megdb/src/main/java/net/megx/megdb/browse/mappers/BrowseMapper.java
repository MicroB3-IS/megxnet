package net.megx.megdb.browse.mappers;

import java.util.List;

import net.megx.model.browse.GenomesRow;
import net.megx.model.browse.MarkerGenesRow;
import net.megx.model.browse.MetagenomesRow;
import net.megx.model.browse.PhagesRow;
import net.megx.model.browse.SamplingSitesRow;

public interface BrowseMapper {
	public List<GenomesRow> getGenomes();
	public List<MetagenomesRow> getMetagenomes();
	public List<PhagesRow> getPhages();
	public List<MarkerGenesRow> getMarkerGenes();
	public List<SamplingSitesRow> getSamplingSites();
}
