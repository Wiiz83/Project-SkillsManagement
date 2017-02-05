package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import navigation.HomePanel;
import navigation.PersonnelPanel;

public class ProgramFrame {
	
		private JFrame frame;
	    public static JPanel contentPane;
	    private HomePanel panel1;
	    private PersonnelPanel panel2;
	    private PersonnelPanel panel3;
	    private Header header;
	    
		private String title;
		private Image iconImage;
		public static final int WIDTH = 1280;
		public static final int HEIGHT = 720;

	    public void displayGUI(String name)
	    {
			this.title = "Blokus";
			this.frame = new JFrame();
			this.frame.setName(name);
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


	        this.header = new Header();
	        header.setSize(new Dimension(frame.getWidth(), 70));
	        
	        JPanel contentPane = new JPanel();
	        contentPane.setBorder(BorderFactory.createEmptyBorder(70,0,0,0)); 
	        contentPane.setLayout(new CardLayout());
	        
	        panel1 = new HomePanel();
	        panel1.setLocation(0, 70);
	        panel2 = new PersonnelPanel(contentPane, Color.GREEN, this);
	        panel3 = new PersonnelPanel(contentPane, Color.DARK_GRAY, this);   

	        contentPane.add(panel1, "Panel 1"); 
	        contentPane.add(panel2, "Panel 2");
	        contentPane.add(panel3, "Panel 3");   

	        frame.getContentPane().add(header);
	        frame.getContentPane().add(contentPane);       
	        frame.setLocationByPlatform(true);
			
			this.frame.setVisible(true);
			this.frame.setResizable(false);
  
	    }
}
