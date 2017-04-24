package navigation;

import models.Mission;

public class Alerte {
	String	type;
	String	desc;
	
	public Alerte(String type, String desc) {
		super();
		this.type = type;
		this.desc = desc;
	}
	
	public Alerte(Mission M) {
		this.type = "Employ�s non affect�s";
		this.desc = M.getNomM() + "(" + M.getDateDebut() + ")";
	}
	
	public String getType() {
		return type;
	}
	
	public String getDesc() {
		return desc;
	}
	
}