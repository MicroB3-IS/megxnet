package net.megx.megdb.esa;

import java.util.List;
import java.util.Map;
import net.megx.model.esa.Sample;
import net.megx.model.esa.SamplePhoto;
import net.megx.model.esa.SampleRow;

public interface EarthSamplingAppService {
	public List<Sample> getSamples(String creator) throws Exception;
	public List<SampleRow> getAllSamples() throws Exception;
	public Sample getSample(String id) throws Exception;
	
	public List<String> storeSamples(List<Sample> samples) throws Exception;
	public List<String> storePhotos(List<SamplePhoto> photos) throws Exception;
	public List<SamplePhoto> getSamplePhotos(String sampleId) throws Exception;
	
	public Map<String, String> getConfiguration(String category) throws Exception;
	public void storeConfiguration(String category, Map<String, String> config) throws Exception;
	public void clearConfig(String category) throws Exception;
	public void clearConfigValue(String category, String name) throws Exception;
	
	public String read(String category, String name) throws Exception;
	public void store(String category, String name, String value) throws Exception;
	
	public void removeSample(String sampleId) throws Exception;
}
