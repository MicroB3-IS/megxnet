package net.megx.megdb.geopfam.impl;

import java.util.List;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.geopfam.GeoPfamService;
import net.megx.megdb.geopfam.mappers.GeoPfamMapper;
import net.megx.model.geopfam.GeoPfamRow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBGeoPfamService extends BaseMegdbService implements GeoPfamService {
	private static final Log log = LogFactory.getLog(GeoPfamService.class);
	
	@Override
	public List<GeoPfamRow> getAll() throws Exception {
		return doInSession(new BaseMegdbService.DBTask<GeoPfamMapper, List<GeoPfamRow>>() {
			@Override
			public List<GeoPfamRow> execute(GeoPfamMapper mapper) throws Exception {
				log.debug("Executing  GeoPfam getAll ...");
				return mapper.getAll();
			}
		}, GeoPfamMapper.class);
	}
}
