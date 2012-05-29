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


package net.megx.security.auth.db;

import java.util.List;

import net.megx.security.auth.Token;
import net.megx.security.auth.TokenService;


public class DBTokenService extends BaseDBService implements TokenService {
	

	public Token getToken(final String value) throws Exception {
		return doInSession(new DBTask<TokenMapper, Token>() {
			@Override
			public Token execute(TokenMapper mapper) throws Exception {
				return mapper.getToken(value);
			}
			
		}, TokenMapper.class);
	}

	public Token saveToken(final String value, final Token token) throws Exception {
		
		return doInTransaction(new DBTask<TokenMapper, Token>() {
			@Override
			public Token execute(TokenMapper mapper) throws Exception {
				token.setValue(value);
				mapper.saveToken(token);
				return token;
			}
		}, TokenMapper.class);
	}

	public Token removeToken(final String value) throws Exception {
		return doInTransaction(new DBTask<TokenMapper, Token>() {
			@Override
			public Token execute(TokenMapper mapper) throws Exception {
				Token token = getToken(value);
				mapper.removeToken(value);
				return token;
			}
		}, TokenMapper.class);
		
	}

	
	public int cleanupTokens(final long beforeTimestamp) throws Exception {
		return doInTransaction(new DBTask<TokenMapper, Integer>() {
			@Override
			public Integer execute(TokenMapper mapper) throws Exception {
				return mapper.removeTokensBeforeTimestamp(beforeTimestamp);
			}
		}, TokenMapper.class);
	}

	public List<Token> getTokensForUser(final String userid) throws Exception {
		return doInSession(new DBTask<TokenMapper, List<Token>>() {
			@Override
			public List<Token> execute(TokenMapper mapper) throws Exception {
				return mapper.getTokensForUser(userid);
			}
		}, TokenMapper.class);
	}

}
