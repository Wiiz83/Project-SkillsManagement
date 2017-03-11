package csv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CSVMetadataMapper {
	private HashMap<Class<? extends CSVEntity>, // Entity
					ArrayList<Class<? extends CSVEntity>> // N-Entities
	> associations;
	
	private HashMap<CSVAssociation, // <Entity, N-Entity>
					String // CSV Path
	> csvPaths;
	
	private HashMap<String, // CSV path
					CSVFileConfig> csvFilesConfigs;
	
	public CSVMetadataMapper() {
		super();
		this.associations = new HashMap<>();
		this.csvPaths = new HashMap<>();
		this.csvFilesConfigs = new HashMap<>();
	}
	
	public void AddAssociationOrEntity(
			Class<? extends CSVEntity> E, Class<? extends CSVEntity> N, String path, boolean ignoreFirstLine,
			int idColumnPosition
	) {
		if (path.equals("./ressources/fichiers/missions_competences.csv")) {
			@SuppressWarnings("unused")
			int i = 0;
		}
		ArrayList<Class<? extends CSVEntity>> NList;
		
		if (!associations.containsKey(E))
			NList = new ArrayList<>();
		else
			NList = associations.get(E);
		NList.add(N);
		associations.put(E, NList);
		
		CSVAssociation assoc = new CSVAssociation(E, N);
		csvPaths.put(assoc, path);
		CSVFileConfig cfg = new CSVFileConfig(path, ignoreFirstLine, idColumnPosition);
		csvFilesConfigs.put(path, cfg);
		
	}
	
	public ArrayList<Class<? extends CSVEntity>> getAssociatedEntities(Class<? extends CSVEntity> Entity) {
		return associations.get(Entity);
	}
	
	public ArrayList<Class<? extends CSVEntity>> getReferencingEntities(Class<? extends CSVEntity> Entity) {
		ArrayList<Class<? extends CSVEntity>> referencingEntities = new ArrayList<>();
		for (Map.Entry<Class<? extends CSVEntity>, ArrayList<Class<? extends CSVEntity>>> pair : associations
				.entrySet()) {
			ArrayList<Class<? extends CSVEntity>> referenced = pair.getValue();
			if (referenced.contains(Entity)) {
				referencingEntities.add(pair.getKey());
			}
		}
		return referencingEntities;
	}
	
	public CSVFileConfig getCSVFileConfig(CSVAssociation assoc) {
		String path = csvPaths.get(assoc);
		CSVFileConfig cfg = csvFilesConfigs.get(path);
		return cfg;
	}
	
	public CSVFileConfig getCSVFileConfig(Class<? extends CSVEntity> E, Class<? extends CSVEntity> N) {
		CSVAssociation assoc = new CSVAssociation(E, N);
		return getCSVFileConfig(assoc);
	}
	
	public CSVFileConfig getCSVFileConfig(Class<? extends CSVEntity> E) {
		return getCSVFileConfig(E, null);
	}
}
