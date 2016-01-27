package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;
import java.util.GregorianCalendar;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import model.DVD;
import model.DueBackComparator;
import model.Game;
import model.NameComparator;
import model.RentalStore;
import model.RentedOnComparator;
import model.TitleComparator;

/***********************************************************************
 * RentalStoreGUI utilizes all of the classes in the RedBox project
 * either directly or indirectly. It gives a visual representation
 * of how this concept could be utilized by a rental store. 
 * 
 * @author Matt Conflitti
 * @version 1.0
 **********************************************************************/
public class RentalStoreGUI extends JFrame implements ActionListener {

	/** menu bar */
	private JMenuBar menu;

	/** file menu */
	private JMenu file;

	/** action menu */
	private JMenu action;

	/** open menuitem */
	private JMenuItem open;

	/** save menu item */
	private JMenuItem save;

	/** close menu item */
	private JMenuItem close;

	/** rent dvd menu item */
	private JMenuItem rentDVD;

	/** rent game menu item */
	private JMenuItem rentGame;

	/** return unit menu item */
	private JMenuItem returnUnit;

	/** filter menu item */
	private JMenuItem filter;

	/** filter off menu item */
	private JMenuItem filterOff;

	/** sort menu */
	private JMenu sort;

	/** by name menu item */
	private JMenuItem byName;

	/** by title menu item */
	private JMenuItem byTitle;

	/** by rent date menu item */
	private JMenuItem byRentDate;

	/** by due date menu item */
	private JMenuItem byDueDate;

	/** rentalstore object */
	private RentalStore list;

	/** jlist area */
	private JList<String> listArea;

	/** if filter has been set */
	private boolean isFilterSet;

	/** filter date */
	private GregorianCalendar filterDate;

