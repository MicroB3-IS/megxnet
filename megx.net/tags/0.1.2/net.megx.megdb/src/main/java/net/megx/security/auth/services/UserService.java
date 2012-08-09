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


package net.megx.security.auth.services;

import java.util.List;

import net.megx.security.auth.model.PaginatedResult;
import net.megx.security.auth.model.Permission;
import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;
import net.megx.security.auth.model.UserVerification;

public interface UserService {
	public User getUserByUserId(String userId) throws Exception;
	public User getUserByConsumerKey(String consumerKey) throws Exception;
	public User getUser(String username, String password) throws Exception;
	@Deprecated
	public User getUser(String uniqueIdentifier) throws Exception;
	public List<User> getUsers() throws Exception;
	public List<User> getUsersWithRole(String role) throws Exception;
	public List<User> filterUsers(String username, int maxResults) throws Exception;
	
	public User addUser(User info) throws Exception;
	public UserVerification addPendingUser(User user) throws Exception;
	public void commitPending(User user, String userVerification, long ttl) throws Exception;
	public UserVerification getVerification(String verificationValue, long ttl) throws Exception;
	public User updateUser(User userInfo) throws Exception;
	public User removeUser(String userid) throws Exception;
	public Role    createRole(Role role)throws Exception;
	public PaginatedResult<Role> getAvailableRoles(int start, int limit, boolean all) throws Exception;
	@Deprecated
	public List<Role> getDefaultRoles() throws Exception;
	public Role removeRole(String role) throws Exception;
	
	public Role updateRole(String oldLabel, Role role) throws Exception;
	
	//public void test();
	public List<Permission> getAvailablePermissions() throws Exception;
	public void removePermission(String permission) throws Exception;
}
