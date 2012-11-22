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

import java.util.List;

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

public interface MpiWsService {

	public abstract List<HabLiteDist> getHabLiteDist(String study, long offset, long limit, String version) throws Exception;	
	
	public abstract List<GenomeOverview> getPhages(String where, String sort, long offset, long limit, String version) throws Exception;

	void insertPhagesList(GenomeOverview l, String version) throws Exception;

	public abstract List<GenomeOverview> getGenomes(String where, String sort, long offset, long limit, String version) throws Exception;
	public abstract long getGenomesCount(String where, String version) throws Exception;
	
	public abstract List<SamplingSite> getSites(String where, String sort, long offset, long limit, String version) throws Exception;	
	
	public abstract List<RRNA> getSilvaOverview(long offset, long limit, String version) throws Exception;
	
	public abstract List<Silva> get_silva_by_geom(String geom, String version) throws Exception;
	
	public abstract List<Sample> getSamples(String lat, String lon, long offset, long limit, String version) throws Exception;
	
	public abstract List<Metagenome> getMetagenomes(String where, String sort, long offset, long limit, String version) throws Exception;	

	public abstract List<IsMarine> isMarine(String lon, String lat, String version) throws Exception;	
	
	public abstract String blastRunStatus(String sid, String jid, String version) throws Exception;
	
	public abstract BlastJob insertBlastRun(BlastJobParams bjp, String version) throws Exception;	
	
	public abstract List<WOA5> woa05_idw_ip(double lon, double lat, float depth, String param, short  season, String version) throws Exception;
	
	public abstract List<WOD5> wod05_idw_ip(float lon, float lat, float depth, float buffer, String version) throws Exception;

	public abstract long getPhagesCount(String where, String version) throws Exception;	
}
