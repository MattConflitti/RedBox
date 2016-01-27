package view;

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
import model.DateException;

public class RentDVDDialog  extends JDialog implements ActionListener{
	private JTextField titleTxt;
	private JTextField renterTxt;
	private JTextField rentedOnTxt;
	private JTextField dueBackTxt;
	private JLabel title;
	private JLabel renter;
	private JLabel rentedOn;
	private JLabel dueBack;
	private JButton okButton;
	private JButton cancelButton;
	private boolean closeStatus;
	private DVD unit;  

	private GregorianCalendar rentedOnDate;
	private GregorianCalendar dueBackDate;

	public RentDVDDialog(JFrame parent, DVD d) {	
		super(parent, true);
		unit = d;

		Date today = new Date();
		rentedOnDate = new GregorianCalendar();
		dueBackDate = new GregorianCalendar();

		rentedOnDate.setTime(today);
		dueBackDate.setTime(today);
		dueBackDate.add(GregorianCalendar.DAY_OF_MONTH, 7);

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		setTitle("Rent a DVD");
		closeStatus = false;
		setLayout(new GridLayout(5,2));

		renter = new JLabel("Renter Name:");
		title = new JLabel("DVD Title:");
		rentedOn = new JLabel("Rented On:");
		dueBack = new JLabel("Due Back:");

		titleTxt = new JTextField("Avengers");
		renterTxt = new JTextField("John Doe");
		rentedOnTxt = new JTextField(df.format(rentedOnDate.getTime()));
		dueBackTxt = new JTextField(df.format(dueBackDate.getTime()));

		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");

		add(renter);
		add(renterTxt);
		add(title);
		add(titleTxt);
		add(rentedOn);
		add(rentedOnTxt);
		add(dueBack);
		add(dueBackTxt);
		add(okButton);
		add(cancelButton);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		pack();
		setSize(400,300);
		setVisible(true);

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
//		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		df.setLenient(false);
		Date time = df.parse(date);
		cal.setLenient(false);
		cal.setTime(time);
	}

	private void setDetails() throws Exception, ParseException, DateException {
		if(renterTxt.getText().trim().isEmpty() || 
				titleTxt.getText().trim().isEmpty())
			throw new Exception();
		
		unit.setNameOfRenter(renterTxt.getText());
		unit.setTitle(titleTxt.getText());

		processDate(rentedOnTxt.getText(), rentedOnDate);
		processDate(dueBackTxt.getText(), dueBackDate);

		if(rentedOnDate.compareTo(dueBackDate) >= 0)
			throw new DateException();

		unit.setDueBack(dueBackDate);
		unit.setBought(rentedOnDate);
		unit.setDaysRented(rentedOnDate,dueBackDate);
	}

	public void actionPerformed(ActionEvent e) {
		JComponent comp = (JComponent) e.getSource();

		if(comp == okButton) {
			closeStatus = true;
			try {
				setDetails();
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(this,"Incorrect Date Format");
				closeStatus = false;
			} catch (DateException e2) {
				JOptionPane.showMessageDialog(this,"Due date must be after rented date.");
				closeStatus = false;
			} catch(Exception e3) {
				JOptionPane.showMessageDialog(this,"Fields cannot be empty.");
				closeStatus = false;
			}
		}

		if(comp == cancelButton || closeStatus)
			dispose();
	}

}
