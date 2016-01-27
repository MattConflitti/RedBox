package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.DVD;

public class FilterDialog  extends JDialog implements ActionListener{
	private JTextField filterTxt;
	private JLabel filterLabel;
	private JButton okButton;
	private JButton cancelButton;
	private boolean closeStatus;
	private DVD unit;
	private GregorianCalendar filterDate;

	public FilterDialog(JFrame parent) {	
		super(parent, true);

		filterDate = new GregorianCalendar();
		filterDate.setTime(Calendar.getInstance().getTime());

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		setTitle("Filter");
		closeStatus = false;
		setLayout(new GridLayout(2,2));
		filterLabel = new JLabel("Show all late rentals after:");

		filterTxt = new JTextField(df.format(filterDate.getTime()));

		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");

		add(filterLabel);
		add(filterTxt);
		add(okButton);
		add(cancelButton);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		pack();
		setSize(400,100);
		setVisible(true);


	}

	public boolean getCloseStatus() {
		return closeStatus;
	}

	private void processDate(String date, GregorianCalendar cal) 
			throws ParseException, NumberFormatException {

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		df.setLenient(false);
		Date time = df.parse(date);
		cal.setLenient(false);
		cal.setTime(time);
	}

	public GregorianCalendar getDate() {
		return filterDate;
	}

	public void actionPerformed(ActionEvent e) {
		JComponent comp = (JComponent) e.getSource();

		if(comp == okButton) {
			closeStatus = true;
			try {
				processDate(filterTxt.getText(),filterDate);
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(this,"Incorrect Date Format");
				closeStatus = false;
			} catch(NumberFormatException e2) {
				JOptionPane.showMessageDialog(this,"Incorrect Date Format");
				closeStatus = false;
			}
		}

		if(comp == cancelButton || closeStatus)
			dispose();
	}


}
