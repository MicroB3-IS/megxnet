package net.megx.pubmap.geonames;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
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
	public Place getPlaceName(String lat, String lon) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		InputStream instream = null;
		URI uri = null;
		Place place = new Place();
		place.setLat(lat);
		place.setLon(lon);

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

						double tmp = Integer.MAX_VALUE;
						double distance;

						double requestLon = Double.valueOf(lon);
						double requestLat = Double.valueOf(lat);

						for (Geoname geoname : geonamesList) {

							distance = Math.sqrt(Math.pow((requestLon - Double
									.valueOf(geoname.getLng())), 2)
									+ Math.pow((requestLat - Double
											.valueOf(geoname.getLat())), 2));

							if (tmp > distance) {
								tmp = distance;
								place.setPlaceName(geoname.getName());
								place.setWorldRegion(geoname.getCountryName());

							}
						}

					} else if (geonames.getOcean() != null) {

						place.setWorldRegion(geonames.getOcean().getName());

					} else if (geonames.getAddress() != null) {

						place.setWorldRegion(geonames.getAddress()
								.getCountryCode());

						if (!geonames.getAddress().getPlacename().isEmpty()) {

							place.setPlaceName(geonames.getAddress()
									.getPlacename());

						} else {
							place.setPlaceName(geonames.getAddress()
									.getStreet());
						}

					} else if (geonames.getCountryName() != null) {

						place.setWorldRegion(geonames.getCountryName());

					} else if (geonames.getStatus() != null) {

						throw new ClientProtocolException(
								"Geonames service status message: "
										+ geonames.getStatus().getMessage());

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

		} catch (URISyntaxException e) {
			log.error("Wrong URI", e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (ClientProtocolException e) {
			log.error("HTTPReq:ClientProtocolException ", e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (JAXBException e) {
			log.error("JAXBException ", e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			log.error("HTTPReq:IOException ", e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			log.error("Geonames getPlaceName service error: ", e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
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

	@Override
	public Place getCoordinates(String placeName, String worldRegion) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		InputStream instream = null;
		URI uri = null;
		Place place = new Place();

		try {
			uri = new URIBuilder().setScheme("http")
					.setHost("api.geonames.org").setPath("/search")
					.setParameter("q", placeName).setParameter("fuzzy", "0.8")
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

					if (geonames.getTotalResultsCount() != null) {

						if (geonames.getTotalResultsCount() > 0
								&& geonames.getGeonamesLst() != null) {

							List<Geoname> geonamesList = new ArrayList<Geoname>();
							geonamesList = geonames.getGeonamesLst();
							for (Geoname geoname : geonamesList) {

								if (geoname.getCountryCode()
										.equals(worldRegion)) {

									place.setPlaceName(geoname.getName());
									place.setWorldRegion(geoname
											.getCountryName());
									place.setLat(geoname.getLat());
									place.setLon(geoname.getLng());

								}
							}
							if (place.getPlaceName() == null) {
								throw new ClientProtocolException(
										"No results found for: " + placeName
												+ " in region: " + worldRegion);
							}

						} else if (geonames.getTotalResultsCount() == 0) {

							throw new ClientProtocolException(
									"No results found for: " + placeName);

						} else {
							throw new ClientProtocolException(
									"Malformed XML document");
						}

					} else if (geonames.getStatus() != null) {

						throw new ClientProtocolException(
								"Geonames service status message: "
										+ geonames.getStatus().getMessage());

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

		} catch (URISyntaxException e) {
			log.error("Wrong URI", e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (ClientProtocolException e) {
			log.error("HTTPReq:ClientProtocolException ", e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (JAXBException e) {
			log.error("JAXBException ", e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			log.error("HTTPReq:IOException ", e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			log.error("Geonames getCoordinates service error: ", e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
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
