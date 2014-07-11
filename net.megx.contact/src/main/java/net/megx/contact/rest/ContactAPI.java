package net.megx.contact.rest;

import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.megx.megdb.contact.ContactService;
import net.megx.model.contact.Contact;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;


@Path("v1/contact/v1.0.0")
public class ContactAPI extends BaseRestService {
	
	private ContactService service;
	
	public ContactAPI(ContactService service) {
		this.service = service;
		
	}
	
	@Path("mail")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String storeContactMail( @FormParam("email") String email,
            @FormParam("name") String name, @FormParam("comment") String comment){
		
		if (email == null ) {
			return toJSON(new Result<String>(true, "Mail not provided",
                    "bad-request"));
        }
		String saveContact = "";
		Date date = Calendar.getInstance().getTime();
		Contact contact = new Contact();
		contact.setEmail(email);
		contact.setName(name);
		contact.setCreated(date);
		contact.setComment(comment);
		
		 try {
			 saveContact = service.storeContact(contact);
	        } catch (Exception e) {
	         
	            log.error("Could not save mail", e);
	        }
				return saveContact;
				
		
	}

}
