package gui;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Table extends JTable {
	
	JScrollPane js;
	
	public Table(DefaultTableModel model, int x, int y, int w, int h) {
		setModel(model);
	   // setPreferredScrollableViewportSize(new Dimension(450,63));
	    setFillsViewportHeight(true);
	    this.js=new JScrollPane(this);
	    this.js.setVisible(true);
	    this.js.setBounds(x, y, w, h);
	}
	
	public JScrollPane getJScrollPane() {
		return this.js;
	}
	

}
