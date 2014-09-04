package net.megx.megdb.exceptions;

public class DBNoRecordsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public DBNoRecordsException(){
		super();
	}
	
	public DBNoRecordsException(String message){
		super(message);
		this.message = message;
	}
	
	public DBNoRecordsException(Throwable t){
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
