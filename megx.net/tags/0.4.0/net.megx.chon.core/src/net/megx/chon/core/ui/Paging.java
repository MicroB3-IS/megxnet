/*
 * Copyright 2011 Max Planck Institute for Marine Microbiology 
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package net.megx.chon.core.ui;

import org.chon.web.api.Request;

public class Paging {
	public long offset;
	public long limit;
	public long count;
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public long getOffset() {
		return offset;
	}
	public void setOffset(long offset) {
		this.offset = offset;
	}
	public long getLimit() {
		return limit;
	}
	public void setLimit(long limit) {
		this.limit = limit;
	}
	
	
	public static Paging getPaging(Request req){
		Paging p = new Paging();
		int itemsOnPage = 10;
		p.offset = (req.get("offset")!=null) ? Long.parseLong(req.get("offset")): 0;
		System.out.println("--------:"+p.offset);
		p.limit = (req.get("limit")!=null) ? Long.parseLong(req.get("limit")) : itemsOnPage;
		System.out.println("--------:"+p.limit);
		System.out.println(p.offset+"   "+p.limit);
		return p;
	}
}
