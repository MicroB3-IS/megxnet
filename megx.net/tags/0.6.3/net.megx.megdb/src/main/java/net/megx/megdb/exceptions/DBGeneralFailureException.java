package net.megx.megdb.exceptions;

public class DBGeneralFailureException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public DBGeneralFailureException(){
		super();
	}
	
	public DBGeneralFailureException(String message){
		super(message);
		this.message = message;
	}
	
	public DBGeneralFailureException(Throwable t){
		super(t);
	}
	
	@Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }
}
