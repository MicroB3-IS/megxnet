package net.megx.ws.blast.utils;

import javax.servlet.http.HttpServletRequest;

public class BlastUtils {
	public static String getUsernameFromRequest(HttpServletRequest request) {
		if(request.getUserPrincipal() != null) {
			return request.getUserPrincipal().getName();
		}
		return "anonymous";
	}
}
