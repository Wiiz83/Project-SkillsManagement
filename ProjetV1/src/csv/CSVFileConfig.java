package csv;

public class CSVFileConfig {
	String path;
	boolean ignoreFirstLine;
	int idColumnPosition;
																
	public CSVFileConfig(String path, boolean ignoreFirstLine, int idColumnPosition) {
		super();
		this.path = path;
		this.ignoreFirstLine = ignoreFirstLine;
		this.idColumnPosition = idColumnPosition;
	}
}
