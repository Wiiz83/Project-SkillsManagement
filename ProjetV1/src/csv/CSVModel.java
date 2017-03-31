package csv;

/**
 * Objet de configuration des fichiers CSV et le mod�le des donn�es.
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
