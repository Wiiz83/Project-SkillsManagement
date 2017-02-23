package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
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
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.w3c.dom.css.RGBColor;

import navigation.Accueil;
import navigation.Navigation;
import navigation.Personnel;
import program.Program;

public class Header extends JPanel implements MouseListener {
	private static final long		serialVersionUID	= 1L;
	private static final boolean	DEBUG				= false;

	// Prend le curseur pr�d�fini
	public static Cursor newCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	
	private Image logo;
	private Image background;

	public static Button boutonAccueil;
	public static Button boutonCompetences;
	public static Button boutonPersonnel;
	public static Button boutonMissions;
	
	public Accueil panelAccueil;
	public Personnel panelPersonnel;
	
	String etat;
	JFrame frame;
	JPanel menu;
	JPanel contenu;


	public Header(JFrame frame) {
		super();
		this.frame = frame;

		try {
			this.logo = ImageIO.read(getClass().getResource("/images/logo.png"));
			this.background = ImageIO.read(getClass().getResource("/images/background.png"));
		}
		catch (IOException e) {
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
        contenu.setBorder(BorderFactory.createEmptyBorder(70,0,0,0)); 
        contenu.setLayout(new CardLayout());
        this.frame.getContentPane().add(contenu); 

        this.panelAccueil = new Accueil();
        this.contenu.add(this.panelAccueil, "Panel 1"); 
        
        this.panelPersonnel = new Personnel();
        this.contenu.add(this.panelPersonnel, "Panel 2"); 
        
        this.etat = "accueil";
		
	}

	@Override
	public void paintComponent(Graphics g) {		
		super.paintComponent(g);
		Graphics2D batch = (Graphics2D) g;
		
        this.panelAccueil.setVisible(false);
        this.panelPersonnel.setVisible(false);
		
		if(this.etat == "accueil")
		{
	        this.panelAccueil.setVisible(true);
		}
		if(this.etat == "personnel")
		{
	        this.panelPersonnel.setVisible(true);
		}
		
		batch.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		batch.setColor(new Color(0, 72, 136));
		batch.fillRect(0, 0, 1280, 70);
		g.drawImage(logo, 1215, 9, 50, 50, null);
		g.drawImage(background, 0, 70, 1280, 720, null);
		


	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof Button) {
			if (e.getSource().equals(this.boutonAccueil)) {
				this.etat = "accueil";
				this.repaint();
			}
			else if (e.getSource().equals(this.boutonCompetences)) {
			
			}
			else if (e.getSource().equals(this.boutonPersonnel)) {
				this.etat = "personnel";
				this.repaint();
				
			}
			else if (e.getSource().equals(this.boutonMissions)) {
				
			}
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