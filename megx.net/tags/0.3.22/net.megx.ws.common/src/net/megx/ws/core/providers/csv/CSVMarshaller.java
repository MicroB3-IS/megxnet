/*
 * Copyright 2011 Max Planck Institute for Marine Microbiology 

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


package net.megx.ws.core.providers.csv;

import java.io.OutputStream;

/**
 * Comma Separated Values document marshaller.
 * 
 * @author Pavle Jonoski
 *
 * @param <T> type of object used as context for marshalling
 */
public interface CSVMarshaller<T> {
	/**
	 * Marshalls context to output stream as CSV document.
	 * @param t - object used as context for marshalling
	 * @param type - context type
	 * @param stream - the stream to marshall to
	 * @throws Exception
	 */
	public void marshall(T t, Class<?> type, OutputStream stream) throws Exception;
}
