package db;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class CSVDocument  {
	private String path ;
	ArrayList<CSVLine> set; 
	
	
	CSVDocument(String path) {
		this.path=path;

	}
	
	ArrayList<CSVLine> getAll() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(path));		 
		String line = null;
		while ((line = br.readLine()) != null) {
			set.add(new CSVLine(line));
		}	 
		br.close();
		return set;
	}
	
	public void Add (CSVLine line){
		try (BufferedWriter writer = Files.newBufferedWriter(path, "US-ASCII	",StandardOpenOption.APPEND)) {
		    writer.write(line.toString());
		} catch (IOException ioe) {
		    System.err.format("IOException: %s%n", ioe);
		}
	}
	
}
