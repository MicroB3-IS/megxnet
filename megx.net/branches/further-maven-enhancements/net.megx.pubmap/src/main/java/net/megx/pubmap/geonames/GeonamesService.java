package net.megx.pubmap.geonames;

import net.megx.pubmap.geonames.model.Place;

public interface GeonamesService {

	public Place getPlaceName(String lat, String lon);

	public Place getCoordinates(String placeName, String worldRegion);

}
