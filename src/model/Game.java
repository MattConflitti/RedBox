package model;

import java.util.GregorianCalendar;

/***********************************************************************
 * @author Matt Conflitti
 * @version 1.0
 * 
 * Game class extends DVD, but overrides one method and has an extra
 * field for console type.
 **********************************************************************/
public class Game extends DVD {
	
    /** Represents the type of player */
    private PlayerType player;

    /*******************************************************************
     * Default constructor only calls super constructor
     ******************************************************************/
    public Game() {
    	super();
    }
    
    /*******************************************************************
     * Constructor sets all fields of Game
     * 
     * @param bought GregorianCalendar object for rented date
	 * @param dueBack GregorianCalendar object for due back date
	 * @param title name of Game
	 * @param nameOfRenter name of renter
	 * @param player player type
     ******************************************************************/
	public Game(GregorianCalendar bought, GregorianCalendar dueBack,
			String title, String nameOfRenter, PlayerType player) {
		super(bought, dueBack, title, nameOfRenter);
		this.player = player;
	}

	/*******************************************************************
     * Getter method for player
     * 
     * @return PlayerType
     ******************************************************************/
	public PlayerType getPlayer() {
		return player;
	}

	/*******************************************************************
     * Setter method for player
     * 
     * @param player PlayerType enumeration
     ******************************************************************/
	public void setPlayer(PlayerType player) {
		this.player = player;
	}
	
	/*******************************************************************
     * Calculates cost of rental
     * 
     * @return total cost
     ******************************************************************/
	public double getCost() {
		return (1.00 * daysRented) + (.50 * daysLate);
	}
	
	

}
