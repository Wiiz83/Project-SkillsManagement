package gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

public class Titre extends JLabel {

	private static final long serialVersionUID = 1L;

	/**
	 * Cr�ation d'un titre type pour le programme : fond bleu rectangulaire et police blanche 
	 */
	public Titre(String texte) {
		setText(texte);
		setOpaque(true);
		setForeground(Color.WHITE);
		setFont(new Font("Calibri", Font.BOLD, 14));
		setBackground(new Color(0, 72, 136));
	}
}
