package gui;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

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
