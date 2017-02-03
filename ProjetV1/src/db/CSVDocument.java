package db;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
	
	public void Add (CSVLine line) throws IOException{
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), Charset.forName("UTF-8"),(OpenOption)StandardOpenOption.APPEND )) {
		    writer.write(line.toString());
		}
	}
	
}
