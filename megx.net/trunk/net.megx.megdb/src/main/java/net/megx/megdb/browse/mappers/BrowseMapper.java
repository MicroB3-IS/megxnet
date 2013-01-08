package net.megx.megdb.browse.mappers;

import java.util.List;

import net.megx.model.browse.MetagenomesRow;
import net.megx.model.browse.PhagesRow;

public interface BrowseMapper {
	public List<MetagenomesRow> getMetagenomes();
	public List<PhagesRow> getPhages();
}
