package net.megx.pubmap.mvc;

import org.chon.cms.light.mvc.AbstractAction;
import org.chon.cms.light.mvc.result.Result;

public class HomeAction extends AbstractAction {

	@Override
	public Result exec() {
		return R.template("pubmap/start_search.html");
	}

}
