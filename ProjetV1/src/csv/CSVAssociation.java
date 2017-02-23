package csv;

public class CSVAssociation {
	Class<? extends CSVEntity>	Entity;
	Class<? extends CSVEntity>	NEntity;
	
	// One to Many Association (csv: ID;T1;T2..)
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
