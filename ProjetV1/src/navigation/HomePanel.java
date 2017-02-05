package navigation;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import gui.ProgramFrame;

public class HomePanel extends JPanel  {
	   private JButton jcomp4;
	    private JPanel contentPane;
	    private JPanel choiceBox;

	    public HomePanel(JPanel panel, ProgramFrame cle) 
	    {
	        contentPane = panel;
	        setOpaque(true);
	        setBackground(Color.RED.darker().darker());
	    }

	    @Override
	    public Dimension getPreferredSize()
	    {
	        return (new Dimension(500, 500));
	    }
}
