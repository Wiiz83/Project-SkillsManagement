package csv;

import java.io.IOException;

public abstract class CSVDeserializer {
	protected CSVConfig csvconfig;
	
	public CSVDeserializer() {
	}
	
	public CSVDeserializer(CSVConfig csvconfig) {
		this.csvconfig = csvconfig;
	}
	
	public void setCSVConfig(CSVConfig csvconfig) {
		this.csvconfig = csvconfig;
	}
	
	/**
	 * Crée un objet à partir de sa classe et sa représentation dans un fichier
	 * CSV
	 * 
	 * @param line
	 * @param c
	 * @return
	 * @throws IOException
	 * @throws CSVException
	 */
	public abstract <E extends CSVEntity> E Deserialize(CSVLine line, Class<?> c) throws IOException, CSVException;
	
}
