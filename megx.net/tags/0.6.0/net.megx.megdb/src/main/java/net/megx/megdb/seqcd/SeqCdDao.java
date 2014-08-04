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


package net.megx.megdb.seqcd;

import java.util.List;

import net.megx.model.ws.seqcd.ContextualDataItem;

public interface SeqCdDao {
	public List<ContextualDataItem> getItemsByMD5sum(String seqType, String md5, String cdi) throws Exception;
	public List<ContextualDataItem> getItemsByDnaSequence(String seqType, String seq, String cdi) throws Exception;
	public List<ContextualDataItem> getItemsBySequenceIdentifier(String seqType, String id, String cdi) throws Exception;
	public Object getListOfAuthorities() throws Exception;
}
