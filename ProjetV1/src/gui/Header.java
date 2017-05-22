package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import data.Data;
import navigation.Accueil;
import navigation.Competences;
import navigation.Formations;
import navigation.Missions;
import navigation.Personnel;

/**
 * La classe Header est la classe maîtresse de notre programme, elle
 * décompose la fenêtre en deux sous panels : l'en-tête avec la navigation
 * et le contenu avec la page souhaitée. Les changements de pages
 * s'effectuent ici car elle relie les actions sur la navigation avec le
 * chargement des pages.
 *
 */
public class Header extends JPanel implements MouseListener {
	
	private static final long	serialVersionUID	= 1L;
	private Image				logo;
	private Image				background;
	private Button				boutonAccueil;
	private Button				boutonCompetences;
	private Button				boutonPersonnel;
	private Button				boutonMissions;
	private Button				boutonFormations;
	private Button				currentBouton;
	private Accueil				panelAccueil;
	private Personnel			panelPersonnel;
	private Missions			panelMissions;
	private Formations		panelFormations;
	private Competences		panelCompetences;
	private String				etat;
	private JFrame				frame;
	private JPanel				menu;
	private JPanel				contenu;
	private JPanel				PageActuelle;	
	private Data 					data;
	
	/**
	 * @param frame La fenêtre de notre programme
	 */
	public Header(JFrame frame, Data data) {
		super();
		this.data = data;
		this.frame = frame;
		
		try {
			this.logo = ImageIO.read(getClass().getResource("/images/logo.png"));
			this.background = ImageIO.read(getClass().getResource("/images/background.png"));
		} catch (IOException e) {
			System.out.println("erreur chargement fond");
			e.printStackTrace();
		}
		
		this.boutonAccueil = new Button("/boutons/accueil.png");
		this.boutonCompetences = new Button("/boutons/competences.png");
		this.boutonPersonnel = new Button("/boutons/personnel.png");
		this.boutonMissions = new Button("/boutons/missions.png");
		this.boutonFormations  = new Button("/boutons/formation.png");
		this.boutonAccueil.addMouseListener(this);
		this.boutonCompetences.addMouseListener(this);
		this.boutonPersonnel.addMouseListener(this);
		this.boutonMissions.addMouseListener(this);
		this.boutonFormations.addMouseListener(this);
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		
		this.menu = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.menu.setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		this.menu.setSize(1280, 70);
		this.menu.setLocation(0, 0);
		this.menu.setOpaque(false);
		this.menu.add(this.boutonAccueil);
		this.menu.add(this.boutonCompetences);
		this.menu.add(this.boutonPersonnel);
		this.menu.add(this.boutonMissions);
		this.menu.add(this.boutonFormations);
		this.frame.add(this.menu);
		
		this.contenu = new JPanel();
		contenu.setSize(this.frame.getWidth(), this.frame.getHeight());
		contenu.setOpaque(false);
		contenu.setBorder(BorderFactory.createEmptyBorder(70, 0, 0, 0));
		contenu.setLayout(new CardLayout());
		this.frame.getContentPane().add(contenu);
		
		this.currentBouton = this.boutonAccueil;
		this.currentBouton.setBorder(new LineBorder(Color.BLACK));
		this.etat = "accueil";
		ChargementPage();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * Dessine l'arrière plan de l'en-tête et de la page ainsi que le logo
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D batch = (Graphics2D) g;
		batch.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		batch.setColor(new Color(0, 72, 136));
		batch.fillRect(0, 0, 1280, 70);
		g.drawImage(logo, 1215, 9, 50, 50, null);
		g.drawImage(background, 0, 70, 1280, 720, null);
	}
	
	/**
	 * On charge la page en fonction de l'état A chaque changement, on actualise
	 * la valeur de la page actuelle
	 */
	public void ChargementPage() {
		if (this.PageActuelle != null) {
			this.contenu.remove(this.PageActuelle);
		}
		
		switch (this.etat) {
		case "accueil":
			this.panelAccueil = new Accueil(data);
			this.PageActuelle = this.panelAccueil;
			this.contenu.add(this.panelAccueil);
			break;
		
		case "personnel":
			this.panelPersonnel = new Personnel(data);
			this.PageActuelle = this.panelPersonnel;
			this.contenu.add(this.panelPersonnel);
			break;
		
		case "missions":
			this.panelMissions = new Missions(data);
			this.PageActuelle = this.panelMissions;
			this.contenu.add(this.panelMissions);
			break;
		
		case "competences":
			this.panelCompetences = new Competences(data);
			this.PageActuelle = this.panelCompetences;
			this.contenu.add(this.panelCompetences);
			break;
			
		case "formations":
			this.panelFormations = new Formations(data);
			this.PageActuelle = this.panelFormations;
			this.contenu.add(this.panelFormations);
			break;
		
		default:
			System.out.println("Problème lors du chargement de la page");
			break;
		}
		
		this.frame.invalidate();
		this.frame.validate();
	}
	
	/**
	 * Le clic sur un des boutons de navigation entraine un changement de page
	 * 
	 * @param e
	 *            L'evenement MouseEvent
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof Button) {
			
			if(this.currentBouton != null){
				this.currentBouton.setBorder(BorderFactory.createEmptyBorder());
			}
			
			if (e.getSource().equals(this.boutonAccueil)) {
				this.etat = "accueil";
			} else if (e.getSource().equals(this.boutonCompetences)) {
				this.etat = "competences";
			} else if (e.getSource().equals(this.boutonPersonnel)) {
				this.etat = "personnel";
			} else if (e.getSource().equals(this.boutonMissions)) {
				this.etat = "missions";
			} else if (e.getSource().equals(this.boutonFormations)) {
				this.etat = "formations";
			}
			this.currentBouton = (Button) e.getSource();
			this.currentBouton.setBorder(new LineBorder(Color.BLACK));
			ChargementPage();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
}
