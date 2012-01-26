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
package org.chon.megx.net.services.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.chon.megx.net.services.ui.impl.GenomesServiceMockImpl;
import org.chon.megx.net.services.ui.pub.Genome;
import org.chon.megx.net.services.ui.pub.IGenomes;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		// /////////////////////////////////////
		URL megx_genomes = context.getBundle().getResource(
				"resources/megx_genomes.csv");
		List<Genome> genomesList = readCSV(megx_genomes.openStream(),
				new String[] { "organismName", "location", "depth",
						"dateSampled", "envOLite", "texId", "genomePID" },
				Genome.class);
		IGenomes svc = new GenomesServiceMockImpl(genomesList);

		// /////////////////////////////////////
		context.registerService(IGenomes.class.getName(), svc, null);
	}

	private static <T> List<T> readCSV(InputStream is, String[] strings,
			Class<T> clzz) throws InstantiationException,
			IllegalAccessException, IOException, SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			InvocationTargetException {
		List<T> ls = new ArrayList<T>();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		while ((line = br.readLine()) != null) {
			T obj = clzz.newInstance();
			String[] values = line.split(",");
			for (int i = 0; i < values.length; i++) {
				String prop = (strings[i].charAt(0) + "").toUpperCase()
						+ strings[i].substring(1);
				Method setter = clzz.getMethod("set" + prop, String.class);
				setter.invoke(obj, values[i]);
			}
			ls.add(obj);
		}
		return ls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {

	}

}
