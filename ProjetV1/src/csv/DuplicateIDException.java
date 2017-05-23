package csv;

public class DuplicateIDException extends CSVUpdateException {
	
	@Override
	public String toString() {
		return "DuplicateIDException [ID=" + ID + ", docPath=" + docPath + "]";
	}
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
		this.ID = ID;
		this.docPath = docPath;
	}
}
