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

public class PersonnelPanel extends JPanel {
	
    private JButton jcomp1;
    private JPanel contentPane;
    private Color backgroundColour;
    private JPanel choiceBox;

    public PersonnelPanel(JPanel panel, Color c, ProgramFrame cle) 
    {   
        contentPane = panel;
        backgroundColour = c;

        setOpaque(true);
        setBackground(backgroundColour);

    }

    @Override
    public Dimension getPreferredSize()
    {
        return (new Dimension(500, 500));
    }

}
