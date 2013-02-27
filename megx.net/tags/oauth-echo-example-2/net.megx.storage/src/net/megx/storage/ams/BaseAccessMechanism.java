package net.megx.storage.ams;

import net.megx.storage.AccessMechanism;
import net.megx.storage.Context;

public abstract class BaseAccessMechanism implements AccessMechanism{
	
	private Context context;
	
	public Context getContext(){
		return context;
	}

	public BaseAccessMechanism(Context context) {
		super();
		this.context = context;
	}
	
	public static final String ATTR_DATE_CREATED = "date-created";
	public static final String ATTR_DATE_MODIFIED = "date-modified";
	public static final String ATTR_SIZE = "size";
	
}
