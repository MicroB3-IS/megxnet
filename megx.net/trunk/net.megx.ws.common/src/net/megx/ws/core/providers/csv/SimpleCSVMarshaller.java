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
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

public class SimpleCSVMarshaller implements CSVMarshaller<CSVDocumentInfo> {

    public void marshall(CSVDocumentInfo t, Class<?> type, OutputStream stream)
            throws Exception {
        CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(stream),
                t.getSeparator(), t.getQuoteChar(), t.getLineEnd());
        if (t.isWriteHeaderColumns()) {
            csvWriter.writeNext(getHeaderColumns(t.getColumnsMapping(), t));
        }

        List<?> data = t.getData();
        for (Object bean : data) {
            csvWriter.writeNext(getBeanAsRow(bean, t.getColumnsMapping()));
        }
        csvWriter.flush();
        csvWriter.close();
    }

    private String[] getBeanAsRow(Object bean, List<PropertyMapping> mappings)
            throws Exception {
        String[] row = new String[mappings.size()];
        int i = 0;
        for (PropertyMapping m : mappings) {
            Method getter = m.getDescriptor().getReadMethod();
            if (getter != null) {
                Object value = getter.invoke(bean);
                if (value != null)
                    row[i] = value.toString();
            }
            i++;
        }
        return row;
    }

    private String[] getHeaderColumns(List<PropertyMapping> mappings,
            CSVDocumentInfo info) throws Exception {
        String[] headers = new String[mappings.size()];
        int i = 0;
        for (PropertyMapping m : mappings) {
            headers[i] = ColumNameFormatUtils.formatStr(m.getMappedName(),
                    info.getFormat());
            i++;
        }
        return headers;
    }
}
