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
package com.iw.megx.ws.mpidb;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.iw.megx.ws.dto.mpiws.GenomeOverview;
import com.iw.megx.ws.service.mpiws.MpiWsService;

public class Tester {

	/**
	 * @param args
	 * @throws Exception 
	 */
	
	private static void insertPhages(MpiWsService service, String version) throws Exception{
		GenomeOverview g = new GenomeOverview();
		g.setEnvolite("Prochlorococcus phage P-SSM4");
		g.setLocation("31.8 N 64.27 W");
		g.setDepth("100 m");
		g.setDateSampled("2000-06-06");
		g.setEnvolite("marine habitat");
		g.setTaxID("268747");
		g.setGenomePID("15136");
		
		service.insertPhagesList(g, version);
		
	}
	
	private static void testDB(MpiWsService service, String version) throws Exception{
		
//		//TODO: test where clause on data
		//List<GenomeOverview> lgp = service.getPhages("site_name='test'","",10,10,version);
		List<GenomeOverview> lgp = service.getPhages("","",0,100,version);
		System.out.println(lgp);
//		
//		//TODO: test where clause on data
//		List<GenomeOverview> lgg = service.getGenomes("site_name='test'","",version);
//		System.out.println(lgg);
//			
//			//TODO: test where clause on data and correct table / view
////		//ERROR > web_r8.sites doesn't exist in devdb
////		List<SamplingSite> lgss = service.getSites(version);
////		System.out.println(lgss);		
////		
////		// ERROR web_r8.silva_overview_t doesn't exist but without _t exists
////		List<RRNA> lgso = service.getSilvaOverview(version);
////		System.out.println(lgso);
//		
//		List<Sample> lgs = service.getSamples("2","1",version);
//		System.out.println(lgs);
//		
//		List<Metagenome> lgm = service.getMetagenomes("site_name='dd'","",version);
//		System.out.println(lgm);		
//		
//		String brs = service.blastRunStatus("0", "0", version);
//		System.out.println(brs);
//		
////		// Constraint problems for inserting
////		BlastJobParams bjp = new BlastJobParams();
////		BlastJob bj = service.insertBlastRun(bjp, version);
////		System.out.println(bj);
//		
//		//ERROR: schema "stage_r6" does not exist
//		List<WOA5> woa = service.woa05_idw_ip(0, 0, 0, "", (short) 1, version);
//		System.out.println(woa);
//		
//		List<WOD5> wod = service.wod05_idw_ip(0, 0, 0, 0, version);
//		System.out.println(wod);
//		
//		List<Silva> s = service.get_silva_by_geom("", version);
//		System.out.println(s);

//		// fix serializing
//		List<IsMarine> im = service.isMarine("0", "0", version);
//		System.out.println(im);
		
//		List<HabLiteDist> hld = service.getHabLiteDist("genome", version);
//		System.out.println(hld);
		
//		long gc = service.getGenomesCount("", version);
//		System.out.println(gc);
		
	}
	
	private static MpiWsService testCfg() throws Exception{
		/*ApplicationContext springCtx = new BundleApplicationContext(this,
				context, new String[] { "cfg/applicationContext.xml",
						"cfg/dao-beans.xml", "cfg/service-beans.xml" });*/
		
		ApplicationContext springCtx =  new ClassPathXmlApplicationContext( new String[] { "cfg/applicationContext.xml",
					"cfg/dao-beans.xml", "cfg/service-beans.xml" });
		MpiWsService service = (MpiWsService) springCtx.getBean("mpiWsService");
		
		//List<GenomeOverview> lgo = service.getPhages("2_1");
		//System.out.println(lgo);
		
		 return service;
		
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String version = "2_1";
		//String version = "mock";
		
		MpiWsService service = testCfg();
		testDB(service, version);
		//insertPhages(service, version);
		
		
	}

}
