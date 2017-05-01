package gui;

import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class RechercheJTable implements DocumentListener {
	
	private String indication;
	private TableRowSorter<TableModel> rowSorter;
	private HintTextField recherche;
	
	public RechercheJTable(HintTextField htf ,String i, TableRowSorter<TableModel> r){
		this.indication = i;
		this.rowSorter = r;
		this.recherche = htf;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
        String text = recherche.getText();

        if (text.equals(indication)) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
        String text = recherche.getText();

        if (text.equals(indication)) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
	}

}
