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
import javax.swing.SpringLayout;

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
        
        String[] labels = {"Pays: ", "Libellé:"};
        int numPairs = labels.length;

        //Create and populate the panel.
        JPanel p = new JPanel(new SpringLayout());
        
        
        
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            p.add(l);
            JTextField textField = new JTextField(10);
            l.setLabelFor(textField);
            p.add(textField);
        }
		
		this.valider = new JButton("Valider");
		this.valider.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		            ProgramFrame.getFrame().setEnabled(true);
		            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		panel.add(this.valider);        

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(p,
                                        numPairs, 2, //rows, cols
                                        6, 6,        //initX, initY
                                        6, 6);       //xPad, yPad

        //Create and set up the window.
        JFrame frame = new JFrame("SpringForm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        p.setOpaque(true);  //content panes must be opaque
        frame.setContentPane(p);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

	}

}
