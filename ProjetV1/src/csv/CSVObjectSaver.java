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
	
	private CSVDocument doc;
	
	public CSVObjectSaver(CSVDocument doc) {
		super();
		this.doc = doc;
	}
	
	public void addObject(E e)
			throws IOException, CSVUpdateException, NumberFormatException, ParseException, CSVException {
		if (e.isAttached()) {
			throw new CSVUpdateException("Object is already attached");
		} else {
			
		}
		CSVLine line = CSVSerializer.Serialize(e);
		doc.addLine(line);
		saveObjectReferences(e);
		e.setAttached();
	}
	
	private void saveObjectReferences(E e) throws IOException, NumberFormatException, ParseException, CSVException {
		HashMap<Class<? extends CSVEntity>, ArrayList<String>> associatedObjectsIDS = e.getReferencedObjectsIDS();
		CSVModel model = new CSVModel();
		for (Class<? extends CSVEntity> N : model.getMetadata().getAssociatedEntities(e.getClass())) {
			if (N == null)
				continue;
			CSVAssociation assoc = new CSVAssociation(e.getClass(), N);
			CSVDocument assocDoc = new CSVDocument(assoc);
			CSVObjects<? extends CSVEntity> nObjects = new CSVObjects<>(N);
			// Check that the referenced objects already exist in the CSV
			// documents
			for (String ID : associatedObjectsIDS.get(N)) {
				CSVEntity n = nObjects.getByID(ID);
				if (n == null) {
					throw new InvalidCSVException("Referenced object with ID: " + ID + " does not exist.");
				}
			}
			CSVLine assocLine = new CSVLine();
			assocLine.add(e.csvID());
			assocLine.addAll(associatedObjectsIDS.get(N));
			assocDoc.addOrModifyLine(e.csvID(), assocLine);
		}
	}
}