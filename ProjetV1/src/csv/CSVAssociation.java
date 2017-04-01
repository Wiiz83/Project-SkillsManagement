package csv;

public class CSVAssociation {
	Class<? extends CSVEntity>	Entity;
	Class<? extends CSVEntity>	NEntity;
	
	/**
	 * Représente une association de cardinalité 1-N, N>0
	 * <p>
	 * Java: ArrayList
	 * </p>
	 * <p>
	 * Format fichier CSV : ID_objet; Clé_Etrangère_1, Clé_Etrangère_2 ...
	 * </p>
	 * 
	 * @param entity
	 * @param nEntity
	 */
	public CSVAssociation(Class<? extends CSVEntity> entity, Class<? extends CSVEntity> nEntity) {
		super();
		Entity = entity;
		NEntity = nEntity;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof CSVAssociation))
			return false;
		CSVAssociation assoc = (CSVAssociation) o;
		if (assoc.NEntity != null)
			return (assoc.Entity.equals(Entity) && assoc.NEntity.equals(NEntity));
		else
			return (assoc.Entity.equals(Entity) && this.NEntity == null);
	}
	
	@Override
	public int hashCode() {
		if (NEntity != null)
			return (Entity.getName() + NEntity.getName()).hashCode();
		else
			return Entity.getName().hashCode();
	}
	
}
