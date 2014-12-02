package jbackgammon;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.introcs.StdRandom;

/*==============================================================================
 * The viewing angle for this whole thing is from the view on the white home 
 * quadrant's side. If you still aren't sure I'll send you a picture.
 * =============================================================================
 */
public class BackgammonModel {

	// dice initializing
	int dice1;
	int dice2;
	boolean rolled = false;

	// board position
	// Array of Points<Color>, is an int

	// use java.awt.Color objects to refer to players
	// also refers to pieces
	private Color player1;
	private Color player2;
	// points is a list of lists of colors
	private List<List<Color>> points;
	// rails is a map where the key is a color, the value is a list
	private Map<Color, List<Color>> rails;
	// same for homes
	private Map<Color, List<Color>> homes;

	private List<BackgammonState> state;

	// this allows us to get a player's home when we only know their color
	// without having to hard-code a variable for it
	
	// Builds initial board (constructor, if you will)
	public BackgammonModel(Color player1, Color player2) {
		this.state = new LinkedList<BackgammonState>();
		
		this.player1 = player1;
		this.player2 = player2;

		this.points = new LinkedList<List<Color>>();
		this.rails = new HashMap<Color, List<Color>>();
		this.homes = new HashMap<Color, List<Color>>();


		for (int i = 0; i < 24; i++) {

			this.points.add(new LinkedList<Color>());
		}
		
		// put empty lists in the rails and homes
		this.rails.put(player1, new LinkedList<Color>());
		this.rails.put(player2, new LinkedList<Color>());
		this.homes.put(player1, new LinkedList<Color>());
		this.homes.put(player2, new LinkedList<Color>());

		// count = new int[24];
		// color = new int[24];
		//
		// for (int i = 0; i < 24; i++) {
		// count[i] = 0;
		// color[i] = empty;
		// }

		setColumn(1, 2, player1);
		setColumn(6, 5, player2);
		setColumn(8, 3, player2);
		setColumn(12, 5, player1);
		setColumn(13, 5, player2);
		setColumn(17, 3, player1);
		setColumn(19, 5, player1);
		setColumn(24, 2, player2);

		// update the game state history
		this.state.add(new BackgammonState(this.player1, this.player2, this.points, this.rails, this.homes));

	}

	// Setting a column up depending on the point #, number of occupants, and
	// the color of the occupants
	// overload convenience method
	public void setColumn(int pos, int num, java.awt.Color color) {
		this.setColumn(this.points.get(pos - 1), num, color);
	}

	public void setColumn(List<Color> point, int num, java.awt.Color color) {
		for (int i = 0; i < num; i++) {
			point.add(color);
		}
	}

	// overload method, takes point numbers for move
	// not used
	public void move(int f, int t) {
		this.move(this.points.get(f - 1), this.points.get(t - 1));
	}

	/*
	 * Move a piece from source to dest. If dest is a blot (single opponent piece),
	 * hit it (move it to opponent's rail).
	 */

	public void move(List<Color> source, List<Color> dest) {
		if (source.size() == 0) {
			// no piece to move
			return;
		}
		if (dest.size() == 0 || dest.contains(source.get(0))) {
			// regular move
			dest.add(source.remove(source.size() - 1));
		} else if (dest.size() == 1 && !dest.contains(source.get(0))) {
			// blot
			// move opponent's piece to rail
			this.move(dest, this.rails.get(dest.get(0)));
			// move our piece to now-empty point
			this.move(source, dest);
		} else {
			// illegal move, do nothing
		}
		// update the game state history
		this.state.add(new BackgammonState(this.player1, this.player2, this.points, this.rails, this.homes));
	}
	

	public static Color getColor(List<Color> point) {
		return point.get(0);
	}
	
	public Color getPlayer1(){
		return this.player1;
	}
	
	public Color getPlayer2(){
		return this.player2;
	}
	
	
	public List<Color> getRail(Color player){
		return this.rails.get(player);
	}
	
	public List<Color> getHome(Color player){
		return this.homes.get(player);
	}
	
	public List<Color> getPoint(int point){
		return this.points.get(point - 1 );
	}
	
	public List<List<Color>> getPoints(){
		return this.points;
	}
	
	public List<Color> getPlayer1Rail(){
		return this.rails.get(this.player1);
	}
	
	public List<Color> getPlayer2Rail(){
		return this.rails.get(this.player2);
	}

	public List<Color> getPlayer1Home(){
		return this.homes.get(this.player1);
	}
	
	public List<Color> getPlayer2Home(){
		return this.homes.get(this.player2);
	}
	
	
	// actually rolls the dice
	public void rollDice() {
		dice1 = StdRandom.uniform(1, 7);
		dice2 = StdRandom.uniform(1, 7);
		rolled = true;
	}

	// getters which i know we will need
	public int getDice1() {
		return dice1;
	}

	public int getDice2() {
		return dice2;
	}

	// reset the dice for next roll
	public void resetDice() {
		dice1 = 0;
		dice2 = 0;
		rolled = false;
	}

	// checks for doubles
	public boolean isDoubles(int dice1, int dice2) {
		if (dice1 == dice2) {
			return true;
		} else {
			return false;
		}
	}

	// sets the dice to values for drawing
	public void setDiceValues(int roll1, int roll2) {
		dice1 = roll1;
		dice2 = roll2;
	}

	public String toString() {
		return this.state.get(this.state.size() - 1).toString();
	}

}
