package csv;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Fournit des méthodes de suppression des objets, leurs associations, et les
 * références vers l'objet.
 *
 * @param <E>
 */
public class CSVObjectDeleter<E extends CSVEntity> {
	
	private CSVConfig config;
	
	public CSVObjectDeleter(CSVConfig config) {
		super();
		this.config = config;
	}
	
	public void deleteObject(E e) throws IOException, InvalidCSVException, CSVUpdateException {
		deleteObject(e, true);
	}
	
	public void deleteObject(E e, boolean deleteReferencesToObject)
			throws IOException, InvalidCSVException, CSVUpdateException {
		
		if (!e.isAttached()) {
			throw new CSVUpdateException("Object to delete is not attached");
		}
		if (deleteReferencesToObject)
			deleteReferencesToObject(e);
		deleteAssociations(e);
		config.getDocument(e.getClass()).removeLine(e.csvID());
		e.setAttached(false);
	}
	
	private void deleteReferencesToObject(E e) throws IOException, InvalidCSVException {
		CSVModel model = config.getModel();
		for (Class<? extends CSVEntity> N : model.Metadata().getReferencingEntities(e.getClass())) {
			if (N == null)
				continue;
			CSVAssociation assoc = new CSVAssociation(N, e.getClass());
			CSVDocument assocDoc = config.getDocument(assoc);
			
			HashMap<String, CSVLine> modifiedLines = new HashMap<>();
			
			for (CSVLine line : assocDoc.getAll()) {
				CSVLine newline = new CSVLine();
				newline.add(line.get(0));
				boolean found = false;
				for (int i = 1; i < line.size(); i++) {
					String column = line.get(i);
					if (!column.equals(e.csvID())) {
						newline.add(column);
					} else {
						found = true;
					}
				}
				if (found) {
					modifiedLines.put(line.get(0), newline);
				}
			}
			for (Entry<String, CSVLine> entry : modifiedLines.entrySet())
				assocDoc.modifiyLineByID(entry.getKey(), entry.getValue());
		}
	}
	
	void deleteAssociations(E e) throws IOException {
		CSVModel model = config.getModel();
		for (Class<? extends CSVEntity> N : model.Metadata().getAssociatedEntities(e.getClass())) {
			if (N == null)
				continue;
			CSVAssociation assoc = new CSVAssociation(e.getClass(), N);
			CSVDocument assocDoc = config.getDocument(assoc);
			assocDoc.removeLine(e.csvID());
		}
	}
}