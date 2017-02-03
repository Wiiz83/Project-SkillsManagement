package models;

public class CompetenceCode {
	private char category;
	private int subCategory;
	
	public CompetenceCode(char category, int subCategory) {
		super();
		this.category = category;
		this.subCategory = subCategory;
	}
	
	public CompetenceCode(String code) throws InvalidDataException{
		if (code.length()<3)
			throw new InvalidDataException();
		if (code.charAt(1)!='.')
			throw new InvalidDataException();
		category=Character.toUpperCase(code.charAt(0));
		if (category <'A' || category > 'Z')
			throw new InvalidDataException();			
		try {
			subCategory= Integer.parseInt(code.substring(2) );
		}
		catch (NumberFormatException e){
			throw new InvalidDataException(e);
		}
	}
	

	@Override
	public String toString() {
		return "category"+'.'+  subCategory;
	}

	public char getCategory() {
		return category;
	}

	public void setCategory(char category) {
		this.category = category;
	}

	public int getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(int subCategory) {
		this.subCategory = subCategory;
	}
	
}
