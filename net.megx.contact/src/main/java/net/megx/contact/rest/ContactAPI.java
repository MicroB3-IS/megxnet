package net.megx.contact.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.megx.megdb.contact.ContactService;
import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.model.contact.Contact;
import net.megx.ws.core.BaseRestService;

@Path("v1/contact/v1.0.0")
public class ContactAPI extends BaseRestService {

	private ContactService service;

	public ContactAPI(ContactService service) {
		this.service = service;

	}

	@Path("store-contact")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response storeContactMail(@FormParam("email") String email,
			@FormParam("name") String name,
			@FormParam("comment") String comment,
			@Context HttpServletRequest request) {

		String saveContact;
		Date date = Calendar.getInstance().getTime();
		Contact contact = new Contact();
		contact.setEmail(email);
		contact.setName(name);
		contact.setCreated(date);
		contact.setComment(comment);
		String url = "";
		URI uri = null;

		try {
			saveContact = service.storeContact(contact);
			url = request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort()
					+ request.getContextPath();
			uri = new URI(url);
		} catch (DBGeneralFailureException e) {
			log.error("Could not save mail", e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (URISyntaxException e) {
			log.error("Wrong URI" + url, e);
		} catch (Exception e) {
			log.error("Error occured", e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		return Response.seeOther(uri).build();
	}

}
