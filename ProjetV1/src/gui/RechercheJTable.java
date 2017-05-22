package gui;

import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * HintTextField de recherche dans JTable 
 */
public class RechercheJTable implements DocumentListener {
	
	private String indication;
	private TableRowSorter<TableModel> rowSorter;
	private HintTextField recherche;
	
	/**
	 * @param htf le HintTextField 
	 * @param i le texte d'indication du HintTextField
	 * @param r le filtre de la JTable
	 */
	public RechercheJTable(HintTextField htf ,String i, TableRowSorter<TableModel> r){
		this.indication = i;
		this.rowSorter = r;
		this.recherche = htf;
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
        String text = recherche.getText();

        if (text.equals(indication)) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
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
