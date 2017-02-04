package db;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class CSVDocument {
	private String path;
	private ArrayList<CSVLine> set;
	private boolean ignoreFirstLine;

	public CSVDocument(String path) {
		this.path = path;
		set = new ArrayList<CSVLine>();
		ignoreFirstLine=false;
	}

	public CSVDocument(String path, boolean ignoreFirstLine) {
		this(path);
		this.ignoreFirstLine = ignoreFirstLine;
	}

	public ArrayList<CSVLine> getAll() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = null;
		boolean ignore = ignoreFirstLine;
		while ((line = br.readLine()) != null) {
			if (!ignore) {
				ignore = false;
				set.add(new CSVLine(line));
			}
		}
		br.close();
		return set;
	}

	public void Add(CSVLine line) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), Charset.forName("UTF-8"),
				(OpenOption) StandardOpenOption.APPEND)) {
			writer.write(line.toString());
			set.add(line);
		} catch (IOException e) {
			set.remove(set.size() - 1);
			throw e;
		}
	}
}
