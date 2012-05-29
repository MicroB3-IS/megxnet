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


package org.microb3.security.auth.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.microb3.security.auth.Role;
import org.microb3.security.auth.UserInfo;


public interface UserMapper {
	

	public UserInfo getUserByUserId(String userId) throws Exception;

	public UserInfo getUserByConsumerKey(String key) throws Exception;

	public UserInfo getUserForUsernameAndPassword(
			@Param("username") String username,
			@Param("password") String password) throws Exception;

	public UserInfo getUserByUniqueIdentifier(String uniqueIdentifier)
			throws Exception;
	
	public void addUser(UserInfo info) throws Exception;
	public void updateUser(UserInfo info) throws Exception;
	public void removeUser(String userId) throws Exception;
	
	
	public void revokeRole(@Param("userId")String userId, @Param("roleId")int roleId) throws Exception;
	public void grantRole(@Param("userId")String userId, @Param("roleId")int roleId) throws Exception;
	public int createRole(Role role) throws Exception;
	public List<Role> getAllRoles() throws Exception;
	public List<Role> getRolesForNames(@Param("names")List<String> names) throws Exception;
	public void removeRole(int roleId) throws Exception;
	public Role getRole(int roleId) throws Exception;
}
