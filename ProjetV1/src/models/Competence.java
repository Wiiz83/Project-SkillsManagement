package models;

import java.util.ArrayList;

public class Competence {
	private CompetenceCode code ;
	private ArrayList<String> Names ;
	
	
	public Competence(CompetenceCode code, ArrayList<String> names) {
		super();
		this.code = code;
		Names = names;
	}
	
	
	public CompetenceCode getCode() {
		return code;
	}
	public void setCode(CompetenceCode code) {
		this.code = code;
	}
	
	public void setCode(String code) throws InvalidDataException {
		this.code = new CompetenceCode(code);
	}


	public ArrayList<String> getNames() {
		return Names;
	}

	public void setNames(ArrayList<String> names) {
		Names = names;
	}
	@Override
	public String toString() {
		return "[code=" + code + ", Names=" + Names + "]";
	}
	
 

}
