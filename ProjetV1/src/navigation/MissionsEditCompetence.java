package navigation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import gui.GenericTableModel;
import gui.ProgramFrame;
import models.Competence;
import models.CompetenceCode;
import models.CompetenceRequirement;

public class MissionsEditCompetence {
	
	public JTextField TFlibelle;
	public JTextField TFpays;
	public JTextField TFNombre;
	
	private JButton valider;
	private JButton annuler;
	
	private String code;
	private String libelle;
	private String nombre;
	
	public JTable jtable;
	public int row;
	public CompetenceRequirement compSelect;
	Missions missions;
	public JFrame frame;
	
	public MissionsEditCompetence(int row, JTable jtable, CompetenceRequirement compSelect, Missions m) {
		CompetenceCode cc = (CompetenceCode) jtable.getValueAt(row, 0);		
		this.code = cc.toString();
		this.libelle = (String) jtable.getValueAt(row, 1);	
		int nb = (int) jtable.getValueAt(row, 2);	
		this.nombre = Integer.toString(nb);
		this.row = row;
		this.jtable = jtable;
		this.compSelect = compSelect;
		this.missions = m;
	}
	
	
	public void displayGUI(){
		this.frame = new JFrame("Modification d'une compétence requise pour une mission");
		frame.setSize(500, 200);
		frame.setResizable(false);
		frame.setLocation(150, 20);
		
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
            	ProgramFrame.getFrame().setEnabled(true);
            	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        }
	    });
	
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        
        JPanel panelPays = new JPanel();
        panelPays.setLayout( new FlowLayout());
        
    	JLabel labelPays = new JLabel("Pays :");
    	panelPays.add(labelPays);
        
        TFpays = new JTextField(code);
        TFpays.setEnabled(false);
        TFpays.setPreferredSize(new Dimension(100, 25));
        panelPays.add(TFpays);
        
        JPanel panelLibelle = new JPanel();
        panelLibelle.setLayout( new FlowLayout());
        
    	JLabel labelLibelle = new JLabel("Libellé :");
    	panelLibelle.add(labelLibelle);
        
        TFlibelle = new JTextField(libelle);
        TFlibelle.setEnabled(false);
        TFlibelle.setPreferredSize(new Dimension(300, 25));
        panelLibelle.add(TFlibelle);
        
        JPanel panelNombre = new JPanel();
        panelNombre.setLayout( new FlowLayout());
        
    	JLabel labeNombre = new JLabel("Nombre d'employés :");
    	panelNombre.add(labeNombre);
        
        TFNombre = new JTextField(nombre);
        TFNombre.setPreferredSize(new Dimension(300, 25));
        panelNombre.add(TFNombre);
        
        JPanel panelBoutons = new JPanel();
        panelBoutons.setLayout( new FlowLayout());        
        
		this.valider = new JButton("Valider");
		this.valider.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					compSelect.setRequiredEmployees(Integer.parseInt(TFNombre.getText()));
					GenericTableModel<Competence>	mJTableCompetences = (GenericTableModel<Competence>) jtable.getModel();
					mJTableCompetences.setValueAt(TFNombre.getText(), row, 2);
					mJTableCompetences.fireTableDataChanged();
		            ProgramFrame.getFrame().setEnabled(true);
		            frame.dispose();
			}
		});
		
		
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
        panel.add(panelNombre);
        panel.add(panelBoutons);

        //Set up the content pane.
		panel.setOpaque(true);  //content panes must be opaque
        frame.setContentPane(panel);

        frame.setVisible(true);
	}

}
