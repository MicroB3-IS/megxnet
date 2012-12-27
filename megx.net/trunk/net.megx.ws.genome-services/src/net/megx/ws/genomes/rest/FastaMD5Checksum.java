package net.megx.ws.genomes.rest;

import javax.jcr.ItemNotFoundException;
import javax.jcr.PathNotFoundException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.security.AccessControlException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import net.megx.storage.ResourceAccessException;
import net.megx.storage.StorageSecuirtyException;
import net.megx.ws.genomes.MD5HashMultiFasta;

@Path("v1/checksum/v1.0.0")
public class FastaMD5Checksum {

	private MD5HashMultiFasta hashService;

	public FastaMD5Checksum(MD5HashMultiFasta hashService) {
		super();
		this.hashService = hashService;
	}

	@GET
	public Response calculateChecksum(
			@QueryParam("infile") final String infile,
			@Context HttpServletRequest request) throws WebApplicationException {
		String username = request.getUserPrincipal() != null ? request
				.getUserPrincipal().getName() : null;
		if (username == null) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		try {
			hashService.calculateMD5Hash(username, infile);
			return Response.ok()
					.entity(hashService.getMd5Hash(username, infile)).build();
		} catch (Exception e) {
			handleException(e);
		}
		return null;
	}

	private void handleException(Exception e) throws WebApplicationException {
		if (e instanceof AccessControlException
				|| e instanceof ResourceAccessException
				|| e instanceof StorageSecuirtyException) {
			throw new WebApplicationException(e, Response.Status.FORBIDDEN);
		} else if (e instanceof ItemNotFoundException
				|| e instanceof PathNotFoundException
				|| e instanceof NoSuchNodeTypeException) {
			throw new WebApplicationException(e, Response.Status.NOT_FOUND);
		} else {
			throw new WebApplicationException(e);
		}
	}

}
