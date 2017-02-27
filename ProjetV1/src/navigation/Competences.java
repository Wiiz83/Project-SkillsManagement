package navigation;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Competences extends JPanel {
	private static final long serialVersionUID = 1L;
	JTextField nom;
	
	public Competences () 
    {   
	    setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));

    }
}
