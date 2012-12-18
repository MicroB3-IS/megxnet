package net.megx.security.filter.http.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.megx.model.logging.LoggedError;

public class ErrorWebUtils {
	
	public static final String NONCE_ATR = "net.megx.http.request.NONCE";
	
	public void setupErrorRequest(HttpServletRequest request, LoggedError error){
		HttpSession session = request.getSession();
	}
	
	
	private class Nonce implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 154785459142181446L;
		
		public Date timestamp;
		public String nonce;
	}
	
	
	public static void main(String[] args) {
		Queue<String> q = new LinkedList<String>();
		q.add("one");
		q.add("two");
		q.add("three");
		q.add("four");
		
		System.out.println(q.peek());
		
		List<String> l = new LinkedList<String>();
		l.add("one");
		l.add("two");
		l.add("three");
		
		ListIterator<String> li = l.listIterator(l.size());
		while(li.hasPrevious()){
			System.out.println(li.previous());
		}
		
		Date d;
		
	}
}
