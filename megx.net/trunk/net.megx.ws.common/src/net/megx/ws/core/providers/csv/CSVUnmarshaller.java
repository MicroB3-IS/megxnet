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

import java.io.InputStream;

/**
 * Unmarshaller from Comma Separated Values document {@link InputStream} to
 * Object of specified type.
 * 
 * @author Pavle Jonoski
 * 
 * @param <T>
 *            type of object to create
 */
public interface CSVUnmarshaller<T> {
    /**
     * Unmarshalls CVS {@link InputStream} to context type object
     * 
     * @param type
     *            - type of object to create
     * @param stream
     *            - CVS stream
     * @return created context object
     * @throws Exception
     */
    public T unmarshall(Class<?> type, InputStream stream) throws Exception;
}
