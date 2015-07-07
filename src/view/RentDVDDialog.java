package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

import model.DVD;

public class RentDVDDialog  extends JDialog implements ActionListener{
	private JTextField titleTxt;
	private JTextField renterTxt;
	private JTextField rentedOnTxt;
	private JTextField DueBackTxt;
	private JButton okButton;
	private JButton cancelButton;
	private boolean closeStatus;
	private DVD unit;  

	public RentDVDDialog(JFrame parent, DVD d) {	
		super(parent);
		unit = d;
	}
	public void actionPerformed(ActionEvent e) {
		//action handling
	}


}
