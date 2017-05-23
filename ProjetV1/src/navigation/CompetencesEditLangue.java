package navigation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import gui.ProgramFrame;
import models.Competence;

/**
 * Fênetre popup pour éditer les libellés des compétences
 */
public class CompetencesEditLangue {
	
	public JTextField TFlibelle;
	public JTextField TFpays;
	
	private JButton valider;
	private JButton annuler;
	
	private String libelle;
	private String pays;
	
	public JTable jtable;
	public int row;
	public Competence compSelect;
	Competences competences;
	public JFrame frame;
	
	/**
	 * @param row la ligne de la jtable séléctionnée
	 * @param jtable la jtable concernée 
	 * @param compSelect la compétence séléctionnée
	 * @param c la page Compétences
	 */
	public CompetencesEditLangue(int row, JTable jtable, Competence compSelect, Competences c) {
		this.pays = (String) jtable.getValueAt(row, 0);		
		this.libelle = (String) jtable.getValueAt(row, 1);	
		this.row = row;
		this.jtable = jtable;
		this.compSelect = compSelect;
		this.competences = c;
	}
	
	
	/**
	 * Affichage de la fênetre
	 */
	public void displayGUI(){
		this.frame = new JFrame("Modification d'un libellé de compétence");
		frame.setSize(400, 170);
		frame.setResizable(false);
		frame.setLocation(300, 100);

		try {
			Image iconImage = ImageIO.read(getClass().getResourceAsStream("/images/icon.png"));
			frame.setIconImage(iconImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
            	ProgramFrame.getFrame().setEnabled(true);
            	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        }
	    });
	
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        
        JPanel panelPays = new JPanel();
        panelPays.setBackground(Color.WHITE);
        panelPays.setLayout( new FlowLayout());
        
    	JLabel labelPays = new JLabel("Pays :");
    	panelPays.add(labelPays);
        
        TFpays = new JTextField(pays);
        TFpays.setEnabled(false);
        TFpays.setPreferredSize(new Dimension(100, 25));
        panelPays.add(TFpays);
        
        
        JPanel panelLibelle = new JPanel();
        panelLibelle.setBackground(Color.WHITE);
        panelLibelle.setLayout( new FlowLayout());
        
    	JLabel labelLibelle = new JLabel("Libellé :");
    	panelLibelle.add(labelLibelle);
        
        TFlibelle = new JTextField(libelle);
        TFlibelle.setPreferredSize(new Dimension(300, 25));
        panelLibelle.add(TFlibelle);
        
        JPanel panelBoutons = new JPanel();
        panelBoutons.setBackground(Color.WHITE);
        panelBoutons.setLayout( new FlowLayout());        
        
        // On valide la modification : on enregistre, on actualise les champs et on revient à la fênetre principale
		this.valider = new JButton("Valider");
		this.valider.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				compSelect.getNames().set(row, TFlibelle.getText());
				competences.ActualisationChamps();
	            ProgramFrame.getFrame().setEnabled(true);
	            frame.dispose();
			}
		});
		
		
		// On annule tout et on revient à la fênetre principale
		this.annuler = new JButton("Annuler");
		this.annuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		            ProgramFrame.getFrame().setEnabled(true);
		            frame.dispose();
			}
		});
		
		panelBoutons.add(this.annuler);    
		panelBoutons.add(this.valider);       

        panel.add(panelPays);
        panel.add(panelLibelle);
        panel.add(panelBoutons);

        //Set up the content pane.
		panel.setOpaque(true);
        frame.setContentPane(panel);

        frame.setVisible(true);
	}

}
