package navigation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import csv.CSVToTable;
import csv.InvalidCSVException;
import csv.InvalidDataException;
import gui.Button;


public class Personnel extends JPanel implements MouseListener{
	
	Button boutonNouveau;
	Button boutonModifier;
	Button boutonSupprimer;
	Button boutonEnregistrer;
	Button boutonAnnuler;
	
	private static final long serialVersionUID = 1L;

	public Personnel()  {   
	    setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		

		JTable table = new JTable();		
		try {
			table = CSVToTable.Employes();
			table.setFillsViewportHeight(true);
			JScrollPane js = new JScrollPane(table);
		    js.setVisible(true);
		    js.setBounds(10, 10, 300, 600);
			add(js);
		    
		} catch (NumberFormatException | IOException | InvalidCSVException | InvalidDataException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.boutonNouveau = new Button("/boutons/nouveau.png");		
		this.boutonNouveau.setBounds(330, 560);
		this.boutonNouveau.addMouseListener(this);
		
		this.boutonModifier = new Button("/boutons/modifier.png");		
		this.boutonModifier.setBounds(510, 560);
		this.boutonModifier.addMouseListener(this);
		
		this.boutonSupprimer = new Button("/boutons/supprimer.png");		
		this.boutonSupprimer.setBounds(690, 560);
		this.boutonSupprimer.addMouseListener(this);
		
		this.boutonEnregistrer = new Button("/boutons/enregistrer.png");		
	    this.boutonEnregistrer.setBounds(885, 560);
		this.boutonEnregistrer.addMouseListener(this);
		
		this.boutonAnnuler = new Button("/boutons/annuler.png");		
		this.boutonAnnuler.setBounds(1100, 560);
		this.boutonAnnuler.addMouseListener(this);
		
		add(this.boutonNouveau);
		add(this.boutonModifier);
		add(this.boutonSupprimer);
		add(this.boutonEnregistrer);
		add(this.boutonAnnuler);
		
    }
	
	@Override
	public void paintComponent(Graphics g) {		
		super.paintComponent(g);
		Graphics2D batch = (Graphics2D) g;
		
		
		batch.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		batch.setColor(Color.WHITE);
		batch.fillRect(330, 13, 930, 535);
		
		
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof Button) {
			if (e.getSource().equals(this.boutonNouveau)) {
				this.repaint();
			}

		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
