package net.megx.megdb.contact.impl;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.BaseMegdbService.DBTask;
import net.megx.megdb.contact.ContactService;
import net.megx.megdb.contact.mappers.ContactMapper;
import net.megx.megdb.esa.mappers.ESAMapper;
import net.megx.model.contact.Contact;
import net.megx.model.esa.Sample;

public class DBContactService extends BaseMegdbService implements ContactService {

	@Override
	public String storeContact(final Contact contact) throws Exception {
		
		return doInTransaction(new DBTask<ContactMapper, String>() {

			@Override
			public String execute(ContactMapper mapper) throws Exception {
				String sender = "";
				mapper.storeContact(contact);
				sender = contact.getName();
				return sender;
			}
			
		}, ContactMapper.class);
	}
}
