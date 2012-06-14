package net.megx.pubmap.rest.json;

import net.megx.model.Author;

public class AuthorDTO {
	private String forename;
	private String initials;
	private String surename;
	private String position;
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public static AuthorDTO fromDAO(Author author) {
		AuthorDTO rv = new AuthorDTO();
		rv.forename = author.getFirstName();
		rv.initials = author.getInitials();
		rv.surename = author.getLastName();
		// rv.position = author.get AUTHR POSITION?;
		rv.position = "TODO: get FROM DAO";
		return rv;
	}
}
