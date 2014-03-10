package net.megx.ws.mg.traits.rest.mappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.megx.model.mgtraits.MGTraitsTaxonomy;

public class TaxonomicContentToClient {
	private Map<String, List<Map<String, String>>> rows = new HashMap<String, List<Map<String,String>>>();
	
	public TaxonomicContentToClient(List<MGTraitsTaxonomy> taxonomies){
		super();
		for (MGTraitsTaxonomy currTaxonomy : taxonomies) {
			Map<String, String> currNameVal = new HashMap<String, String>();
			currNameVal.put(currTaxonomy.getKey(), currTaxonomy.getValue());
			List<Map<String, String>> nameValArr = rows.get(currTaxonomy.getSampleLabel());
			if(nameValArr != null){
				nameValArr.add(currNameVal);
			} else{
				nameValArr = new ArrayList<Map<String,String>>();
				nameValArr.add(currNameVal);
			}
			rows.put(currTaxonomy.getSampleLabel(), nameValArr);
		}
	}

	public Map<String, List<Map<String, String>>> getRows() {
		return rows;
	}

	public void setRows(Map<String, List<Map<String, String>>> rows) {
		this.rows = rows;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rows == null) ? 0 : rows.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaxonomicContentToClient other = (TaxonomicContentToClient) obj;
		if (rows == null) {
			if (other.rows != null)
				return false;
		} else if (!rows.equals(other.rows))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaxonomicContentToClient [rows=" + rows + "]";
	}
}
