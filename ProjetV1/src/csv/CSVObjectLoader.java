package csv;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Fournit les méthodes de chargement d'un objet et ses dépendances
 *
 * @param <E>
 */
public class CSVObjectLoader<E extends CSVEntity> {
	
	private Class<? extends CSVEntity>	EntityType;
	CSVCache							cache;
	
	public CSVObjectLoader(Class<? extends CSVEntity> c, CSVCache cache) throws IOException {
		super();
		EntityType = c;
		this.cache = cache;
	}
	
	/**
	 * Crée l'objet et charge ses dépendances
	 * 
	 * @param line
	 * @return
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws ParseException
	 * @throws CSVException
	 */
	public E createObject(CSVLine line) throws IOException, NumberFormatException, ParseException, CSVException {
		E object = CSVDeserializer.Deserialize(line, EntityType, cache);
		object.setReferencedObjects(getAssociatedObjects(object));
		object.setAttached();
		return object;
	}
	
	private HashMap<Class<? extends CSVEntity>, ArrayList<Object>> getAssociatedObjects(E e)
			throws IOException, NumberFormatException, ParseException, CSVException {
		
		HashMap<Class<? extends CSVEntity>, ArrayList<Object>> AssociatedObjects = new HashMap<>();
		CSVModel model = new CSVModel();
		for (Class<? extends CSVEntity> N : model.getMetadata().getAssociatedEntities(e.getClass())) {
			if (N == null)
				continue;
			CSVAssociation assoc = new CSVAssociation(e.getClass(), N);
			CSVObjects<? extends CSVEntity> nObjects = new CSVObjects<>(N, cache);
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
