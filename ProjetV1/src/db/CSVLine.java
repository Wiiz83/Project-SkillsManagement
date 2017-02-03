package db;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("serial")
public class CSVLine extends ArrayList<String>{
	public CSVLine(String line) {
		this.addAll(Arrays.asList(line.split(";")));
}
	public String toString(){
		String line="";
		for (String s : this){
			line+=s+";";
		}
		return line.substring(0, line.length()-1);
	}
}
