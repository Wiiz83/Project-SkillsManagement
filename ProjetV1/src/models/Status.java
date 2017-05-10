package models;

/**
 * @author David Enumération des status de missions
 */
public enum Status {
	
	PREPARATION("Préparation"), PLANIFIEE("Planifiée"), EN_COURS("En cours"), TERMINEE("Terminée"), EN_RETARD("En retard");
	private String value;
	
	private Status(String s) {
		this.value = s;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
