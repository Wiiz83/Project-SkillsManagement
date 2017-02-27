package csv;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CSVObjectLoader<E extends CSVEntity> {
	
	private Class<? extends CSVEntity> EntityType;
	
	public CSVObjectLoader(Class<? extends CSVEntity> c) throws IOException {
		super();
		EntityType = c;
	}
	
	public E createObject(CSVLine line)
			throws InvalidCSVException, InvalidDataException, IOException, NumberFormatException, ParseException {
		E object = CSVDeserializer.Deserialize(line, EntityType);
		object.setReferencedObjects(getAssociatedObjects(object));
		return object;
	}
	
	private HashMap<Class<? extends CSVEntity>, ArrayList<Object>> getAssociatedObjects(E e)
			throws IOException, InvalidCSVException, InvalidDataException, NumberFormatException, ParseException {
		
		HashMap<Class<? extends CSVEntity>, ArrayList<Object>> AssociatedObjects = new HashMap<>();
		CSVModel model = new CSVModel();
		for (Class<? extends CSVEntity> N : model.getMetadata().getAssociatedEntities(e.getClass())) {
			if (N == null)
				continue;
			CSVAssociation assoc = new CSVAssociation(e.getClass(), N);
			CSVObjects<? extends CSVEntity> nObjects = new CSVObjects<>(N);
			ArrayList<Object> AssociatedNObjects;
			for (String nID : getAssociatedIDS(e.csvID(), assoc)) {
				Object n = nObjects.getByID(nID);
				if (n != null) {
					if (!AssociatedObjects.containsKey(N)) {
						AssociatedNObjects = new ArrayList<>();
					} else {
						AssociatedNObjects = AssociatedObjects.get(N);
					}
					AssociatedNObjects.add(n);
				} else
					throw new InvalidCSVException("Refrenced object could not be found");
				AssociatedObjects.put(N, AssociatedNObjects);
			}
		}
				
		return AssociatedObjects;
	}
	
	private ArrayList<String> getAssociatedIDS(String ID, CSVAssociation assoc) throws IOException {
		CSVDocument assocDoc = new CSVDocument(assoc);
		CSVLine line = assocDoc.getLineByID(ID);
		if (line != null)
			line.remove(0);
		else
			return new ArrayList<String>();
		return line;
	}
	
}