	/*******************************************************************
	 * Class constructor sets title of JFrame and sets all Swing
	 * components in place.
	 ******************************************************************/
	public RentalStoreGUI(String title) {
		super(title);

		//instantiate the rentalstore object
		list = new RentalStore();
		isFilterSet = false;

		//set up the menubar
		menu = new JMenuBar();
		file = new JMenu("File");
		action = new JMenu("Action");
		sort = new JMenu("Sort");

		open = new JMenuItem("Open Serial");
		save = new JMenuItem("Save Serial");
		close = new JMenuItem("Exit");

		rentDVD = new JMenuItem("Rent DVD");
		rentGame = new JMenuItem("Rent Game");
		returnUnit = new JMenuItem("Return");
		filter = new JMenuItem("Turn on Filter");
		filterOff = new JMenuItem("Turn off Filter");

		filterOff.setEnabled(false);

		byName = new JMenuItem("By Name");
		byTitle = new JMenuItem("By Title");
		byRentDate = new JMenuItem("By Rental Date");
		byDueDate = new JMenuItem("By Due Date");

		file.add(open);
		file.add(save);
		file.addSeparator();
		file.add(close);

		action.add(rentDVD);
		action.add(rentGame);
		action.addSeparator();
		action.add(returnUnit);
		action.addSeparator();
		action.add(filter);
		action.add(filterOff);

		sort.add(byName);
		sort.add(byTitle);
		sort.add(byRentDate);
		sort.add(byDueDate);

		menu.add(file);
		menu.add(action);
		menu.add(sort);

		setJMenuBar(menu);

		//set the jlist to the rentalstore
		listArea = new JList<String>(list);
		listArea.setPreferredSize(new Dimension(700,300));

		add(listArea);

		//add the actionlisteners
		open.addActionListener(this);
		save.addActionListener(this);
		close.addActionListener(this);
		rentDVD.addActionListener(this);
		rentGame.addActionListener(this);
		returnUnit.addActionListener(this);
		filter.addActionListener(this);
		filterOff.addActionListener(this);
		byName.addActionListener(this);
		byTitle.addActionListener(this);
		byRentDate.addActionListener(this);
		byDueDate.addActionListener(this);

		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);

	}

	/*******************************************************************
	 * Main method instantiates the GUI.
	 * 
	 * @param args String array for CLI arguments
	 ******************************************************************/
	public static void main(String[] args) {
		new RentalStoreGUI("RedBox");
	}

	/*******************************************************************
	 * ActionPerformed method handles all user input.  Depending on 
	 * which button is pushed by the user, a different result will occur
	 * 
	 * @param e ActionEvent when something is clicked
	 ******************************************************************/
	public void actionPerformed(ActionEvent e) {

		//cast event source to a jcomponent
		JComponent comp = (JComponent) e.getSource();

		//close button
		if(comp == close)
			System.exit(0);

		//open a file
		if(comp == open) {
			JFileChooser fc = new JFileChooser();
			File currentDir = new File(System.getProperty("user.dir"));
			fc.setCurrentDirectory(currentDir);
			int returnVal = fc.showOpenDialog(this);

			if(returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				try{
					list.loadDB(file.getName());
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(this, 
							"Problem loading file.");
				}
			}


		}

		//save the current list
		if(comp == save) {

			JFileChooser fc = new JFileChooser();
			File currentDir = new File(System.getProperty("user.dir"));
			fc.setCurrentDirectory(currentDir);
			int returnVal = fc.showSaveDialog(this);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				try{
					if(!(file.getName().trim().equals("")))
						list.saveDB(file.getName());
					else
						throw new Exception();
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(this, 
							"Problem saving file.");
				}
			}
		}

		//rent a dvd and add it to the list
		if(comp == rentDVD) {
			DVD dvd = new DVD();
			RentDVDDialog d = new RentDVDDialog(this, dvd);
			if(d.getCloseStatus())
				list.add(dvd);
			if(isFilterSet)

				//if filter is set change comp to filter to move
				//handling down to that branch
				comp = filter;
		}

		//if game is rented handle it
		if(comp == rentGame) {
			Game game = new Game();
			RentGameDialog d = new RentGameDialog(this, game);
			if(d.getCloseStatus())
				list.add(game);
			if(isFilterSet)
				comp = filter;
		}

		//return a game or dvd
		if(comp == returnUnit) {

			NumberFormat nf = NumberFormat.getCurrencyInstance();

			if(listArea.getSelectedIndex() > -1) {
				DVD unit = list.get(listArea.getSelectedIndex());

				ReturnDialog d = new ReturnDialog(this, unit);
				if(d.getCloseStatus()) {
					JOptionPane.showMessageDialog(this,
							"Thanks " + unit.getNameOfRenter() + 
							" for returning " + unit.getTitle() +
							". You owe " + nf.format(unit.getCost()));

					list.remove(listArea.getSelectedIndex());
				}
			} else {
				JOptionPane.showMessageDialog(this, 
						"Please select an entry.");
			}	
		}

		//filter the list by date
		if(comp == filter) {
			if(!isFilterSet) {
				FilterDialog f = new FilterDialog(this);
				filterDate = f.getDate();
				if(f.getCloseStatus()) {
					list.setUsedList(list.filter(filterDate));
					isFilterSet = true;
					filter.setEnabled(false);
					filterOff.setEnabled(true);
				} 
			}else {
				list.setUsedList(list.filter(filterDate));
			}
		}

		//turn filter off
		if(comp == filterOff) {
			filter.setEnabled(true);
			filterOff.setEnabled(false);
			isFilterSet = false;
			list.resetList();
		}

		//if listsize is at least 1
		if(list.getSize() > 0) {

			//sort list by name
			if(comp == byName) {
				list.sort(new NameComparator());
				setTitle("RedBox: Sorted By Name");
			}

			//sort list by title
			if(comp == byTitle) {
				list.sort(new TitleComparator());
				setTitle("RedBox: Sorted By Title");
			}

			//sort list by rental date
			if(comp == byRentDate) {
				list.sort(new RentedOnComparator());
				setTitle("RedBox: Sorted By Rent Date");
			}

			//sort list by due date
			if(comp == byDueDate) {
				list.sort(new DueBackComparator());
				setTitle("RedBox: Sorted By Due Date");
			}
		}

	}
}