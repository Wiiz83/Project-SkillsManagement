package csv;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/**
 * Représente un document CSV et fournit les méthodes de lecture / écriture
 *
 */
public class CSVDocument {
	private String				path;
	private ArrayList<CSVLine>	set;
	private boolean				ignoreFirstLine;
	private int					idColumnPosition;
	
	public CSVDocument(CSVAssociation assoc) throws IOException {
		CSVModel model = new CSVModel();
		CSVMetadataMapper mapper = model.getMetadata();
		CSVFileConfig cfg = mapper.getCSVFileConfig(assoc);
		path = cfg.path;
		ignoreFirstLine = cfg.ignoreFirstLine;
		idColumnPosition = cfg.idColumnPosition;
		set = getAll();
		
	}
	
	public CSVDocument(Class<? extends CSVEntity> entity) throws IOException {
		assert (entity != null);
		CSVModel model = new CSVModel();
		CSVMetadataMapper mapper = model.getMetadata();
		CSVFileConfig cfg = mapper.getCSVFileConfig(entity);
		path = cfg.path;
		ignoreFirstLine = cfg.ignoreFirstLine;
		idColumnPosition = cfg.idColumnPosition;
		set = getAll();
		
	}
	
	public CSVLine getLineByID(String ID) {
		for (CSVLine line : set) {
			if (line.get(idColumnPosition).equals(ID))
				return line;
		}
		return null;
	}
	
	/**
	 * @return List of the document lines
	 * @throws IOException
	 */
	public ArrayList<CSVLine> getAll() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = null;
		ArrayList<CSVLine> set = new ArrayList<>();
		boolean ignore = ignoreFirstLine;
		while ((line = br.readLine()) != null) {
			if (!ignore) {
				CSVLine csvline = new CSVLine();
				csvline.add(line);
				if (csvline.isValid())
					set.add(csvline);
			} else
				ignore = false;
		}
		br.close();
		if (set.size() > 0)
			if (!set.get(set.size() - 1).isValid())
				set.remove(set.size() - 1);
		return set;
	}
	
	/**
	 * Adds a CSVline object the internal set and to the CSV file.
	 * 
	 * @param line
	 * @throws IOException
	 */
	public void addLine(CSVLine line) throws IOException {
		
		try (BufferedWriter writer = Files.newBufferedWriter(
				Paths.get(path), Charset.forName("UTF-8"), StandardOpenOption.APPEND
		)) {
			writer.write(System.lineSeparator());
			writer.write(line.toString());
			set.add(line);
		} catch (IOException e) {
			set.remove(set.size() - 1);
			throw e;
		}
	}
	
	public boolean removeLine(String ID) throws IOException {
		boolean ignore = ignoreFirstLine;
		boolean found = false;
		
		File inFile = new File(path);
		File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
		BufferedReader br = new BufferedReader(new FileReader(path));
		PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
		
		try {
			String line;
			while ((line = br.readLine()) != null) {
				if (!ignore) {
					CSVLine csvline = new CSVLine();
					csvline.add(line);
					boolean deleteLine = csvline.isValid();
					if (deleteLine && csvline.size() > idColumnPosition)
						deleteLine = ID.equals(csvline.get(idColumnPosition));
					if (!deleteLine && csvline.isValid()) {
						pw.println(line);
						// pw.flush();
					} else if (csvline.isValid()) {
						found = true;
						int i = -1;
						for (CSVLine set_line : set) {
							i++;
							if (set_line.get(idColumnPosition).equals(csvline.get(idColumnPosition))) {
								break;
							}
						}
						set.remove(i);
					}
				} else {
					pw.println(line);
					// pw.flush();
					ignore = false;
				}
			}
			
		} finally {
			pw.close();
			br.close();
			inFile.delete();
			tempFile.renameTo(inFile);
		}
		return found;
	}
	
	public void addOrModifyLine(String ID, CSVLine newLine) throws IOException, InvalidCSVException {
		if (getLineByID(ID) == null) {
			addLine(newLine);
		} else {
			modifiyLineByID(ID, newLine);
		}
	}
	
	public void modifiyLineByID(String ID, CSVLine newLine) throws IOException, InvalidCSVException {
		assert (ID.equals(newLine.get(idColumnPosition)));
		boolean found = removeLine(ID);
		if (found) {
			addLine(newLine);
		} else {
			throw new InvalidCSVException("Line to be modified not found: ID=" + ID);
		}
	}
	
	public int getIdColumnPosition() {
		return idColumnPosition;
	}
	
	public String getPath() {
		return path;
	}
	
	public ArrayList<Integer> getIDS() throws InvalidDataException {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for (CSVLine line : this.set) {
			Integer id = -1;
			try {
				id = Integer.parseInt(line.get(idColumnPosition));
			} catch (NumberFormatException e) {
				throw new InvalidDataException(e);
			}
			ids.add(id);
		}
		return ids;
	}
	
	public int lineCount() {
		return set.size();
	}
}
