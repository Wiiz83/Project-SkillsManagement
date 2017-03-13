package csv;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Représente une ligne d'un document CSV
 *
 */
@SuppressWarnings("serial")
public class CSVLine extends ArrayList<String> {
	
	public boolean add(String line) {
		return this.addAll(Arrays.asList(line.split(";")));
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
