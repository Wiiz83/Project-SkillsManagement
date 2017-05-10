package models;

/**
 * @author David Enum�ration des status de missions
 */
public enum Status {
	
	PREPARATION("Pr�paration"), PLANIFIEE("Planifi�e"), EN_COURS("En cours"), TERMINEE("Termin�e"), EN_RETARD("En retard");
	private String value;
	
	private Status(String s) {
		this.value = s;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
