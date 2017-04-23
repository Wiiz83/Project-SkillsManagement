package gui;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import data.Data;

public class ProgramFrame {
	private static JFrame		frame;
	private Header				header;
	private Image				iconImage;
	public static final int	WIDTH	= 1280;
	public static final int	HEIGHT	= 720;

	public void displayGUI(Data data) {
		ProgramFrame.setFrame(new JFrame());
		ProgramFrame.getFrame().setSize(WIDTH, HEIGHT);
		ProgramFrame.getFrame().setTitle("Skill Expert");
		ProgramFrame.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ProgramFrame.getFrame().setResizable(false);
		ProgramFrame.getFrame().setLocation(5, 5);
		
		try {
			iconImage = ImageIO.read(getClass().getResourceAsStream("/images/icon.png"));
			ProgramFrame.getFrame().setIconImage(iconImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.header = new Header(ProgramFrame.getFrame(), data);
		ProgramFrame.getFrame().getContentPane().add(header);
		ProgramFrame.getFrame().setVisible(true);
	}

	/**
	 * @return the frame
	 */
	public static JFrame getFrame() {
		return frame;
	}

	/**
	 * @param frame the frame to set
	 */
	private static void setFrame(JFrame frame) {
		ProgramFrame.frame = frame;
	}
}
