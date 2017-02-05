package gui;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import program.Program;

public class HomePage extends Page implements ActionListener {

	private static final int POS_X	= 488;

	/**
	 * Bouton représentant le choix de 1 joueur
	 */
	private BlokusButton buttonHomePage;

	/**
	 * Bouton représentant le choix de quitter le jeu
	 */
	private BlokusButton buttonExit;

	/**
	 * Image représentant le logo du jeu
	 */
	private BufferedImage titre;

	/**
	 * Constructeur
	 */
	public HomePage() {
		super();
	}

	@Override
	public void updatePage(float elapsedTime) {
		GraphicsPanel.newCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
		/*this.buttonHomePage.update(elapsedTime);
		this.buttonExit.update(elapsedTime);*/
	}

	@Override
	public void drawPage(Graphics2D g) {
		/*Graphics2D batch = (Graphics2D) g.create();
		batch.drawImage(this.titre, 500, 51, null);
		this.buttonHomePage.draw(batch);
		this.buttonExit.draw(batch);
		batch.dispose();*/

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*if (e.getSource() instanceof BlokusButton) {
			if (e.getSource().equals(this.buttonHomePage)) {
				Navigation.NavigateTo(Navigation.homePage);
			}
			else if (e.getSource().equals(this.buttonExit)) {
				System.exit(0);
			}
		}*/
	}

	@Override
	public void loadContents() {
		/*try {
			this.titre = ImageIO.read(getClass().getResourceAsStream(Page.PATH_RESOURCES_IMAGES + "logo.png"));
		}
		catch (IOException e) {
			this.titre = null;
			e.printStackTrace();
		}

		try {
			BufferedImage checked = ImageIO.read(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "musicon.png"));
			BufferedImage noChecked = ImageIO
					.read(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "musicoff.png"));
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}

		this.buttonHomePage = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "oneplayer.png"));
		this.buttonHomePage.setPosition(new Vector2(POS_X, 213));
		this.buttonHomePage.addListener(this);

		this.buttonExit = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "exit.png"));
		this.buttonExit.setPosition(new Vector2(POS_X, 642));
		this.buttonExit.addListener(this);*/
	}

	@Override
	public void unloadContents() {}

}

