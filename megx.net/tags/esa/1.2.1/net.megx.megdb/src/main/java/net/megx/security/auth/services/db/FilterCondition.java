package net.megx.security.auth.services.db;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FilterCondition {
	private String name;
	private String query;
	private String condition;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	protected FilterCondition(String name, String query){
		this.name = name;
		this.query = query;
	}
	
	protected FilterCondition(String name, String query, String cond){
		this.name = name;
		this.query = query;
		this.condition = cond;
	}
	
	public static class ConditionBuilder{
		
		private static List<String> VALID_CONDITIONS = 
				Arrays.asList(new String []{"AND","OR"});
		
		private List<FilterCondition> conditions = new LinkedList<FilterCondition>();
		
		private String queryFormat = "%s";
		
		
		public ConditionBuilder() {	}
		
		public ConditionBuilder(String queryFormat) {		
			this.queryFormat = queryFormat;
		}

		private String getQuery(String q){
			return String.format(queryFormat, q);
		}
		
		public List<FilterCondition> build(){
			return conditions;
		}
		
		
		
		public ConditionBuilder add(String name, String query){
			query = getQuery(query);
			if(conditions.size() > 0){
				conditions.set(0, new FilterCondition(name, query));
			}else{
				conditions.add(new FilterCondition(name, query));
			}
			return this;
		}
		
		public ConditionBuilder first(String name, String query){
			return add(name, query);
		}
		
		
		public ConditionBuilder add(String condition, String name, String query){
			query = getQuery(query);
			if(conditions.size() == 0){
				conditions.add(new FilterCondition(name, query));
			}else{
				if(!VALID_CONDITIONS.contains(condition.toUpperCase())){
					condition = null;
				}
				conditions.add(new FilterCondition(name, query, condition));
			}
			return this;
		}
	}
	
	public static ConditionBuilder builder(){
		return new ConditionBuilder();
	}
	
	public static ConditionBuilder builder(String queryFormat){
		return new ConditionBuilder(queryFormat);
	}
	
	public static ConditionBuilder likeBuilder(){
		return builder("%%%s%%");
	}
}
