package net.megx.pubmap.rest.json;

import net.megx.model.Author;
import net.megx.model.ModelFactory;

public class AuthorDTO {
	private String forename;
	private String initials;
	private String surename;
	private int position;
	
	public String getForename() {
		return forename;
	}
	public void setForename(String forename) {
		this.forename = forename;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public String getSurename() {
		return surename;
	}
	public void setSurename(String surename) {
		this.surename = surename;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public static AuthorDTO fromDAO(Author author, int pos) {
		AuthorDTO rv = new AuthorDTO();
		rv.forename = author.getFirstName();
		rv.initials = author.getInitials();
		rv.surename = author.getLastName();
		rv.position = pos;
		return rv;
	}
	public Author toAuthor() {
		Author author = ModelFactory.createAuthor();
		author.setFirstName( this.forename );
		author.setInitials( this.initials );
		author.setLastName( this.surename );
		return author;
	}
}
