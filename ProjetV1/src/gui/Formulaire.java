package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Classe pour une page de type formulaire
 */
public class Formulaire extends JPanel {

	private static final long serialVersionUID = 1L;
	protected  ArrayList<JComponent> composantsEdition			= new ArrayList<JComponent>();
    protected ArrayList<JComponent> composantsConsultation 	= new ArrayList<JComponent>();
    protected String				mode;
    protected Button				boutonNouveau;
    protected Button				boutonModifier;
    protected Button				boutonSupprimer;
    protected Button				boutonEnregistrer;
    protected Button				boutonAnnuler;
    
    /**
     * Construction du formulaire avec les boutons
     */
    public Formulaire(){
		
		this.boutonNouveau = new Button("/boutons/nouveau.png");
		this.boutonNouveau.setBounds(330, 560);

		this.boutonModifier = new Button("/boutons/modifier.png");
		this.boutonModifier.setBounds(510, 560);
	
		this.boutonSupprimer = new Button("/boutons/supprimer.png");
		this.boutonSupprimer.setBounds(690, 560);
		
		this.boutonEnregistrer = new Button("/boutons/enregistrer.png");
		this.boutonEnregistrer.setBounds(885, 560);
		
		this.boutonAnnuler = new Button("/boutons/annuler.png");
		this.boutonAnnuler.setBounds(1100, 560);
		
		add(this.boutonNouveau);
		add(this.boutonModifier);
		add(this.boutonSupprimer);
		add(this.boutonEnregistrer);
		add(this.boutonAnnuler);
    }
    
	/**
	 * Les éléments du formulaire ne sont pas modifiables
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
	
	/**
	 * Les éléments du formulaire sont modifiables
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
    
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D batch = (Graphics2D) g;
		batch.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		batch.setColor(Color.WHITE);
		batch.fillRect(330, 40, 930, 510);
	}

}
