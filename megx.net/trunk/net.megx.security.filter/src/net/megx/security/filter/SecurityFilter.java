package net.megx.security.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;

public interface SecurityFilter {
	public void initialize(BundleContext context);
	public void doFilter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
