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
	    private JPanel contentPane;
	    private HomePanel panel1;
	    private PersonnelPanel panel2;
	    private PersonnelPanel panel3;
	    private JPanel boutons;
	    
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
			this.frame.setLayout(new GridLayout(2,0));
			
			try {
				iconImage = ImageIO.read(getClass().getResourceAsStream("/images/icon.png"));
				this.frame.setIconImage(iconImage);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        JPanel contentPane = new JPanel();
	        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	        contentPane.setLayout(new CardLayout());
	        
	        this.boutons = new JPanel();
	        boutons.setLayout(new BoxLayout(boutons, BoxLayout.Y_AXIS));
	        boutons.setSize(new Dimension(frame.getWidth(), 70));

			JButton rouge =  new JButton("Rouge");
			JButton vert =  new JButton("Vert");
			JButton gris =  new JButton("Gris");

			boutons.add(rouge);
			boutons.add(vert);
			boutons.add(gris);
			
		    boutons.add(new Header());
			
			
	        panel1 = new HomePanel(contentPane, this);
	        System.out.println(this);
	        panel2 = new PersonnelPanel(contentPane, Color.GREEN.darker().darker(), this);
	        panel3 = new PersonnelPanel(contentPane, Color.DARK_GRAY, this);   

	        contentPane.add(panel1, "Panel 1"); 
	        contentPane.add(panel2, "Panel 2");
	        contentPane.add(panel3, "Panel 3");   
	              

	        frame.getContentPane().add(boutons, BorderLayout.PAGE_START);
	        frame.getContentPane().add(contentPane, BorderLayout.CENTER);       
	        frame.setLocationByPlatform(true);
			
			this.frame.setVisible(true);
			this.frame.setResizable(false);
			
	        
			rouge.addActionListener( new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
	                cardLayout.show(contentPane, "Panel 1");
	            }
	        });
	        
			vert.addActionListener( new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
	                cardLayout.show(contentPane, "Panel 2");
	            }
	        });
			gris.addActionListener( new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
	                cardLayout.show(contentPane, "Panel 3");
	            }
	        });
	      
	    }




}
