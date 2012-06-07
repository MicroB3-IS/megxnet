package net.megx.pubmap;

import java.util.Map;

import net.megx.pubmap.mvc.HomeAction;

import org.chon.cms.light.mvc.AbstractAction;

public class PubMapActions {
	public static final String ACTIONS_ROOT = "pubmap";
	
	public static void registerActions(Map<String, Class<? extends AbstractAction>> actions) {
		actions.put("home", HomeAction.class);
		actions.put("myaction", MyAction.class);
	}
}
