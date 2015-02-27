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

import org.apache.ibatis.annotations.Param;

import net.megx.security.auth.model.Token;


public interface TokenMapper {
	public Token getToken(String value);
	public List<Token> getTokensForUser(String userid);
	public Token getTokenForUserAndConsumer(
			@Param("userId") String userId,
			@Param("consumerKey") String consumerKey
			);
	public void saveToken(Token token);
	public void removeToken(String value);
	public int removeTokensBeforeTimestamp(long timestamp);
}
