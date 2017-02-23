package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import navigation.Accueil;
import navigation.Personnel;

public class ProgramFrame {
	
		private JFrame frame;
	    private Header header;
		private String title;
		private Image iconImage;
		public static final int WIDTH = 1280;
		public static final int HEIGHT = 720;

	    public void displayGUI()
	    {
			this.title = "Skill Expert";
			this.frame = new JFrame();
			this.frame.setName(this.title);
			this.frame.setSize(WIDTH, HEIGHT);
			this.frame.setTitle(this.title);
			
			try {
				iconImage = ImageIO.read(getClass().getResourceAsStream("/images/icon.png"));
				this.frame.setIconImage(iconImage);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        this.header = new Header(this.frame);
	        frame.getContentPane().add(header);  
	        
	        frame.setLocationByPlatform(true);
			this.frame.setVisible(true);
			this.frame.setResizable(false);
  
	    }

}
