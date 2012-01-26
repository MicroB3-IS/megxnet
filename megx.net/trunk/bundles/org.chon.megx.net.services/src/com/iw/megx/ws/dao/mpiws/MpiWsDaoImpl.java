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
package com.iw.megx.ws.dao.mpiws;

import java.util.List;
import java.util.Map;

import com.iw.megx.ws.dao.mpiws.mappers.MpiWsMapper;
import com.iw.megx.ws.dto.mpiws.BlastJob;
import com.iw.megx.ws.dto.mpiws.BlastJobParams;
import com.iw.megx.ws.dto.mpiws.BlastStatus;
import com.iw.megx.ws.dto.mpiws.GenomeOverview;
import com.iw.megx.ws.dto.mpiws.HabLiteDist;
import com.iw.megx.ws.dto.mpiws.IsMarine;
import com.iw.megx.ws.dto.mpiws.Metagenome;
import com.iw.megx.ws.dto.mpiws.RRNA;
import com.iw.megx.ws.dto.mpiws.Sample;
import com.iw.megx.ws.dto.mpiws.SamplingSite;
import com.iw.megx.ws.dto.mpiws.Silva;
import com.iw.megx.ws.dto.mpiws.WOA5;
import com.iw.megx.ws.dto.mpiws.WOD5;

public class MpiWsDaoImpl implements MpiWsDao{

	private Map<String, MpiWsMapper> mappers;

	public void setMappers(Map<String, MpiWsMapper> mappers) {
		this.mappers = mappers;
	}

	public Map<String, MpiWsMapper> getMappers() {
		return mappers;
	}

	public List<String> test() throws Exception {
		//return mappers.get(version).getCombinedChecklistSpecificationItems(clName, envPackage);
		return null;
	}	

	//----------------------------------------

	@Override
	public List<HabLiteDist> getHabLiteDist(String study, long offset, long limit, String version)
			throws Exception {
		// TODO Auto-generated method stub
		return mappers.get(version).getHabLiteDist(study, offset, limit);
	}
	
	@Override
	public List<GenomeOverview> getPhages(String where, String sort, long offset, long limit, String version) throws Exception{
		return mappers.get(version).getPhages(where, sort, offset, limit);
	}
	
	@Override
	public long getPhagesCount(String where, String version) throws Exception {
		return mappers.get(version).getPhagesCount(where);
	}	

	@Override
	public void insertPhagesList(GenomeOverview l,String version) throws Exception{
		mappers.get(version).insertPhagesList(l);
	}

	@Override
	public List<GenomeOverview> getGenomes(String where, String sort, long offset, long limit, String version) throws Exception {
		return mappers.get(version).getGenomes(where, sort, offset, limit);
	}

	@Override
	public long getGenomesCount(String where, String version) throws Exception {
		return mappers.get(version).getGenomesCount(where);
	}
	
	@Override
	public List<SamplingSite> getSites(String where, String sort, long offset, long limit, String version) throws Exception {
		return mappers.get(version).getSites(where, sort, offset, limit);
	}

	@Override
	public List<RRNA> getSilvaOverview(long offset, long limit, String version) throws Exception {
		return mappers.get(version).getSilvaOverview(offset, limit);
	}

	@Override
	public List<Silva> get_silva_by_geom(String geom, String version)
			throws Exception {
		return mappers.get(version).get_silva_by_geom(geom);
	}	
	
	@Override
	public List<Sample> getSamples(String lat, String lon, long offset, long limit, String version) throws Exception {
		return mappers.get(version).getSamples(lat,lon,offset,limit);
	}

	@Override
	public List<Metagenome> getMetagenomes(String where, String sort, long offset, long limit, String version) throws Exception {
		return mappers.get(version).getMetagenomes(where, sort, offset, limit);
	}

	@Override
	public List<IsMarine> isMarine(String lon, String lat, String version)
			throws Exception {
		//List<IsMarine> 
		List<IsMarine> im = mappers.get(version).isMarine(lon, lat);
		/*if (im.get(0).marine){ 
			im.get(0).setErrmsg("The requested location is either on land or too close to the shore. Therefore, no data is available from WOA05 and no interpolation is possible.");
		}*/
		return im;
	}
	
	@Override
	public String blastRunStatus(String sid, String jid,
			String version) throws Exception {
		List<BlastStatus> bs = mappers.get(version).blastRunStatus(sid, jid);
		// TODO: null or empty will throw exception (and it should be), check if modification is needed or maybe if boolean should be returned
		try {
			return bs.get(0).status; 	
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Invalid data");
		}
		
	}

	@Override
	public BlastJob insertBlastRun(BlastJobParams bjp, String version) throws Exception {
		List<BlastJob> lbj = mappers.get(version).insertBlastRun(bjp);
		return (BlastJob)lbj.get(0);
	}

	@Override
	public List<WOA5> woa05_idw_ip(double lon, double lat, float depth,
			String param, short  season, String version) throws Exception {
		// TODO Auto-generated method stub
		return mappers.get(version).woa05_idw_ip(lon, lat, depth, param, season);
	}

	@Override
	public List<WOD5> wod05_idw_ip(float lon, float lat, float depth,
			float buffer, String version) throws Exception {
		// TODO Auto-generated method stub
//		- lat (-90 .. 90) else "Latitude out of range", if 0 "Please, insert latitude" 
//		- lon (-180 .. 180) else "Longitude out of range", if 0 "Please, insert longitude" 
//		- depth (0 .. 5500) else "Depth out of range" if 0 "Please, insert depth" 
//		- buffer (non 0) else "Please, insert buffer" 
		
		if ((lat < -90) || (lat > 90)) throw new Exception("Latitude out of range");
		if (lat == 0) throw new Exception("Please, insert latitude");
		if ((lon < -180) || (lon > 180)) throw new Exception("Longitude out of range");
		if (lon == 0) throw new Exception("Please, insert longitude");
		if ((depth < 5500) ) throw new Exception("Latitude out of range");
		if (depth == 0) throw new Exception("Please, insert depth");
		if (buffer == 0) throw new Exception("Please, insert buffer");
		
		return mappers.get(version).wod05_idw_ip(lon, lat, depth, buffer);
	}



	
	
}
