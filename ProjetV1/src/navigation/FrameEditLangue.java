package navigation;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import gui.ProgramFrame;

public class FrameEditLangue {
	
	private JTextField TFlibelle;
	private JTextField TFpays;
	
	private JButton valider;
	
	String libelle;
	String pays;
	
	public FrameEditLangue(String p, String l) {
		this.pays = p;
		this.libelle = l;
	}
	
	
	public String displayGUI(){
		JFrame frame = new JFrame();
		frame.setSize(200, 300);
		frame.setResizable(false);
		frame.setLocation(150, 150);
		
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
            	ProgramFrame.getFrame().setEnabled(true);
            	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        }
	    });
		
        /*
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        */
        
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
        panel.setLayout(new GridBagLayout());
		
		JLabel labelPays = new JLabel("Pays");
		panel.add(labelPays);
		
		this.TFpays = new JTextField(this.pays);
		panel.add(this.TFpays);
		
		panel.add(Box.createRigidArea(new Dimension(5,0)));
		
		JLabel labelLibelle = new JLabel("Libellé");
		panel.add(labelLibelle);
		
		this.TFlibelle = new JTextField(this.libelle);
		panel.add(this.TFlibelle);
		
		this.valider = new JButton("Valider");
		this.valider.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		            ProgramFrame.getFrame().setEnabled(true);
		            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		
		panel.add(this.valider);
		
		frame.add(panel);
		
		frame.pack();
		frame.setVisible(true);
			
		return TFlibelle.getText();
		
	}

}
