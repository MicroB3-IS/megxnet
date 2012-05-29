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

public interface UserService {
	public User getUserByUserId(String userId) throws Exception;
	public User getUserByConsumerKey(String consumerKey) throws Exception;
	public User getUser(String username, String password) throws Exception;
	public User getUser(String uniqueIdentifier) throws Exception;
	public User addUser(User info) throws Exception;
	public User updateUser(User userInfo) throws Exception;
	public User removeUser(String userid) throws Exception;
	public Role    createRole(Role role)throws Exception;
	public List<Role> getAvailableRoles() throws Exception;
	public List<Role> getDefaultRoles() throws Exception;
	public Role removeRole(int roleId) throws Exception;
}
