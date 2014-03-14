package net.megx.megdb.wod05.impl;

import java.util.List;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.wod05.Wod05Service;
import net.megx.megdb.wod05.mappers.Wod05Mapper;
import net.megx.model.wod05.Wod05;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBWod05Service extends BaseMegdbService implements Wod05Service {
	private static final Log log = LogFactory.getLog(DBWod05Service.class);

	@Override
	public List<Wod05> getWod05Data(final Double latitude,
			final Double longitude, final Double depth, final Double buffer)
			throws Exception {
		return doInSession(new BaseMegdbService.DBTask<Wod05Mapper, List<Wod05>>() {
			@Override
			public List<Wod05> execute(Wod05Mapper mapper) throws Exception {
				log.debug("Executing getWod05Data ...");
				return mapper.getWod05Data(latitude, longitude, depth, buffer);
			}
		}, Wod05Mapper.class);
	}
}
