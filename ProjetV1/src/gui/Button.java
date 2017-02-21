package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

import gui.BufferedHelper;
import program.Program;

public class Button extends JComponent implements MouseListener {

	private static final long serialVersionUID = 1L;
	BufferedImage backgroundImage;
	BufferedImage backgroundImageHover;
	BufferedImage backgroundImageDisable;
	String etat;
	int width;
	int height;

	public Button(String url)
	{
		super();
		
	    try {
			this.backgroundImage = ImageIO.read(new File(getClass().getResource(url).toURI()));
			this.backgroundImageHover = BufferedHelper.generateMask(this.backgroundImage, Color.BLACK, 0.5f);
			this.backgroundImageDisable = BufferedHelper.generateMask(this.backgroundImage, Color.GRAY, 0.8f);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    
		enableInputMethods(true);   
		addMouseListener(this);
		this.width = backgroundImage.getWidth();
		this.height = backgroundImage.getHeight();
		System.out.println(this.width);
	}

	@Override
	public Dimension getPreferredSize() {
        if (backgroundImage == null) {
            return new Dimension(100,100);
       } else {
          return new Dimension(this.width, this.height);
      }
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if(this.backgroundImage != null)
		{
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.drawImage(this.backgroundImage,0,0,this.width,this.height,null);
			
			if(this.etat == "survol")
			{
				g2d.drawImage(this.backgroundImageHover,0,0,this.width,this.height,null);
			}
			else if(this.etat == "grisé")
			{
				g2d.drawImage(this.backgroundImageDisable,0,0,this.width,this.height,null);
			}
			
			g2d.dispose();
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.etat = "survol";
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.etat = "normal";
		repaint();		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}