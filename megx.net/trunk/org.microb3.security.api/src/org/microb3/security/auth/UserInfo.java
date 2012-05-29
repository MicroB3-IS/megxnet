/*
 * Copyright 2011 Max Planck Institute for Marine Microbiology 
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */


package org.microb3.security.auth;

import java.util.List;

public class UserInfo {
	
	
	
	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	//--------------- begin here
	
	private String userId;
	private String fullName;
	private String password;
	private String providerName;
	private String eMail;
	private String address;
	private String city;
	private int zipCode;
	private String country;
	private String birthDate;
	private String uniqueIdentifier;
	
	private List<Role> roles;
	
	private boolean accountExpired;
	private boolean accountLocked;
	private boolean credentialsExpired;
	private boolean enabled = true;
	
	
	private String firstName;
	private String lastName;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}
	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public boolean isAccountExpired() {
		return accountExpired;
	}
	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}
	public boolean isAccountLocked() {
		return accountLocked;
	}
	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}
	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}
	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	public UserInfo(boolean accountExpired, boolean accountLocked,
			boolean credentialsExpired, boolean enabled, String userId,
			String fullName, String password, String providerName,
			String eMail, String address, String city, int zipCode,
			String country, String birthDate, String uniqueIdentifier,
			String firstName, String lastName,
			List<Role> roles) {
		super();
		this.accountExpired = accountExpired;
		this.accountLocked = accountLocked;
		this.credentialsExpired = credentialsExpired;
		this.enabled = enabled;
		this.userId = userId;
		this.fullName = fullName;
		this.password = password;
		this.providerName = providerName;
		this.eMail = eMail;
		this.address = address;
		this.city = city;
		this.zipCode = zipCode;
		this.country = country;
		this.birthDate = birthDate;
		this.uniqueIdentifier = uniqueIdentifier;
		this.roles = roles;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", fullName=" + fullName
				+ ", password=(*******), providerName=" + providerName
				+ ", eMail=" + eMail + ", address=" + address + ", city="
				+ city + ", zipCode=" + zipCode + ", country=" + country
				+ ", birthDate=" + birthDate + ", uniqueIdentifier="
				+ uniqueIdentifier + ", roles=" + roles + ", accountExpired="
				+ accountExpired + ", accountLocked=" + accountLocked
				+ ", credentialsExpired=" + credentialsExpired + ", enabled="
				+ enabled + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((uniqueIdentifier == null) ? 0 : uniqueIdentifier.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfo other = (UserInfo) obj;
		if (uniqueIdentifier == null) {
			if (other.uniqueIdentifier != null)
				return false;
		} else if (!uniqueIdentifier.equals(other.uniqueIdentifier))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
	//--------------- end here
	
	
	
}
