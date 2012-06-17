package net.megx.model.impl;

import net.megx.model.Author;

public class PubMapAuthor implements Author {

	private String firstName;
	private String lastName;
	private String initials = "";
	private Gender sex;
	private String institute = "";


	@Override
	public String getFirstName() {
		return this.firstName;
	}

	@Override
	public void setFirstName(String firstName) {
		if (firstName != null) {
			this.firstName = firstName.trim();
		}
	}

	@Override
	public String getLastName() {
		return this.lastName;

	}

	@Override
	public void setLastName(String lastName) {
		// TODO firstuppercase test
		if (lastName != null) {
			this.lastName = lastName.trim();
		}
	}

	@Override
	public String getInitials() {
		return this.initials;
	}

	@Override
	public void setInitials(String initials) {
		if (initials != null) {
			this.initials = initials.trim();
		}
	}

	@Override
	public String getSex() {
		return this.sex.toString();
	}

	@Override
	public void setSex(String sex) {
		try {
			this.sex = Enum.valueOf(Gender.class, sex.trim().toUpperCase()
					.replace(' ', '_'));
		} catch (IllegalArgumentException e) {
			StringBuilder hint = new StringBuilder();
			for (Gender g : Gender.values()) {
				hint.append("|" + g);
			}
			throw new IllegalArgumentException(
					"Gender not known and not set. Please use one of " + hint,
					e);
		}
	}

	public void setSex(Gender sex) {
		this.sex = sex;
	}
	
	public void setSexCode(String sex) {
		switch (Integer.parseInt(sex)) {
		case 1:
			this.sex = Gender.MALE;
			break;

		case 2:
			this.sex = Gender.FEMALE;
			break;

		case 9:
			this.sex = Gender.NOT_APPLICABLE;
			break;
		default:
			this.sex = Gender.UNKNOWN;
			break;
		}
	}

	public String getSexCode() {
		return String.valueOf(sex.getCode());
	}
	
	
	
	@Override
	public String getInstitute() {
		return this.institute;
	}

	@Override
	public void setInstitute(String institute) {
		if (institute != null) {
			this.institute = institute.trim();
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PubMapAuthor [firstName=").append(firstName)
				.append(", lastName=").append(lastName).append(", initials=")
				.append(initials).append(", sex=").append(sex)
				.append(", institute=").append(institute).append("]");
		return builder.toString();
	}

}
