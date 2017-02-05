package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;

public abstract class Page implements ActionListener {
	
	/**
	 * Constante d'accès au dossier de ressource des images
	 */
	public static final String PATH_RESOURCES_IMAGES = "/images/";
	
	/**
	 * Constante d'accès au dossier de resources des boutons
	 */
	public static final String PATH_RESOURCES_BOUTONS = "/boutons/";
	
	/**
	 * Constante d'accès au dossier de resources des fonts
	 */
	public static final String PATH_RESOURCES_FONTS = "/fonts/";
	
	/**
	 * Constante d'accès au dossier de resources des animation
	 */
	public static final String PATH_RESOURCES_ANIMATION = "/animation/";
	
	/**
	 * Si il est activé ou non
	 */
	protected boolean enabled;
	
	/**
	 * Constructeur de la page
	 */
	public Page()
	{
		this.enabled = true;
	}
	
	/**
	 * Méthode de chargement du contenu de l'état de jeu
	 */
	public abstract void loadContents();
	
	/**
	 * Méthode de déchargement du contenu de l'état de jeu
	 */
	public abstract void unloadContents();
	
	/**
	 * Procédure abstraite qui rafraichit les données
	 * @param elapsedTime
	 */
	public abstract void updatePage(float elapsedTime);
	
	/**
	 * Procédure abstraite qui dessine l'objet
	 * @param g
	 */
	public abstract void drawPage(Graphics2D g);
	
	public final void update(float elapsedTime)
	{
		if(this.enabled)
		{
			this.updatePage(elapsedTime);
		}
	}
	
	public final void draw(Graphics2D g)
	{
		this.drawPage(g);
		
		if(!this.enabled)
		{
			g.setPaint(new Color(0, 0, 0, 120));
			g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		}
	}
	
	/**
	 * Setter de l'état de la page
	 * 
	 * @param state état
	 */
	public void setEnabled(boolean state)
	{
		this.enabled = state;
	}
	
}
