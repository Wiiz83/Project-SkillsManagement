package csv;

public class DuplicateIDException extends CSVUpdateException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String				ID, docPath;
	
	public String getID() {
		return ID;
	}
	
	public String getDocPath() {
		return docPath;
	}
	DuplicateIDException(String ID, String docPath) {
		super();
	}
}
