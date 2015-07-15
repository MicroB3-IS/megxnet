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

import java.beans.PropertyDescriptor;

public class PropertyMapping {
    private String mappedName;
    private PropertyDescriptor descriptor;

    public PropertyMapping(String mappedName, PropertyDescriptor descriptor) {
        super();
        this.mappedName = mappedName;
        this.descriptor = descriptor;
    }

    public String getMappedName() {
        return mappedName;
    }

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
    }

    public PropertyDescriptor getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(PropertyDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((mappedName == null) ? 0 : mappedName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PropertyMapping other = (PropertyMapping) obj;
        if (mappedName == null) {
            if (other.mappedName != null)
                return false;
        } else if (!mappedName.equals(other.mappedName))
            return false;
        return true;
    }
}
