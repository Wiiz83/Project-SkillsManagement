package csv;

import java.io.IOException;

public class CSVObjectDeleter<E extends CSVEntity> {
	
	private CSVDocument doc;
	
	public CSVObjectDeleter(CSVDocument doc) {
		super();
		this.doc = doc;
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
		doc.removeLine(e.csvID());
		e.setAttached(false);
	}
	
	private void deleteReferencesToObject(E e) throws IOException, InvalidCSVException {
		CSVModel model = new CSVModel();
		for (Class<? extends CSVEntity> N : model.getMetadata().getReferencingEntities(e.getClass())) {
			if (N == null)
				continue;
			CSVAssociation assoc = new CSVAssociation(N, e.getClass());
			CSVDocument assocDoc = new CSVDocument(assoc);
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
					assocDoc.modifiyLineByID(line.get(0), newline);
				}
			}
		}
	}
	
	void deleteAssociations(E e) throws IOException {
		CSVModel model = new CSVModel();
		for (Class<? extends CSVEntity> N : model.getMetadata().getAssociatedEntities(e.getClass())) {
			if (N == null)
				continue;
			CSVAssociation assoc = new CSVAssociation(e.getClass(), N);
			CSVDocument assocDoc = new CSVDocument(assoc);
			assocDoc.removeLine(e.csvID());
		}
	}
}