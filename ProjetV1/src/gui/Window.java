/*
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 	FICHIER A SUPPRIMER APRES RECUPERATION
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
package gui;
 

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Window {
	private static JFrame frame;
	private Header header;
	private String title;
	private Image iconImage;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
    JPanel cards; //a panel that uses CardLayout

	public Window(String name) {
		
/*
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

		
		// CONFIGURE LE HEADER
		JPanel panelHeader = new JPanel();
		panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.Y_AXIS));
		panelHeader.setSize(new Dimension(this.frame.getWidth(), 70));
		panelHeader.add(new Header());
		
		// CONFIGURE LE CONTENU
		JPanel panelContent = new JPanel();
		panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.Y_AXIS));
		panelContent.setSize(new Dimension(this.frame.getWidth(), 650));
		panelContent.add(new HomePage());
		
		this.frame.add(panelHeader);
		this.frame.add(panelContent);
	
		
		
		JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new CardLayout());
        
        
        
        HomePanel panel1 = new HomePanel(contentPane);
        
        panel2 = new MyPanel2(contentPane);
        contentPane.add(panel1, "Panel 1"); 
        contentPane.add(panel2, "Panel 2");
        frame.setContentPane(contentPane);
        frame.pack();   
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        
        
		
		JPanel headerPanel = new JPanel();
		JButton accueil =  new JButton("Accueil");
		JButton personnel =  new JButton("Personnel");
		headerPanel.add(accueil);
		headerPanel.add(personnel);
		
		
        JPanel card1 = new JPanel();
        card1.add(new JButton("Button 1"));

        JPanel card2 = new JPanel();
        card2.add(new JTextField("TextField", 20));
        
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1);
        cards.add(card2);
        
        frame.getContentPane().add(headerPanel, BorderLayout.PAGE_START);
        frame.getContentPane().add(cards, BorderLayout.CENTER);
        
		this.frame.setVisible(true);
		this.frame.setResizable(false);
		

*/
		
	}



	
}
