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


package net.megx.security.auth.services.db;

import java.util.List;

import net.megx.security.auth.model.Permission;
import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;
import net.megx.security.auth.model.UserVerification;

import org.apache.ibatis.annotations.Param;


public interface UserMapper {
	

	public User getUserByUserId(String userId) throws Exception;

	public User getUserByConsumerKey(String key) throws Exception;

	public User getUserForUsernameAndPassword(
			@Param("username") String username,
			@Param("password") String password) throws Exception;

	//public User getUserByUniqueIdentifier(String uniqueIdentifier)
	//		throws Exception;
	
	public void addUser(User user) throws Exception;
	public void updateUser(User user) throws Exception;
	public void removeUser(String login) throws Exception;
	
	
	public void revokeRole(@Param("login")String login, @Param("role")String role) throws Exception;
	public void grantRole(@Param("login")String login, @Param("role")String role) throws Exception;
	public int createRole(Role role) throws Exception;
	
	public List<Role> getAllRoles() throws Exception;
	
	public List<Role> getAllRolesPaginated(
			@Param("offset") int offset, 
			@Param("limit")  int limit) throws Exception;
	
	public int getRolesCount() throws Exception;
	
	public List<Role> getRolesForNames(@Param("names")List<String> names) throws Exception;
	
	
	public void removeRole(String role) throws Exception;
	public Role getRole(String role) throws Exception;
	public List<User> getUsers() throws Exception; //FIXME: pagination
	public List<User> getUsersWithRole(String role) throws Exception;
	public List<User> filterUsers(
			@Param("username")String username, 
			@Param("maxResults") int maxResults) throws Exception;
	
	public void createVerification(UserVerification verification) throws Exception;
	public UserVerification getVerification(
			@Param("verificationValue")String value, 
			@Param("ttl") long ttl) throws Exception;
	
	public void deleteVerification(String verification) throws Exception;
	public void cleanupVerifications(long ttl) throws Exception;
	
	
	public void updateRole(
			@Param("oldLabel") String oldLabel,
			@Param("role") Role role) throws Exception;
	public void grantPermission(
			@Param("role") String role, 
			@Param("permission") String permission) throws Exception;
	public void revokePermission(
			@Param("role") String role, 
			@Param("permission") String permission) throws Exception;
	
	public void removePermission(String permission) throws Exception;
	
	public List<Permission> getAllPermissions() throws Exception;
	
	// users pagination
	public List<User> filterUsersWithRole(
			@Param("conditions") List<FilterCondition> conditions,
			@Param("role") String role,
			@Param("start") int start,
			@Param("limit") int limit) throws Exception;
	public int countFilteredResultsWithRole(
			@Param("conditions") List<FilterCondition> conditions,
			@Param("role") String role) throws Exception;
}
