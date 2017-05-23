package navigation;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import data.DataException;
import gui.GenericTableModel;
import gui.JTables;
import gui.ProgramFrame;
import models.CompetenceRequirement;
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
	JTable empNonPoss;
	
	public MissionsAddEmploye(Mission missionEnCours, ArrayList<Employee> listeEmp, Missions pageMissions, GenericTableModel mJTablePoss) {
		this.missionEnCours = missionEnCours;
		this.listeEmp = listeEmp;
		this.pageMissions = pageMissions;
		this.mJTablePoss = mJTablePoss;
	}
	
	
	public void displayGUI(){
		this.frame = new JFrame("Affectation d'employés");
		frame.setSize(500, 550);
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
        
        JPanel panelJTable = new JPanel();
        panelJTable.setBackground(Color.WHITE);
        panelJTable.setLayout( new FlowLayout());
       
		Recommendation recommandation  = new Recommendation(missionEnCours, listeEmp);
		recommandation.newRecommendation();

		ArrayList<Employee> listEmpNonPoss = recommandation.getEmpRec();
		this.empNonPoss = JTables.Recommendation(recommandation);
		
		empNonPoss.setFillsViewportHeight(true);
		JScrollPane js = new JScrollPane(empNonPoss);
		js.setVisible(true);
		js.setBounds(10, 80, 300, 520);
		panelJTable.add(js);

        JPanel panelBoutons = new JPanel();
        panelBoutons.setBackground(Color.WHITE);
        panelBoutons.setLayout( new FlowLayout());        
        
		this.ajouter = new JButton("Ajouter");
		this.ajouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowIndex = empNonPoss.getSelectedRow();
				Employee emp = (Employee) empNonPoss.getValueAt(rowIndex, 3);
			
				listeEmp.remove(emp);
				DefaultTableModel tm = (DefaultTableModel) JTables.Recommendation(recommandation).getModel();
				tm.fireTableDataChanged();

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
