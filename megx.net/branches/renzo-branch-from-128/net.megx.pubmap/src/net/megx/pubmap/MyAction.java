package net.megx.pubmap;

import java.util.HashMap;

import org.chon.cms.light.mvc.AbstractAction;
import org.chon.cms.light.mvc.result.Result;

public class MyAction extends AbstractAction {

	@Override
	public Result exec() {
		String param1 = req.get("param1");
		String param2 = req.get("param2");
		if(param1 != null) {
			HashMap<String, Object> templateParametes = new HashMap<String, Object>();
			
			templateParametes.put("result", "OK, form submitted");
			templateParametes.put("p1", param1);
			templateParametes.put("p2", param2);
			
			return R.template("pubmap/result.html", templateParametes);
		}
	
		return R.template("pubmap/test1.html");
	}

}
