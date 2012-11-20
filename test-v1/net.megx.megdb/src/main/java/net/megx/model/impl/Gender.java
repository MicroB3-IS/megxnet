package net.megx.model.impl;

public enum Gender {
	//ISO gender code for sex. 0=unknown; 1=male; 2=female; 9=not applicable
	UNKNOWN(0), MALE(1), FEMALE(2), NOT_APPLICABLE(9);
	
	private int code;
	
	Gender(int code){
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
	
	@Override public String toString() {
		   //only capitalize the first letter
		   String s = super.toString();
		   return s.toLowerCase();
		 }
	
}
