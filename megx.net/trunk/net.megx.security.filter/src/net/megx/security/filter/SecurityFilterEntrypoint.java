package net.megx.security.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.megx.security.auth.SecurityException;

import org.json.JSONObject;
import org.osgi.framework.BundleContext;

public interface SecurityFilterEntrypoint {
	public void initialize(BundleContext context, JSONObject config);
	public void doFilter(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException, SecurityException, StopFilterException;
}
