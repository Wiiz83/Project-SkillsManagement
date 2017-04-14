package csv;

import java.io.IOException;

public interface CSVDeserializer {
	
	/**
	 * Cr�e un objet � partir de sa classe et sa repr�sentation dans un fichier
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