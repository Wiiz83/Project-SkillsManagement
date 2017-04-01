package csv;

public class CSVAssociation {
	Class<? extends CSVEntity>	Entity;
	Class<? extends CSVEntity>	NEntity;
	
	/**
	 * Repr�sente une association de cardinalit� 1-N, N>0
	 * <p>
	 * Java: ArrayList
	 * </p>
	 * <p>
	 * Format fichier CSV : ID_objet; Cl�_Etrang�re_1, Cl�_Etrang�re_2 ...
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
