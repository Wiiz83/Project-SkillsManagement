package gui;

import java.util.Properties;

import javax.swing.JComponent;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class JDatePicker {

	JDatePickerImpl datePicker;
	
	public JDatePicker(int x, int y, int width, int height){
		UtilDateModel model = new UtilDateModel();
		model.setDate(20,04,2014);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		this.datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(x, y, width, height);
	}
	
	public JDatePickerImpl getDatePicker(){
		return datePicker;
	}
	
	
}
