package net.megx.ws.genomes.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.megx.ws.genomes.MD5HashMultiFasta;

@Path("v1/checksum/v1.0.0")
public class FastaMD5Checksum extends GenomesRestService{

	private MD5HashMultiFasta hashService;

	public FastaMD5Checksum(MD5HashMultiFasta hashService) {
		super();
		this.hashService = hashService;
	}

	@GET
	@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
	public Response calculateChecksum(
			@QueryParam("infile") final String infile,
			@Context HttpServletRequest request) throws WebApplicationException, IOException {
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
			handleWorkspaceAccessException(e);
		}
		return null;
	}
	
//	private void _handleException(Exception e) throws WebApplicationException {
//		if (e instanceof AccessControlException
//				|| e instanceof ResourceAccessException
//				|| e instanceof StorageSecuirtyException) {
//			throw new WebApplicationException(e, Response.Status.FORBIDDEN);
//		} else if (e instanceof ItemNotFoundException
//				|| e instanceof PathNotFoundException
//				|| e instanceof NoSuchNodeTypeException) {
//			throw new WebApplicationException(e, Response.Status.NOT_FOUND);
//		} else {
//			throw new WebApplicationException(e);
//		}
//	}
	
}
