package csv;

public abstract class CSVSerializer {
	public CSVSerializer() {
		super();
	}
	
	public abstract CSVLine Serialize(Object o);
}