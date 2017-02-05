package gui;

import java.awt.Color;
import java.awt.Cursor;
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

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import org.w3c.dom.css.RGBColor;

public class GraphicsPanel extends JComponent implements MouseMotionListener, MouseListener, MouseWheelListener, ActionListener{
	private static final long		serialVersionUID	= 1L;
	private static final boolean	DEBUG				= false;
	private static final int POS_X	= 488;

	// Prend le curseur prédéfini
	public static Cursor newCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);

	// L'image de fond
	private Image background;
	
	// Le nombre de frame
	private long framesRendered;
	
	// Temps passÃ© en milliseconde
	private double timeElapsed;
	
	// La moyenne FPS
	private int	approxFPS;
	
	// Le bouton d'accueil
	private BlokusButton buttonHomePage;

	/**
	 * Constructeur de GraphicsPanel
	 */
	public GraphicsPanel() {
		super();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		try {
			this.background = ImageIO.read(getClass().getResource("/images/background.png"));
			this.buttonHomePage = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "accueil.png"));

		}
		catch (IOException e) {
			System.out.println("erreur chargement fond");
			e.printStackTrace();
		}
		this.framesRendered = 0;
		this.timeElapsed = 0.0;
		this.approxFPS = 0;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D batch = (Graphics2D) g;
		this.clear(batch);
		batch.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
		Navigation.getPage().draw(batch);
		
		batch.setColor(new Color(0, 72, 136));
		batch.fillRect(0, 0, 1280, 75);
		
		this.buttonHomePage.draw(batch);
		
		this.framesRendered++;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof BlokusButton) {
			if (e.getSource().equals(this.buttonHomePage)) {
				Navigation.NavigateTo(Navigation.homePage);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Mouse.setLastMouseButton(e.getButton());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Mouse.setLastMouseButton(e.getButton());
		Mouse.setMouseState(Mouse.MOUSE_PRESSED);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Mouse.setLastMouseButton(e.getButton());
		Mouse.setMouseState(Mouse.MOUSE_RELEASED);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Mouse.setMouseState(Mouse.MOUSE_PRESSED);
		Mouse.setMousePosition(new Vector2(e.getX(), e.getY()));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Mouse.setMousePosition(new Vector2(e.getX(), e.getY()));
	}

	protected void clear(Graphics2D batch) {
		batch.setPaint(Color.WHITE);
		batch.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		Mouse.setLastScrollClicks(e.getWheelRotation());
	}

	/**
	 * ProcÃ©dure d'update
	 * 
	 * @param elapsedTime
	 */
	public void update(float elapsedTime) {
		this.timeElapsed += elapsedTime / 1000f;
		this.approxFPS = Math.round(1000 / elapsedTime);
		Navigation.getPage().update(elapsedTime);
		if (this.getCursor() != newCursor) {
			this.setCursor(newCursor);
		}
		this.repaint();
	}
}
