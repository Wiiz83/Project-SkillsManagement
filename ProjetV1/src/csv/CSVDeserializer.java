package csv;

import java.io.IOException;

public interface CSVDeserializer {
	
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
	<E extends CSVEntity> E Deserialize(CSVLine line, Class<?> c) throws IOException, CSVException;
	
}