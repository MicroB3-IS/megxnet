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
package com.iw.megx.ws.dao.mpiws.mappers;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.apache.ibatis.annotations.Param;

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

public interface MpiWsMapper {

	public List<HabLiteDist> getHabLiteDist(String study, long offset, long limit) throws Exception;	
	
	// Sort will be ignored for now as is in old code, but let it be there to be implemented if needed
	public List<GenomeOverview> getPhages(@Param("where") String where, @Param("sort") String sort, @Param("offset") long offset, @Param("limit") long limit) throws Exception;
	public long getPhagesCount(String where) throws Exception;
	
	// For Testing
	public void insertPhagesList(GenomeOverview l) throws Exception;
	
	public List<GenomeOverview> getGenomes(String where, String sort, long offset, long limit) throws Exception;
	public long getGenomesCount(String where) throws Exception;	
	
	public List<SamplingSite> getSites(String where, String sort, long offset, long limit) throws Exception;
	
	public List<RRNA> getSilvaOverview(long offset, long limit) throws Exception;
	
	public List<Silva> get_silva_by_geom(String geom) throws Exception;
	
	public List<Sample> getSamples(String lat, String lon, long offset, long limit) throws Exception;
	
	public List<Metagenome> getMetagenomes(String where, String sort, long offset, long limit) throws Exception;
	
	public List<IsMarine> isMarine(String lon, String lat) throws Exception;
	
	public List<BlastStatus> blastRunStatus(String sid, String jid) throws Exception;
	
	public List<BlastJob> insertBlastRun(BlastJobParams bjp) throws Exception;
	
	public List<WOA5> woa05_idw_ip(double lon, double lat, float depth, String param, short season) throws Exception;

	public List<WOD5> wod05_idw_ip(float lon, float lat, float depth, float buffer) throws Exception;
	
	
}
