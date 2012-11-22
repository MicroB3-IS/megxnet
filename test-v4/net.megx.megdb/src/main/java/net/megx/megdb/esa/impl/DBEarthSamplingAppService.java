package net.megx.megdb.esa.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.esa.EarthSamplingAppService;
import net.megx.megdb.esa.mappers.ESAMapper;
import net.megx.model.esa.Sample;
import net.megx.model.esa.SamplePhoto;

public class DBEarthSamplingAppService extends BaseMegdbService implements EarthSamplingAppService {
	
	private Log log = LogFactory.getLog(getClass());
	
	@Override
	public List<String> storeSamples(final List<Sample> samples) throws Exception{
		
		return doInTransaction(new DBTask<ESAMapper, List<String>>(){
			@Override
			public List<String> execute(ESAMapper mapper) throws Exception{
				List<String> savedSamplesList = new ArrayList<String>();
				for (Sample sample : samples) {
					mapper.addSample(sample);
					savedSamplesList.add(sample.getId());
					SamplePhoto [] photos = sample.getPhotos();
					if(photos !=null){
						for(SamplePhoto photo: photos){
							mapper.addPhoto(photo, sample.getId());
						}
					}
				}
				return savedSamplesList;
			}
		}, ESAMapper.class);
	}
	
	@Override
	public Map<String, String> getConfiguration(final String category) throws Exception{
		
		return doInSession(new DBTask<ESAMapper, Map<String, String>>(){
			@Override
			public Map<String, String> execute(ESAMapper mapper) throws Exception{
				Map<String, String> savedConfigs = new HashMap<String, String>();
				for (Map<String, String> configuration: mapper.getConfiguration(category)) {
					savedConfigs.put(configuration.get("name"), configuration.get("value"));
				}
				return savedConfigs;
			}
		}, ESAMapper.class);
	}
	
	@Override
	public Sample getSample(final String id) throws Exception{
		
		return doInSession(new DBTask<ESAMapper, Sample>(){
			@Override
			public Sample execute(ESAMapper mapper) throws Exception{
				return mapper.getSample(id);
			}
		}, ESAMapper.class);
	}
	
	@Override
	public List<Sample> getSamples(final String creator) throws Exception {
		
		return doInSession(new DBTask<ESAMapper, List<Sample>>(){
			@Override
			public List<Sample> execute(ESAMapper mapper) throws Exception{
				List<Sample> samplesToReturn = new ArrayList<Sample>();
				for (Sample sample : mapper.getSamples(creator)) {
					samplesToReturn.add(sample);
				}
				return samplesToReturn;
			}
		}, ESAMapper.class);
	}
	
	@Override
	public String read(final String category, final String name) throws Exception {
		
		return doInSession(new DBTask<ESAMapper, String>(){
			@Override
			public String execute(ESAMapper mapper) throws Exception{
				return mapper.getConfigValue(category, name);
			}
		}, ESAMapper.class);
	}
	
	@Override
	public void store(final String category, final String name, final String value) throws Exception {
		
		doInTransaction(new DBTask<ESAMapper, Object>(){
			@Override
			public Object execute(ESAMapper mapper) throws Exception{
				mapper.storeConfigValue(category, name, value);
				return null;
			}
		}, ESAMapper.class);
	}
	
	@Override
	public void storeConfiguration(final String category, final Map<String, String> config) throws Exception {
		
		doInTransaction(new DBTask<ESAMapper, Object>(){
			@Override
			public Object execute(ESAMapper mapper) throws Exception{
				mapper.storeConfiguration(category, config.entrySet());
				return null;
			}
		}, ESAMapper.class);
	}
	
	@Override
	public void clearConfigValue(final String category, final String name) throws Exception {
		
		doInTransaction(new DBTask<ESAMapper, Object>(){
			@Override
			public Object execute(ESAMapper mapper) throws Exception{
				mapper.clearConfigValue(category, name);
				return null;
			}
		}, ESAMapper.class);
	}
	
	@Override
	public List<String> storePhotos(final List<SamplePhoto> photos) throws Exception {
		
		return doInTransaction(new DBTask<ESAMapper, List<String>>(){
			@Override
			public List<String> execute(ESAMapper mapper) throws Exception{
				List<String> savedPhotosIds = new ArrayList<String>();
				for (SamplePhoto samplePhoto : photos) {
					mapper.updatePhoto(samplePhoto);
					savedPhotosIds.add(samplePhoto.getUuid());
				}
				return savedPhotosIds;
			}
		}, ESAMapper.class);
	}
	
	@Override
	public List<SamplePhoto> getSamplePhotos(final String sampleId) throws Exception {
		
		return doInSession(new DBTask<ESAMapper, List<SamplePhoto>>(){
			@Override
			public List<SamplePhoto> execute(ESAMapper mapper) throws Exception{
				return mapper.getPhotosForSample(sampleId);
			}
		}, ESAMapper.class);
	}
	
	@Override
	public void removeSample(final String sampleId) throws Exception {
		
		doInTransaction(new DBTask<ESAMapper, Object>(){
			@Override
			public Object execute(ESAMapper mapper) throws Exception{
				mapper.removeSample(sampleId);
				return null;
			}
		}, ESAMapper.class);
	}
	
	@Override
	public void clearConfig(final String category) throws Exception {
		
		doInTransaction(new DBTask<ESAMapper, Object>(){
			@Override
			public Object execute(ESAMapper mapper) throws Exception{
				mapper.clearConfig(category);
				return null;
			}
		}, ESAMapper.class);
	}
	
}
