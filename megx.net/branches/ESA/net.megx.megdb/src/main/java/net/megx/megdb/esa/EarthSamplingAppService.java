package net.megx.megdb.esa;

import java.util.List;
import java.util.Map;

import net.megx.model.esa.Sample;
import net.megx.model.esa.SamplePhoto;

public interface EarthSamplingAppService {
	public List<Sample> getSamples(String creator);
	public Sample getSample(String id);
	
	public List<String> storeSamples(List<Sample> samples);
	public List<String> storePhotos(List<SamplePhoto> photos);
	
	public Map<String, String> getConfiguration(String category);
	public void storeConfiguration(String category);
	
	public String read(String category, String name);
	public void store(String category, String name, String value);
}
