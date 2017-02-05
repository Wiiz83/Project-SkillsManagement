package gui;

import gui.HomePage;
import gui.Page;

public class Navigation {
	
	/**
	 * L'état de jeu
	 */
	private static Page page;
	
	/**
	 * L'état de jeu : Page d'accueil
	 */
	public static Page homePage = new HomePage();
	
	/**
	 * La page sur laquelle on était avant (apres clic sur retour)
	 */
	public static Page previous = null;
	
	
	/**
	 * Change l'état de jeu et navigue vers cet état
	 * @param p Le nouvel état de jeu
	 */
	public static void NavigateTo(Page p)
	{
		Page oldpage = page;
		page = p;
		
		if(oldpage != null && p != oldpage)
		{
			oldpage.unloadContents();
		}
		
		page.loadContents();
	}
	
	
	
	/**
	 * Obtient l'état courant du jeu
	 * @return L'état courant du jeu
	 */
	public static Page getPage()
	{
		return page;
	}
}
