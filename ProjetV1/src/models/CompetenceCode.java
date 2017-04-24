package models;

import java.io.Serializable;

import csv.InvalidDataException;

/**
 * Représente le code d'une compétence
 */
public class CompetenceCode implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 7950636579110754928L;
	private char				category;
	private int					subCategory;
	
	/**
	 * @param category
	 * @param subCategory
	 *            Constructeur pour les code de type 'A' . 1 (A.1)
	 */
	public CompetenceCode(char category, int subCategory) {
		super();
		this.category = category;
		this.subCategory = subCategory;
	}
	
	/**
	 * @param code
	 * @throws InvalidDataException
	 *             Constructeur pour les codes de types "A.1"
	 */
	public CompetenceCode(String code) throws InvalidDataException {
		System.out.println(code);
		if (code.length() < 3)
			throw new InvalidDataException();
		if (code.charAt(1) != '.' || code.charAt(code.length() - 1) != '.')
			throw new InvalidDataException();
		category = Character.toUpperCase(code.charAt(0));
		if (category < 'A' || category > 'Z')
			throw new InvalidDataException();
		try {
			subCategory = Integer.parseInt(code.substring(2, code.length() - 1));
		} catch (NumberFormatException e) {
			throw new InvalidDataException(e);
		}
	}
	
	public String toString() {
		return category + "." + subCategory + ".";
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + category;
		result = prime * result + subCategory;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CompetenceCode))
			return false;
		CompetenceCode other = (CompetenceCode) obj;
		if (category != other.category)
			return false;
		if (subCategory != other.subCategory)
			return false;
		return true;
	}
	
}
