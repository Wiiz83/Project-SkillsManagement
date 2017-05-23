package data;

@SuppressWarnings("serial")
public class DataException extends Exception {
	
	public DataException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DataException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	public DataException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	public DataException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	public Throwable getRootCause() {
		Throwable cause = null;
		Throwable result = this;
		
		while (null != (cause = result.getCause()) && (result != cause)) {
			result = cause;
		}
		return result;
	}
	
}
