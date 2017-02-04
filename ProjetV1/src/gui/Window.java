package gui;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Window implements Runnable {

	private JFrame frame;
	private String title;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public Window(String name) {
		this.title = "Blokus";
		this.frame = new JFrame();
		this.frame.setName(name);
		this.frame.setSize(WIDTH, HEIGHT);
		this.frame.setTitle(this.title);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JButton accueil = new JButton("test");
	}

	public void run() {
		this.frame.setVisible(true);
		this.frame.setResizable(false);
	}
	
}
