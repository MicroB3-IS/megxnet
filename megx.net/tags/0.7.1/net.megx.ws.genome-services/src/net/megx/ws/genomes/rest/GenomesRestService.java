package net.megx.ws.genomes.rest;

import java.io.IOException;

import javax.jcr.ItemNotFoundException;
import javax.jcr.PathNotFoundException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.security.AccessControlException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import net.megx.storage.ResourceAccessException;
import net.megx.storage.StorageSecuirtyException;
import net.megx.ws.core.BaseRestService;

public class GenomesRestService extends BaseRestService{
	protected void handleWorkspaceAccessException(Exception e) throws WebApplicationException, IOException{
		log.error(e);
		if(e instanceof IOException){
			throw (IOException)e;
		}else if(e instanceof AccessControlException ||
				e instanceof StorageSecuirtyException){
			// 
			throw new WebApplicationException(e, Status.UNAUTHORIZED);
		}else if(e instanceof ItemNotFoundException ||
				e instanceof PathNotFoundException ||
				e instanceof NoSuchNodeTypeException ||
				e instanceof ResourceAccessException){
			throw new WebApplicationException(e, Status.NOT_FOUND);
		}else{
			throw new WebApplicationException(e);
		}
	}
}
