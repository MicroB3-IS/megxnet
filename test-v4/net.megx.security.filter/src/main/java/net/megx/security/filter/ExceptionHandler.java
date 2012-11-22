package net.megx.security.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public interface ExceptionHandler {
	public boolean handleException(Exception e, HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException;
	public void init(JSONObject config);
}
