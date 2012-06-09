package net.megx.pubmap.mvc;

import org.chon.cms.light.mvc.AbstractAction;
import org.chon.cms.light.mvc.result.Result;

public class HomeAction extends AbstractAction {

	@Override
	public Result exec() {
		//R is really a bad name for a variable
		//another comment
		//and another comment
		return R.template("pubmap/start.html");
	}

}
