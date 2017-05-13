package csv;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Fournit les les méthodes d'ajout d'un objet et ses dépendances dans les
 * fichiers CSV
 *
 * @param <E>
 */
public class CSVObjectSaver<E extends CSVEntity> {
	
	CSVConfig config;
	
	public CSVObjectSaver(CSVConfig config) {
		super();
		this.config = config;
	}
	
	public void addObject(E e)
			throws IOException, CSVUpdateException, NumberFormatException, ParseException, CSVException {
		if (e.isAttached()) {
			throw new CSVUpdateException("Object is already attached");
		} else {
			
		}
		CSVSerializer serializer = config.getSerializer();
		CSVLine line = serializer.Serialize(e);
		CSVDocument doc = config.getDocument(e.getClass());
		doc.addLine(line);
		saveObjectReferences(e);
		e.setAttached();
	}
	
	private void saveObjectReferences(E e) throws IOException, NumberFormatException, ParseException, CSVException {
		HashMap<Class<? extends CSVEntity>, ArrayList<? extends CSVEntity>> associatedObjects = e
				.getReferencedObjects();
		CSVModel model = config.getModel();
		for (Class<? extends CSVEntity> N : model.Metadata().getAssociatedEntities(e.getClass())) {
			if (N == null || associatedObjects == null)
				continue;
			if (associatedObjects.get(N).size() == 0)
				continue;
			CSVAssociation assoc = new CSVAssociation(e.getClass(), N);
			CSVDocument assocDoc = config.getDocument(assoc);
			CSVObjects<? extends CSVEntity> nObjects = new CSVObjects<>(N, config);
			// Check that the referenced objects already exist in the CSV
			// documents
			for (CSVEntity nEntity : associatedObjects.get(N)) {
				CSVEntity n = nObjects.getByID(nEntity.csvID());
				if (n == null) {
					throw new InvalidCSVException("Referenced object does not exist: " + nEntity);
				}
			}
			CSVLine assocLine = new CSVLine();
			assocLine.add(e.csvID());
			assocLine.addAll(getIDS(associatedObjects.get(N)));
			assocDoc.addOrModifyLine(e.csvID(), assocLine);
		}
	}
	
	private ArrayList<String> getIDS(ArrayList<? extends CSVEntity> ReferencedObjects) {
		ArrayList<String> ids = new ArrayList<String>();
		for (CSVEntity e : ReferencedObjects) {
			ids.add(e.csvID());
		}
		return ids;
	}
	
}