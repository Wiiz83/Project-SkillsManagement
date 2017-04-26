package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Formulaire extends JPanel {
	
	protected  ArrayList<JComponent> composantsEdition			= new ArrayList<JComponent>();
    protected ArrayList<JComponent> composantsConsultation 	= new ArrayList<JComponent>();
    protected String	mode;
	
	 /*
	  *   Mode Consultation : Les éléments du formulaire ne sont pas modifiables
	  */
	public void ChargementConsultation() {
		 for(JComponent component: composantsEdition){
			if(component instanceof Button){
				component.setVisible(false);
			}else{
				component.setEnabled(false); 
			}
		 }
		 for(JComponent component: composantsConsultation){
				if(component instanceof Button){
					component.setVisible(true);
				}else{
					component.setEnabled(true); 
				}
		 }
		this.mode = "consultation";
	}
	
	/*
	 * Mode Modification : Les éléments du formulaire sont modifiables
	 */
	public void ChargementModification() {
		 for(JComponent component: composantsEdition){
				if(component instanceof Button){
					component.setVisible(true);
				}else{
					component.setEnabled(true); 
				}
		 }
		 for(JComponent component: composantsConsultation){
				if(component instanceof Button){
					component.setVisible(false);
				}else{
					component.setEnabled(false); 
				}
		 }
		this.mode = "modification";
	}
    
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D batch = (Graphics2D) g;
		batch.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		batch.setColor(Color.WHITE);
		batch.fillRect(330, 40, 930, 510);
	}

}
