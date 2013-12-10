package net.megx.megdb.wod05;

import java.util.List;

import net.megx.model.wod05.Wod05;

public interface Wod05Service {
	public List<Wod05> getWod05Data(Double latitude, Double longitude, Double depth, Double buffer) throws Exception;
}
