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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import navigation.Accueil;
import navigation.Competences;
import navigation.Missions;
import navigation.Personnel;

public class Header extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	public static Cursor newCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	private Image logo;
	private Image background;
	public Button boutonAccueil;
	public Button boutonCompetences;
	public Button boutonPersonnel;
	public Button boutonMissions;
	public Accueil panelAccueil;
	public Personnel panelPersonnel;
	public Missions panelMissions;
	public Competences panelCompetences;
	String etat;
	JFrame frame;
	JPanel menu;
	JPanel contenu;
	JPanel PageActuelle;

	
	/**
	 * La classe Header est la classe maîtresse de notre programme
	 * Elle décompose la fenêtre en deux sous panels : l'en-tête avec la navigation et le contenu avec la page souhaitée
	 * Les changements de pages s'effectuent ici car elle relie les actions sur la navigation avec le chargement des pages
	 * @param frame La fenêtre de notre programme
	 */
	public Header(JFrame frame) {
		super();
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
		this.boutonAccueil.addMouseListener(this);
		this.boutonCompetences.addMouseListener(this);
		this.boutonPersonnel.addMouseListener(this);
		this.boutonMissions.addMouseListener(this);

		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));

		this.menu = new JPanel(new FlowLayout(FlowLayout.LEFT));
		menu.setSize(1280, 70);
		menu.setLocation(0, 0);
		menu.setOpaque(false);
		menu.add(boutonAccueil);
		menu.add(boutonCompetences);
		menu.add(boutonPersonnel);
		menu.add(boutonMissions);
		add(menu);

		this.contenu = new JPanel();
		contenu.setSize(this.frame.getWidth(), this.frame.getHeight());
		contenu.setOpaque(false);
		contenu.setBorder(BorderFactory.createEmptyBorder(70, 0, 0, 0));
		contenu.setLayout(new CardLayout());
		this.frame.getContentPane().add(contenu);

		this.etat = "accueil";
		ChargementPage();
	}

	/**
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
	 * On charge la page en fonction de l'état 
	 * A chaque changement, on actualise la valeur de la page actuelle
	 */
	public void ChargementPage() {
		if (this.PageActuelle != null) {
			this.contenu.remove(this.PageActuelle);
		}

		switch (this.etat) {
		case "accueil":
			this.panelAccueil = new Accueil();
			this.PageActuelle = this.panelAccueil;
			this.contenu.add(this.panelAccueil);
			break;

		case "personnel":
			this.panelPersonnel = new Personnel();
			this.PageActuelle = this.panelPersonnel;
			this.contenu.add(this.panelPersonnel);
			break;

		case "missions":
			this.panelMissions = new Missions();
			this.PageActuelle = this.panelMissions;
			this.contenu.add(this.panelMissions);
			break;

		case "competences":
			this.panelCompetences = new Competences();
			this.PageActuelle = this.panelCompetences;
			this.contenu.add(this.panelCompetences);
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
	 * @param e L'evenement MouseEvent
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof Button) {

			if (e.getSource().equals(this.boutonAccueil)) {
				this.etat = "accueil";
			} else if (e.getSource().equals(this.boutonCompetences)) {
				this.etat = "competences";
			} else if (e.getSource().equals(this.boutonPersonnel)) {
				this.etat = "personnel";
			} else if (e.getSource().equals(this.boutonMissions)) {
				this.etat = "missions";
			}
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
