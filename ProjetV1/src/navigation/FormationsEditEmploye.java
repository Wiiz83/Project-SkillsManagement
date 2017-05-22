package navigation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import gui.GenericTableModel;
import gui.ProgramFrame;

public class FormationsEditEmploye implements ActionListener {

	private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
	private static final String ADD_BUTTON_LABEL = "<< Add";
	private static final String REMOVE_BUTTON_LABEL = "Remove >>";

	private static final String PRESENTS	= "Élements présents";
	private static final String RESTANTS	= "Élements restants";
	
	private JLabel ElementPossLabel;
	private JLabel ElementNonPossLabel;
	JTable	ElementPoss;
	JTable	ElementNonPoss;
	
	GenericTableModel		mJTablePoss;
	GenericTableModel		mJTableNonPoss;
	
	private JButton addButton;
	private JButton removeButton;

	public FormationsEditEmploye(JTable Poss, JTable nonPoss, GenericTableModel PossModel,GenericTableModel nonPossModel) {
		this.ElementPoss = Poss;
		this.ElementNonPoss = nonPoss;
		this.ElementNonPoss.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.ElementNonPoss.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.mJTableNonPoss = nonPossModel;
		this.mJTablePoss = PossModel;
	}
	
	public void displayGUI(){
		
		JFrame frame = new JFrame();
		frame.setSize(800, 400);
		frame.setTitle("Modification des employés d'une formation");
		frame.setResizable(false);
		frame.setLocation(5, 5);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
            	ProgramFrame.getFrame().setEnabled(true);
            	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        }
	    });
		
        JPanel content = new JPanel();
		content.setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		content.setLayout(new GridBagLayout());
		
		ElementPossLabel = new JLabel(PRESENTS);
		ElementNonPossLabel = new JLabel(RESTANTS);

		content.add(ElementPossLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,EMPTY_INSETS, 0, 0));
		content.add(new JScrollPane(ElementPoss), new GridBagConstraints(0, 1, 1, 5, .5, 1, GridBagConstraints.CENTER,GridBagConstraints.BOTH, EMPTY_INSETS, 0, 0));

		addButton = new JButton(ADD_BUTTON_LABEL);
		content.add(addButton, new GridBagConstraints(1, 2, 1, 2, 0, .25, GridBagConstraints.CENTER, GridBagConstraints.NONE,EMPTY_INSETS, 0, 0));
		addButton.addActionListener(this);

		removeButton = new JButton(REMOVE_BUTTON_LABEL);
		content.add(removeButton, new GridBagConstraints(1, 4, 1, 2, 0, .25, GridBagConstraints.CENTER, GridBagConstraints.NONE,new Insets(0, 5, 0, 5), 0, 0));
		removeButton.addActionListener(this);

		content.add(ElementNonPossLabel, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,EMPTY_INSETS, 0, 0));
		content.add(new JScrollPane(ElementNonPoss), new GridBagConstraints(2, 1, 1, 5, .5, 1.0, GridBagConstraints.CENTER,GridBagConstraints.BOTH, EMPTY_INSETS, 0, 0));
		
		frame.getContentPane().add(content);
		frame.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(addButton)) {
			Object o = mJTableNonPoss.getRowObject(ElementNonPoss.getSelectedRow());
			mJTablePoss.addRowObject(o);
			mJTableNonPoss.deleteRowObject(o);		
			mJTablePoss.fireTableDataChanged();
			mJTableNonPoss.fireTableDataChanged();
		}

		if (e.getSource().equals(removeButton)) {
			Object o = mJTablePoss.getRowObject(ElementPoss.getSelectedRow());
			mJTableNonPoss.addRowObject(o);
			mJTablePoss.deleteRowObject(o);		
			mJTableNonPoss.fireTableDataChanged();
			mJTablePoss.fireTableDataChanged();
		}
	}
}
