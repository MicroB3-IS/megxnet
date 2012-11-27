package net.megx.security.auth.model;

import java.util.List;

public class PaginatedResult<T> {
	private List<T> results;
	private int start;
	private int end;
	private int pageSize;
	private int totalCount;
	private int pageNumber;
	
	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	
	public PaginatedResult(List<T> results, int start, int end, int pageSize,
			int totalCount) {
		super();
		this.results = results;
		this.start = start;
		this.end = end;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		if(pageSize > 0)
			pageNumber = (start/pageSize) + 1;
	}
	
	
	public static <T>  PaginatedResult<T> fromList(List<T> results, int start, int end, int totalCount){
		return new PaginatedResult<T>(results, start, end, end-start, totalCount);
	}
	
	public static <T>  PaginatedResult<T> fromListWithPageSize(List<T> results, int start, int pageSize, int totalCount){
		return new PaginatedResult<T>(results, start, start + pageSize, pageSize, totalCount);
	}
	
	public static <T>  PaginatedResult<T> empty(){
		return new PaginatedResult<T>(null, 0, 0, 0, 0);
	}
	
}
