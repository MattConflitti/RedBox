package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

/***********************************************************************
 * Return dialog creates a JDialog box that takes a date and sends
 * it to the main GUI for processing
 * 
 * @author Matt Conflitti
 * @version 1.0
 **********************************************************************/
public class ReturnDialog  extends JDialog implements ActionListener{

	/** JTextfield for return date */
	private JTextField returnTxt;
	
	/** JLabel for return date */
	private JLabel returnLabel;
	
	/** ok button */
	private JButton okButton;
	
	/** cancel button */
	private JButton cancelButton;
	
	/** close status if input is ready to be sent to main GUI */
	private boolean closeStatus;
	
	/** DVD to be updated with information */
	private DVD unit;
	
	/** stores the date of return */
	private GregorianCalendar returnDate;

	/*******************************************************************
	 * Return dialog creates a JDialog box that takes a date and sends
	 * it to the main GUI for processing
	 * 
	 * @param parent JFrame to be modal in
	 * @param d DVD to update information
	 ******************************************************************/
	public ReturnDialog(JFrame parent, DVD d) {	
		super(parent, true);
		unit = d;
		
		returnDate = new GregorianCalendar();
		returnDate.setTime(d.getDueBack().getTime());

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		setTitle("Return");
		closeStatus = false;
		setLayout(new GridLayout(2,2));
		returnLabel = new JLabel("Due Back:");

		returnTxt = new JTextField(df.format(returnDate.getTime()));

		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");

		add(returnLabel);
		add(returnTxt);
		add(okButton);
		add(cancelButton);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		pack();
		setSize(400,100);
		setVisible(true);
		setPreferredSize(new Dimension(400,100));
		setLocationRelativeTo(null);


	}

	public boolean getCloseStatus() {
		return closeStatus;
	}

	private void processDate(String date, GregorianCalendar cal) 
			throws ParseException {
		
//		String[] array = date.split("/");
//		if(array.length == 3) {
//			if(array[0].length() != 2 || array[1].length() != 2 ||
//					array[2].length() != 4)
//				throw new NumberFormatException();
//		} else {
//			throw new NumberFormatException();
//		}
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		df.setLenient(false);
		Date time = df.parse(date);
		cal.setLenient(false);
		cal.setTime(time);
	}

	private void getDateComparison() throws Exception, ParseException {

		processDate(returnTxt.getText(), returnDate);

		if(unit.getBought().after(returnDate))
			throw new Exception();

		if(unit.getDueBack().before(returnDate)) {
			unit.setDaysLate(returnDate);
		}

	}



	public void actionPerformed(ActionEvent e) {
		JComponent comp = (JComponent) e.getSource();

		if(comp == okButton) {
			closeStatus = true;
			try {
				getDateComparison();
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(this,"Incorrect Date Format");
				closeStatus = false;
			} catch(Exception e2) {
				JOptionPane.showMessageDialog(this,"Return date must be after rent date.");
				closeStatus = false;
			}
		}

		if(comp == cancelButton || closeStatus)
			dispose();
	}


}
