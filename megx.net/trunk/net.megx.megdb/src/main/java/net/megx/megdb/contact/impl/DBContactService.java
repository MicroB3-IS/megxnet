package net.megx.megdb.contact.impl;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.contact.ContactService;
import net.megx.megdb.contact.mappers.ContactMapper;
import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.model.contact.Contact;

public class DBContactService extends BaseMegdbService implements
		ContactService {

	@Override
	public String storeContact(final Contact contact)
			throws DBGeneralFailureException {

		return doInTransaction(new DBTask<ContactMapper, String>() {

			@Override
			public String execute(ContactMapper mapper) throws Exception {
				mapper.storeContact(contact);
				String sender = contact.getName();
				return sender;
			}

		}, ContactMapper.class);
	}
}
