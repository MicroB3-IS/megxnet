package net.megx.pubmap.geonames;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.megx.pubmap.geonames.model.Geoname;
import net.megx.pubmap.geonames.model.Geonames;
import net.megx.pubmap.geonames.model.Place;
import net.megx.ws.core.BaseRestService;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class GeonamesServiceImpl extends BaseRestService implements
		GeonamesService {

	@Override
	public Place getPlaceName(String lat, String lon)
			throws ClientProtocolException, URISyntaxException, JAXBException,
			IOException, Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		InputStream instream = null;
		URI uri = null;
		Place place = new Place();

		try {
			uri = new URIBuilder().setScheme("http")
					.setHost("api.geonames.org").setPath("/extendedFindNearby")
					.setParameter("lat", lat).setParameter("lng", lon)
					.setParameter("username", "megx").build();

			HttpGet httpget = new HttpGet(uri);
			response = httpclient.execute(httpget);
			int status = response.getStatusLine().getStatusCode();

			if (status >= 200 && status < 300) {

				HttpEntity entity = response.getEntity();

				if (entity != null) {

					instream = entity.getContent();
					JAXBContext context = JAXBContext
							.newInstance(Geonames.class);
					Unmarshaller um = context.createUnmarshaller();
					Geonames geonames = (Geonames) um.unmarshal(instream);

					if (geonames.getGeonamesLst() != null) {

						List<Geoname> geonamesList = new ArrayList<Geoname>();
						geonamesList = geonames.getGeonamesLst();
						place.setPlaceName(geonamesList.get(
								geonamesList.size() - 1).getName());
						place.setCountry(geonamesList.get(
								geonamesList.size() - 1).getCountryName());

					} else if (geonames.getOcean() != null) {

						place.setPlaceName(geonames.getOcean().getName());

					} else if (geonames.getAddress() != null) {
						
						place.setCountry(geonames.getAddress().getCountryCode());
						
						if(!geonames.getAddress().getPlacename().isEmpty()){
							
							place.setPlaceName(geonames.getAddress().getPlacename());
							
						} else {
							place.setPlaceName(geonames.getAddress().getStreet());
						}
						
					} else if (geonames.getCountryName() != null) {

						place.setCountry(geonames.getCountryName());

					} else {
						throw new ClientProtocolException(
								"Malformed XML document");
					}
					EntityUtils.consume(entity);
				}
			} else {
				throw new ClientProtocolException(
						"Unexpected response status: " + status);
			}

		} finally {
			try {
				instream.close();
				response.close();
			} catch (IOException e) {
				log.error("HTTPReq:Exception closing response ", e);
			}
		}
		return place;
	}

}
