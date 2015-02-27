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

import java.sql.SQLException;
import java.util.List;

import net.megx.security.auth.model.Permission;
import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;
import net.megx.security.auth.model.UserVerification;

import org.apache.ibatis.annotations.Param;


public interface UserMapper {
	

	public User getUserByUserId(String userId) throws SQLException;

	public User getUserByConsumerKey(String key) throws SQLException;
	
	public boolean isUserIdAvailable(String id) throws SQLException;
	
	public User getUserForUsernameAndPassword(
			@Param("username") String username,
			@Param("password") String password) throws SQLException;

	//public User getUserByUniqueIdentifier(String uniqueIdentifier)
	//		throws SQLException;
	
	public void addUser(User user) throws SQLException;
	public void updateUser(User user) throws SQLException;
	public void updateUserId(
			@Param("oldId") String oldId, 
			@Param("newId") String newId) throws SQLException;
	public void removeUser(String login) throws SQLException;
	
	
	public void revokeRole(@Param("login")String login, @Param("role")String role) throws SQLException;
	public void grantRole(@Param("login")String login, @Param("role")String role) throws SQLException;
	public int createRole(Role role) throws SQLException;
	
	public List<Role> getAllRoles() throws SQLException;
	
	public List<Role> getAllRolesPaginated(
			@Param("offset") int offset, 
			@Param("limit")  int limit) throws SQLException;
	
	public int getRolesCount() throws SQLException;
	
	public List<Role> getRolesForNames(@Param("names")List<String> names) throws SQLException;
	
	
	public void removeRole(String role) throws SQLException;
	public Role getRole(String role) throws SQLException;
	public List<User> getUsers() throws SQLException; //FIXME: pagination
	
	public List<User> getUsersPaginated(@Param("offset") int offset,
											@Param("limit") int limit) throws SQLException;
	public int countUsers() throws SQLException;
	
	public List<User> getUsersWithRole(String role) throws SQLException;
	public List<User> filterUsers(
			@Param("username")String username, 
			@Param("maxResults") int maxResults) throws SQLException;
	
	public void createVerification(UserVerification verification) throws SQLException;
	public UserVerification getVerification(
			@Param("verificationValue")String value, 
			@Param("ttl") long ttl) throws SQLException;
	
	public void deleteVerification(String verification) throws SQLException;
	public void cleanupVerifications(long ttl) throws SQLException;
	
	
	public void updateRole(
			@Param("oldLabel") String oldLabel,
			@Param("role") Role role) throws SQLException;
	public void grantPermission(
			@Param("role") String role, 
			@Param("permission") String permission) throws SQLException;
	public void revokePermission(
			@Param("role") String role, 
			@Param("permission") String permission) throws SQLException;
	
	public void removePermission(String permission) throws SQLException;
	
	public List<Permission> getAllPermissions() throws SQLException;
	
	// users pagination
	public List<User> filterUsersWithRole(
			@Param("conditions") List<FilterCondition> conditions,
			@Param("role") String role,
			@Param("start") int start,
			@Param("limit") int limit) throws SQLException;
	public int countFilteredResultsWithRole(
			@Param("conditions") List<FilterCondition> conditions,
			@Param("role") String role) throws SQLException;
	
	public User getExternalUser(@Param("provider") String provider, @Param("externalId") String externalId) throws SQLException;
}
