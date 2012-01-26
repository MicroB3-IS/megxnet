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
package com.iw.megx.ws.service.mpiws;

import java.util.LinkedList;
import java.util.List;

import com.iw.megx.ws.dao.mpiws.MpiWsDao;
import com.iw.megx.ws.dto.mpiws.BlastJob;
import com.iw.megx.ws.dto.mpiws.BlastJobParams;
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

public class MpiWsServiceImpl implements MpiWsService{

	private MpiWsDao mpiWsDao;

	public void setMpiWsDao(MpiWsDao mpiWsDao) {
		this.mpiWsDao = mpiWsDao;
	}

	public MpiWsDao getMpiWsDao() {
		return mpiWsDao;
	}
	
	public void test(){
		//mpiWsDao
	}
	
	@Override
	public List<HabLiteDist> getHabLiteDist(String study, long offset, long limit, String version)
			throws Exception {
		return mpiWsDao.getHabLiteDist(study, offset, limit, version);
	}	
	
	@Override
	public List<GenomeOverview> getPhages(String where, String sort, long offset, long limit, String version) throws Exception {
		return mpiWsDao.getPhages(where, sort, offset, limit, version);
	}

	@Override
	public void insertPhagesList(GenomeOverview l, String version) throws Exception {
		mpiWsDao.insertPhagesList(l,version);
	}

	@Override
	public List<GenomeOverview> getGenomes(String where, String sort, long offset, long limit, String version) throws Exception {
		// DEMO
//		List<GenomeOverview> lg = new LinkedList<GenomeOverview>();
//		for (long i = offset; i < offset+limit; i++) {
//			GenomeOverview go = new GenomeOverview();
//			go.organismName = ""+i;
//			lg.add(go);
//		}
//		return lg;
		// END DEMO
		
		return mpiWsDao.getGenomes(where, sort, offset, limit, version);
	}

	@Override
	public long getGenomesCount(String where, String version) throws Exception {
		//return 83;
		return mpiWsDao.getGenomesCount(where, version);
	}

	
	@Override
	public List<SamplingSite> getSites(String where, String sort, long offset, long limit, String version) throws Exception {
		return mpiWsDao.getSites(where, sort, offset, limit, version);
	}

	@Override
	public List<RRNA> getSilvaOverview(long offset, long limit, String version) throws Exception {
		return mpiWsDao.getSilvaOverview(offset, limit, version);
	}

	@Override
	public List<Silva> get_silva_by_geom(String geom, String version)
			throws Exception {
		return mpiWsDao.get_silva_by_geom(geom, version);
	}	
	
	@Override
	public List<Sample> getSamples(String lat, String lon, long offset, long limit, String version) throws Exception {
		return mpiWsDao.getSamples(lat, lon, offset, limit, version);
	}

	@Override
	public List<Metagenome> getMetagenomes(String where, String sort, long offset, long limit, String version) throws Exception {
		return mpiWsDao.getMetagenomes(where, sort, offset, limit, version);
	}

	@Override
	public String blastRunStatus(String sid, String jid, String version)
			throws Exception {
		// TODO Auto-generated method stub
		return mpiWsDao.blastRunStatus(sid, jid, version);
	}

	@Override
	public BlastJob insertBlastRun(BlastJobParams bjp, String version)
			throws Exception {
		// TODO Auto-generated method stub
		return mpiWsDao.insertBlastRun(bjp, version);
	}

	@Override
	public List<WOA5> woa05_idw_ip(double lon, double lat, float depth, String param, short  season, String version) throws Exception {
		// TODO Auto-generated method stub
		return mpiWsDao.woa05_idw_ip(lon, lat, depth, param, season, version);
	}

	@Override
	public List<WOD5> wod05_idw_ip(float lon, float lat, float depth,
			float buffer, String version) throws Exception {
		return mpiWsDao.wod05_idw_ip(lon, lat, depth, buffer, version);
	}

	@Override
	public List<IsMarine> isMarine(String lon, String lat, String version)
			throws Exception {
		return mpiWsDao.isMarine(lon, lat, version);
	}

	@Override
	public long getPhagesCount(String where, String version) throws Exception {
		// TODO Auto-generated method stub
		return mpiWsDao.getPhagesCount(where, version);
	}




}
