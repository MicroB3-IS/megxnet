package net.megx.ws.mixs.dto;

import net.megx.model.ws.mixs.IndependentMetadataItem;
import net.megx.ws.core.providers.csv.annotations.CSVColumn;

public class CSVFormattedIndMetadataItem extends IndependentMetadataItem{

	
	public CSVFormattedIndMetadataItem() {
	}
	
	
	
	
	public CSVFormattedIndMetadataItem(String item, String label,
			String definition, String expectedValue,
			String expectedValueDetails, String valueType, String syntax,
			String example, String help, String occurrence, String regexp,
			String sampleAssoc) {
		super(item, label, definition, expectedValue, expectedValueDetails, valueType,
				syntax, example, help, occurrence, regexp, sampleAssoc);
	}


	public CSVFormattedIndMetadataItem(IndependentMetadataItem m){
		this(m.getItem(), m.getLabel(), m.getDefinition(), m.getExpected_value(),
				m.getExpectedValueDetails(), m.getValueType(), m.getSyntax(),
				m.getExample(), m.getHelp(), m.getOccurrence(), m.getRegexp(), m.getSampleAssoc());
	}


	@CSVColumn(name="expected_value")
	private String expectedValue;
	
	@CSVColumn(name="expected_value_details")
	private String expectedValueDetails;
	
	@CSVColumn(name="value_type")
	private String valueType;
	
	@CSVColumn(name="sample_assoc")
	private String sampleAssoc;

	
	public String getExpectedValue() {
		return expectedValue;
	}

	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}

	public String getExpectedValueDetails() {
		return expectedValueDetails;
	}

	public void setExpectedValueDetails(String expectedValueDetails) {
		this.expectedValueDetails = expectedValueDetails;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getSampleAssoc() {
		return sampleAssoc;
	}

	public void setSampleAssoc(String sampleAssoc) {
		this.sampleAssoc = sampleAssoc;
	}
	
}
