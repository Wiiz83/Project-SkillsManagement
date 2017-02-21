package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.RGBImageFilter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.w3c.dom.css.RGBColor;

import navigation.Navigation;
import navigation.Page;
import program.Program;
import utilities.Button;
import utilities.Vector2;

public class Header extends JPanel implements ActionListener {
	private static final long		serialVersionUID	= 1L;
	private static final boolean	DEBUG				= false;

	// Prend le curseur prédéfini
	public static Cursor newCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	private Image logo;

	public static Button boutonAccueil;
	public static Button boutonCompetences;
	public static Button boutonPersonnel;
	public static Button boutonMissions;



	public Header() {
		super();

		try {
			this.logo = ImageIO.read(getClass().getResource("/images/logo.png"));
		}
		catch (IOException e) {
			System.out.println("erreur chargement fond");
			e.printStackTrace();
		}	
		
		
		this.boutonAccueil = new Button("/boutons/accueil.png");
		this.boutonCompetences = new Button("/boutons/competences.png");
		this.boutonPersonnel = new Button("/boutons/personnel.png");
		this.boutonMissions = new Button("/boutons/missions.png");

		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));

		JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // new FlowLayout not needed
		subPanel.setOpaque(false);
		subPanel.add(boutonAccueil);
		subPanel.add(boutonCompetences);
		subPanel.add(boutonPersonnel);
		subPanel.add(boutonMissions);
		add(subPanel);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D batch = (Graphics2D) g;
		batch.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		batch.setColor(new Color(0, 72, 136));
		batch.fillRect(0, 0, 1280, 70);
		g.drawImage(logo, 1215, 9, 50, 50, null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
	
}
