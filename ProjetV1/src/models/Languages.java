package models;

/**
 * @author David Enumeration des différents langages de l'application
 */
public enum Languages {
	FRENCH, ENGLISH;

	public static final int size = Languages.values().length;
	public static Languages Current = Languages.FRENCH;
}
