package navigation;

import models.Mission;
import models.Status;

public class Alerte {
	String	type;
	String	desc;
	
	public Alerte(String type, String desc) {
		super();
		this.type = type;
		this.desc = desc;
	}
	
	public Alerte(Mission M) {
		
		switch (M.getStatus()) {
		
		case PREPARATION:
			this.type = "Employés non affectés";
			break;
		case WARNING:
			this.type = "Mission en retard";
			break;
		default:
			break;
		}
		this.desc = M.getNomM() + "(" + M.getDateDebut() + ")";
	}
	
	public String getType() {
		return type;
	}
	
	public String getDesc() {
		return desc;
	}
	
}