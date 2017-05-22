package csv;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Représente une ligne d'un document CSV
 *
 */
public class CSVLine extends ArrayList<String> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5261929444386511832L;
	
	public boolean add(String line) {
		return this.addAll(Arrays.asList(line.split("\\;", -1)));
	}
	
	// Empty Lines detection
	public boolean isValid() {
		boolean valid = this.size() > 1;
		if (valid)
			valid &= this.get(1) != null;
		return valid;
	}
	
	public String toString() {
		String line = "";
		for (String s : this) {
			line += s + ";";
		}
		return line.substring(0, line.length() - 1);
	}
	
	public boolean equals(Object o) {
		return o.toString().equals(this.toString());
	}
	
}
