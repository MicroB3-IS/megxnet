package net.megx.megdb.contact;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.model.contact.Contact;

public interface ContactService {

	public String storeContact(Contact contact)
			throws DBGeneralFailureException;

}
