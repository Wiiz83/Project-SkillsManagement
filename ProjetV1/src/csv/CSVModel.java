package csv;

/**
 * Objet de configuration des fichiers CSV et le modèle des données.
 *
 */
public abstract class CSVModel {
	
	private CSVMetadataMapper metadata;
	
	public CSVModel() {
		metadata = new CSVMetadataMapper();
		defineModel();
	}
	
	public CSVMetadataMapper Metadata() {
		return metadata;
	}
	
	public abstract void defineModel();
	
}
