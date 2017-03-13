package csv;

/**
 * Objet de configuration d'un document CSV (chemin du fichier, ignorer la
 * première ligne, colonne de l'ID)
 *
 */
public class CSVFileConfig {
	String	path;
	boolean	ignoreFirstLine;
	int		idColumnPosition;
	
	public CSVFileConfig(String path, boolean ignoreFirstLine, int idColumnPosition) {
		super();
		this.path = path;
		this.ignoreFirstLine = ignoreFirstLine;
		this.idColumnPosition = idColumnPosition;
	}
}
