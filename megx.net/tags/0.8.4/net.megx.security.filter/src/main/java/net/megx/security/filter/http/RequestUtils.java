package net.megx.security.filter.http;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.megx.model.logging.LoggedError;
import net.megx.security.logging.ErrorLog;

public class RequestUtils {

	public static final String SESSION_NONCE_ATTR = "net.megx.http.request.NONCE_LIST";
	public static final String REQUEST_NONCE_PARAM = "REQUEST_NONCE";
	
	private long nonceTTL = 20 * 60 * 1000; // 20 mins
	private int nonceMaxNumber = 250; // 250 requests per 20 minutes
	private ErrorLog errorLog;
	
	private static Log log = LogFactory.getLog(RequestUtils.class);
	
	public RequestUtils() {	}
	
	
	public RequestUtils(long nonceTTL, int nonceMaxNumber, ErrorLog errorLog) {
		super();
		this.nonceTTL = nonceTTL;
		this.nonceMaxNumber = nonceMaxNumber;
		this.errorLog = errorLog;
	}


	public void prepareRequest(HttpServletRequest request, LoggedError err){
		NonceHolder holder = (NonceHolder)request.getSession().getAttribute(SESSION_NONCE_ATTR);
		if(holder == null){
			synchronized (this) {
				holder = new NonceHolder(nonceMaxNumber, nonceTTL);
				request.getSession().setAttribute(SESSION_NONCE_ATTR, holder);
			}
		}
		Nonce nonce = new Nonce();
		nonce.nonce = err.getId();
		nonce.timestamp = new Date();
		holder.push(nonce);
	}
	
	
	public boolean validateRequest(HttpServletRequest request){
		NonceHolder holder = (NonceHolder)request.getSession().getAttribute(SESSION_NONCE_ATTR);
		String nonceValue = request.getParameter(REQUEST_NONCE_PARAM);
		if(nonceValue != null && holder != null){
			Nonce n = holder.pop(nonceValue);
			return n != null;
		}
		return false;
	}
	
	public boolean errorLimitReached(HttpServletRequest request){
		NonceHolder holder = (NonceHolder)request.getSession().getAttribute(SESSION_NONCE_ATTR);
		if( holder != null && holder.limitReached() ){
			return true;
		}
		String user = request.getUserPrincipal() != null ?
				request.getUserPrincipal().getName(): null;
		String remoteAddress = request.getRemoteAddr();
		Calendar timestamp = Calendar.getInstance();
		timestamp.setTimeInMillis(timestamp.getTimeInMillis() - nonceTTL);
		try {
			int errorCount = errorLog.getErrorCount(user, remoteAddress, timestamp.getTime());
			return errorCount >= this.nonceMaxNumber;
		} catch (Exception e) {
			log.error("Failed to retrieve errors count",e);
		}
		return true;
	}
	
	
	public static class Nonce implements Comparable<Nonce>, Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 8299950555645282630L;
		public String nonce;
		public Date timestamp;

		@Override
		public int compareTo(Nonce o) {
			return timestamp.compareTo(o.timestamp);
		}
	}
	
	private static class NonceHolder implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 2567881701723994784L;
		
		private Map<String, Nonce> hashed = new HashMap<String, RequestUtils.Nonce>();
		private List<Nonce> all = new LinkedList<Nonce>();
		
		private int maxCapacity = 1;
		private long ttl;
		
		
		
		public NonceHolder(int maxCapacity, long ttl) {
			super();
			this.maxCapacity = maxCapacity;
			this.ttl = ttl;
		}

		public void push(Nonce nonce){
			if(all.size() == maxCapacity){
				cleanup();
			}
			if(all.size() == maxCapacity){
				Nonce n = all.remove(0);
				hashed.remove(n.nonce);
			}
			all.add(nonce);
			hashed.put(nonce.nonce, nonce);
		}
		
		public Nonce pop(String nonceId){
			Nonce n = hashed.get(nonceId);
			if(n != null){
				all.remove(n);
				hashed.remove(nonceId);
				Date t = getTimeThreshold();
				if(n.timestamp.compareTo(t) >= 0){
					return n;
				}
			}
			return null;
		}
		
		private void cleanup(){
			Date d = getTimeThreshold();
			
			int i = 0;
			for(Nonce n: all){
				if(n.timestamp.compareTo(d) >= 0){
					break;
				}
				hashed.remove(n.nonce);
				i++;
			}
			if(i >= 0){
				all = all.subList(i, all.size());
			}
		}
		
		public boolean limitReached(){
			return all.size() == maxCapacity;
		}
		
		private Date getTimeThreshold(){
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(c.getTimeInMillis()-ttl);
			Date d = c.getTime();
			return d;
		}
	}
}
