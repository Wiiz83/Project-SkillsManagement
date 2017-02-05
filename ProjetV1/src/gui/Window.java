package gui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

import gui.Navigation;
import gui.Page;

public class Window implements Runnable , ActionListener 
{
	private static final int UPDATE_DELAY = 15;
	
	/**
	 * La fenêtre
	 */
	private JFrame frame;
	
	/**
	 * Le dessin
	 */
	private GraphicsPanel gameGraphics;

	/**
	 * Le timer
	 */
	private Timer timer;

	/**
	 * 
	 */
	private long startTime;

	private String title;
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	/**
	 * Constructeur d'une Window
	 * 
	 * @param name le nom de la fenêtre
	 */
	public Window(String name) {
		this.title = "Blokus";
		this.frame = new JFrame();
		this.frame.setName(name);
		this.frame.setSize(WIDTH, HEIGHT);
		this.frame.setTitle(this.title);
		Image iconImage;
		try {
			iconImage = ImageIO.read(getClass().getResourceAsStream(Page.PATH_RESOURCES_IMAGES + "icon.png"));
			this.frame.setIconImage(iconImage);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameGraphics = new GraphicsPanel();
		this.frame.getContentPane().add(this.gameGraphics, BorderLayout.CENTER);
		
		Navigation.NavigateTo(Navigation.homePage);
		this.timer = new Timer(UPDATE_DELAY, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==this.timer) {
			float elapsedTime = (System.nanoTime() - this.startTime)/1000000f;
			this.startTime = System.nanoTime();
			
			this.gameGraphics.update(elapsedTime);
		}
	}

	@Override
	public void run() {
		this.frame.setVisible(true);
		this.frame.setResizable(false);
		this.frame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				//Keyboard.setLastKeyTyped(e.getKeyCode());
			}

			@Override
			public void keyTyped(KeyEvent e) {}
		});
		this.startTime = System.nanoTime();
		this.timer.start();
	}
	
}
