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

import model.DateException;
import model.EnumException;
import model.Game;
import model.PlayerType;

public class RentGameDialog  extends JDialog implements ActionListener{
	private JTextField titleTxt;
	private JTextField renterTxt;
	private JTextField rentedOnTxt;
	private JTextField dueBackTxt;
	private JLabel title;
	private JLabel renter;
	private JLabel rentedOn;
	private JLabel dueBack;
	private JLabel player;
	private JTextField players;
	private JButton okButton;
	private JButton cancelButton;
	private boolean closeStatus;
	private Game unit;  

	private GregorianCalendar rentedOnDate;
	private GregorianCalendar dueBackDate;

	public RentGameDialog(JFrame parent, Game g) {	
		super(parent, true);
		unit = g;

		Date today = new Date();
		rentedOnDate = new GregorianCalendar();
		dueBackDate = new GregorianCalendar();

		rentedOnDate.setTime(today);
		dueBackDate.setTime(today);
		dueBackDate.add(GregorianCalendar.DAY_OF_MONTH, 7);

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		setTitle("Rent a Game");
		closeStatus = false;
		setLayout(new GridLayout(6,2));

		renter = new JLabel("Renter Name:");
		title = new JLabel("Game Title:");
		rentedOn = new JLabel("Rented On:");
		dueBack = new JLabel("Due Back:");
		player = new JLabel("Player Type:");
		
		titleTxt = new JTextField("Call of Duty");
		renterTxt = new JTextField("John Doe");
		rentedOnTxt = new JTextField(df.format(rentedOnDate.getTime()));
		dueBackTxt = new JTextField(df.format(dueBackDate.getTime()));
		
		players = new JTextField();

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
		add(player);
		add(players);
		add(okButton);
		add(cancelButton);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		pack();
		setSize(450,400);
		setVisible(true);


	}

	public boolean getCloseStatus() {
		return closeStatus;
	}
	
	private void processDate(String date, GregorianCalendar cal) 
			throws ParseException {
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		df.setLenient(false);
		Date time = df.parse(date);
		cal.setLenient(false);
		cal.setTime(time);
	}

	private void setDetails() throws Exception, ParseException, EnumException, DateException {
		
		if(renterTxt.getText().trim().isEmpty() || 
				titleTxt.getText().trim().isEmpty())
			throw new Exception();
		
		unit.setNameOfRenter(renterTxt.getText());
		unit.setTitle(titleTxt.getText());

		processDate(rentedOnTxt.getText(), rentedOnDate);
		processDate(dueBackTxt.getText(), dueBackDate);

		if(rentedOnDate.compareTo(dueBackDate) >= 0)
			throw new DateException();
		
		if(PlayerType.fromString(players.getText()) == null)
			throw new EnumException();
		
		unit.setDueBack(dueBackDate);
		unit.setBought(rentedOnDate);
		unit.setPlayer(PlayerType.fromString(players.getText()));
		unit.setDaysRented(rentedOnDate, dueBackDate);
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
			} catch (EnumException e3) {
				JOptionPane.showMessageDialog(this,"Player not supported.");
				closeStatus = false;
			} catch(Exception e4) {
				JOptionPane.showMessageDialog(this,"Fields cannot be empty!");
				closeStatus = false;
			}
		}

		if(comp == cancelButton || closeStatus)
			dispose();
	}	
	
}

