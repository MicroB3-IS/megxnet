package net.megx.ws.genomes;

import net.megx.ws.core.BaseRestService;
import net.megx.ws.genomes.resources.WorkspaceAccess;

public class BaseGenomeService extends BaseRestService{
	private WorkspaceAccess access;

	public BaseGenomeService(WorkspaceAccess access) {
		super();
		this.access = access;
	}
	
	protected WorkspaceAccess getAccess(){
		return access;
	}
}
