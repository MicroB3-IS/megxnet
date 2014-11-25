package net.megx.pubmap.geonames;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBException;

import net.megx.pubmap.geonames.model.Place;

import org.apache.http.client.ClientProtocolException;

public interface GeonamesService {

	public Place getPlaceName(String lat, String lon)
			throws ClientProtocolException, URISyntaxException, JAXBException,
			IOException, Exception;

	public Place getCoordinates(String placeName, String worldRegion)
			throws ClientProtocolException, URISyntaxException, JAXBException,
			IOException, Exception;

}
