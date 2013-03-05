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

import net.megx.security.auth.model.Token;

public interface TokenService {
	public Token getToken(String value) throws Exception;
	public List<Token> getTokensForUser(String userid) throws Exception;
	public Token getToken(String userId, String consumerKey) throws Exception;
	public Token saveToken(String value, Token token) throws Exception;
	public Token removeToken(String value) throws Exception;
	public int cleanupTokens(long beforeTimestamp) throws Exception;
	
}
