package net.megx.megdb.browse;

import java.util.List;

import net.megx.model.browse.MetagenomesRow;
import net.megx.model.browse.PhagesRow;

public interface BrowseService {
	public List<MetagenomesRow> getMetagenomes() throws Exception;
	public List<PhagesRow> getPhages() throws Exception;
}
