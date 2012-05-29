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

import org.microb3.security.auth.Token;
import org.microb3.security.auth.TokenService;


public class DBTokenService implements TokenService {

	private TokenMapper mapper;

	public void setMapper(TokenMapper mapper) {
		this.mapper = mapper;
	}

	public Token getToken(String value) throws Exception {
		Token token = mapper.getToken(value);
		return token;
	}

	public Token saveToken(String value, Token token) throws Exception {
		mapper.saveToken(token);
		return token;
	}

	public Token removeToken(String value) throws Exception {
		Token token = getToken(value);
		mapper.removeToken(value);
		return token;
	}

	
	public int cleanupTokens(long beforeTimestamp) throws Exception {
		return mapper.removeTokensBeforeTimestamp(beforeTimestamp);
	}

	public List<Token> getTokensForUser(String userid) throws Exception {
		return mapper.getTokensForUser(userid);
	}

}
