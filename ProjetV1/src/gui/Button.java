package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import gui.BufferedHelper;

public class Button extends JComponent implements MouseListener {

	private static final long serialVersionUID = 1L;
	BufferedImage backgroundImage;
	BufferedImage backgroundImageHover;
	BufferedImage backgroundImageDisable;
	String etat;
	int width;
	int height;
	int x;
	int y;
	private ArrayList<ActionListener> listeners;

	/**
	 * Création d'un bouton personnalisé à partir d'une image et avec un masque sur le survol de souris
	 * @param url : Chemin vers l'image pour le bouton 
	 */
	public Button(String url) {
		super();
		try {
			this.backgroundImage = ImageIO.read(new File(getClass().getResource(url).toURI()));
			this.backgroundImageHover = BufferedHelper.generateMask(this.backgroundImage, Color.BLACK, 0.5f);
			this.backgroundImageDisable = BufferedHelper.generateMask(this.backgroundImage, Color.GRAY, 0.8f);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.listeners = new ArrayList<ActionListener>();
		enableInputMethods(true);
		addMouseListener(this);
		this.width = backgroundImage.getWidth();
		this.height = backgroundImage.getHeight();
	}

	/**
	 * Renvoi les dimensions de l'image
	 * @return Une Dimension contenant la hauteur et la largeur
	 */
	@Override
	public Dimension getPreferredSize() {
		if (backgroundImage == null) {
			return new Dimension(100, 100);
		} else {
			return new Dimension(this.width, this.height);
		}
	}

	/**
	 * Ajoute un listener au bouton
	 * @param listener ActionListener 
	 */
	public void addListener(ActionListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Execute les actions de chacun des listenners 
	 * @param e est un ActionEvent 
	 */
	public void raiseClickEvent(ActionEvent e) {
		for (ActionListener listener : this.listeners) {
			listener.actionPerformed(e);
		}
	}

	/**
	 * Dessine les boutons avec l'image initial 
	 * Actualise l'apparence du bouton en fonction de son état : survol ou désactivé 
	 * @param e est un ActionEvent 
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (this.backgroundImage != null) {
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.drawImage(this.backgroundImage, this.x, this.y, this.width, this.height, null);
			if (this.etat == "survol") {
				g2d.drawImage(this.backgroundImageHover, this.x, this.y, this.width, this.height, null);
			} else if (this.etat == "grisé") {
				g2d.drawImage(this.backgroundImageDisable, this.x, this.y, this.width, this.height, null);
			} else if (this.etat == "clicked") {
				g2d.drawImage(this.backgroundImageHover, this.x, this.y, this.width, this.height, null);
			}

			g2d.dispose();
		}
	}

	/**
	 * Positionne le bouton aux coordonnés 
	 * @param x est la position en abcisse
	 * @param y est la position en ordonnés  
	 */
	public void setBounds(int x, int y) {
		super.setBounds(x, y, this.width, this.height);
	}

	/**
	 * Au survol du bouton :
	 *  - Changement du curseur
	 *  - Appel de la fonction paintComponent pour afficher le masque sur le bouton
	 * @param e est un ActionEvent 
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		BufferedImage cursorPointingHandImage;
		try {
			cursorPointingHandImage = ImageIO.read(new File(getClass().getResource("/cursors/pointing_hand.png").toURI()));
			this.setCursor(tk.createCustomCursor(cursorPointingHandImage, new Point(4, 0), "Pointing Hand"));
		} catch (IOException | URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.etat = "survol";
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * Sortir du bouton : le bouton redevient à son état initial
	 * @param e est un ActionEvent 
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		this.etat = "normal";
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

}