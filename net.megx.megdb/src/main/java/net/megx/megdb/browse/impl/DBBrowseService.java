package net.megx.megdb.browse.impl;

import java.util.List;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.browse.BrowseService;
import net.megx.megdb.browse.mappers.BrowseMapper;
import net.megx.model.browse.MetagenomesRow;
import net.megx.model.browse.PhagesRow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBBrowseService extends BaseMegdbService implements BrowseService {
	private static final Log log = LogFactory.getLog(DBBrowseService.class);

	@Override
	public List<MetagenomesRow> getMetagenomes() throws Exception {
		return doInSession(new BaseMegdbService.DBTask<BrowseMapper, List<MetagenomesRow>>() {
			@Override
			public List<MetagenomesRow> execute(BrowseMapper mapper) throws Exception {
				log.debug("Executing getMetagenomes ...");
				return mapper.getMetagenomes();
			}
		}, BrowseMapper.class);
	}

	@Override
	public List<PhagesRow> getPhages() throws Exception {
		return doInSession(new BaseMegdbService.DBTask<BrowseMapper, List<PhagesRow>>() {
			@Override
			public List<PhagesRow> execute(BrowseMapper mapper) throws Exception {
				log.debug("Executing getPhages ...");
				return mapper.getPhages();
			}
		}, BrowseMapper.class);
	}

}
