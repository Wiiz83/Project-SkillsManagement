package gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class BufferedHelper {
	
	/**
	 * Obtient les configurations de la librairie de dessin
	 * @return Les configurations
	 */
	public static GraphicsConfiguration getGraphicsConfiguration() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	}

	/**
	 * Génére le mask d'une image donnée
	 * @param imgSource L'image donnée
	 * @param color La couleur du mask
	 * @param alpha La transparence du mask
	 * @return Le mask de l'image
	 */
	public static BufferedImage generateMask(BufferedImage imgSource, Color color, float alpha) {
		int imgWidth = imgSource.getWidth();
		int imgHeight = imgSource.getHeight();

		BufferedImage image = getGraphicsConfiguration().createCompatibleImage(imgWidth, imgHeight,Transparency.TRANSLUCENT);
		image.coerceData(true);

		Graphics2D g2 = image.createGraphics();

		g2.drawImage(imgSource, 0, 0, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
		g2.setColor(color);

		g2.fillRect(0, 0, imgSource.getWidth(), imgSource.getHeight());
		g2.dispose();

		return image;
	}


}
