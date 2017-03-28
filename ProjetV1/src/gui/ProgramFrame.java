package gui;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import data.Data;

public class ProgramFrame {
	private JFrame			frame;
	private Header			header;
	private Image			iconImage;
	public static final int	WIDTH	= 1280;
	public static final int	HEIGHT	= 720;
	
	/**
	 * Création d'une fenêtre et affichage à l'écran de celle-ci
	 */
	public void displayGUI(Data data) {
		this.frame = new JFrame();
		this.frame.setSize(WIDTH, HEIGHT);
		this.frame.setTitle("Skill Expert");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.setLocation(5, 5);
		
		try {
			iconImage = ImageIO.read(getClass().getResourceAsStream("/images/icon.png"));
			this.frame.setIconImage(iconImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.header = new Header(this.frame, data);
		frame.getContentPane().add(header);
		this.frame.setVisible(true);
	}
}
