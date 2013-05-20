package net.megx.ws.core.providers.html;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.chon.core.velocity.VTemplate;

public class ChonTemplater implements HTMLTemplater<Object>{

	private VTemplate vtemplate;
	
	public ChonTemplater(VTemplate template) {
		this.vtemplate = template;
	}
	
	@Override
	public void merge(String template, Object data, Writer writer)
			throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", data);
		vtemplate.format(template, dataMap, new HashMap<String, Object>(), writer);
		writer.flush();
		writer.close();
	}
	

}
