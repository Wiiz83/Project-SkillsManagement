package navigation;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import data.DataException;
import gui.GenericTableModel;
import gui.JTables;
import gui.ProgramFrame;
import models.Employee;
import models.Mission;
import models.Recommendation;

public class MissionsAddEmploye {
	
	private JButton ajouter;
	private JFrame frame;
	
	Mission missionEnCours;
	ArrayList<Employee> listeEmp;
	Missions pageMissions;
	GenericTableModel<Employee>	mJTablePoss;
	
	public MissionsAddEmploye(Mission missionEnCours, ArrayList<Employee> listeEmp, Missions pageMissions, GenericTableModel mJTablePoss) {
		this.missionEnCours = missionEnCours;
		this.listeEmp = listeEmp;
		this.pageMissions = pageMissions;
		this.mJTablePoss = mJTablePoss;
	}
	
	
	public void displayGUI(){
		this.frame = new JFrame("Modification d'une compétence requise pour une mission");
		frame.setSize(500, 550);
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
        
        JPanel panelJTable = new JPanel();
        panelJTable.setLayout( new FlowLayout());
       
		Recommendation recommandation  = new Recommendation(missionEnCours, listeEmp);
		try {
			recommandation.setRecommendations();
		} catch (DataException e1) {
			e1.printStackTrace();
		}

		ArrayList<Employee> listEmpNonPoss = recommandation.getEmpRec();
		JTable empNonPoss = JTables.Recommendation(recommandation);
		empNonPoss.setFillsViewportHeight(true);
		JScrollPane js = new JScrollPane(empNonPoss);
		js.setVisible(true);
		js.setBounds(10, 80, 300, 520);
		panelJTable.add(js);

        JPanel panelBoutons = new JPanel();
        panelBoutons.setLayout( new FlowLayout());        
        
		this.ajouter = new JButton("Ajouter");
		this.ajouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowIndex = empNonPoss.getSelectedRow();
				Employee emp = (Employee) empNonPoss.getValueAt(rowIndex, 3);
				missionEnCours.affectEmployee(emp);
				mJTablePoss.fireTableDataChanged();
				int modelRow = empNonPoss.convertRowIndexToModel(rowIndex);
				DefaultTableModel model = (DefaultTableModel) empNonPoss.getModel();
				model.removeRow( modelRow );
			}
		});
		
		panelBoutons.add(this.ajouter);       

        panel.add(panelJTable);
        panel.add(panelBoutons);

		panel.setOpaque(true); 
        frame.setContentPane(panel);

        frame.setVisible(true);
	}

}
