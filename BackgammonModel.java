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

//	static final int empty = 0;
//	static final int white = 1;
//	static final int black = 2;

//	// Which point
//	int count[];
//	// Type of point
//	int color[];
//
//	// rails
//	int rail[] = new int[3]; // rail[0] is unused
//
//	// white/black HQ counts
//	int white_HQ = 0;
//	int black_HQ = 0;
//	// white home int
//	// black home int
//	int white_home = 0;
//	int black_home = 0;
	// dice initializing
	int dice1;
	int dice2;
	boolean rolled = false;

	// board position
	// Array of Points<Color>, is an int

	// use java.awt.Color objects to refer to players
	// also refers to pieces
	Color player1;
	Color player2;
	// points is a list of lists of colors
	List<LinkedList<Color>> points;
	// rails is a map where the key is a color, the value is a list
	Map<Color, LinkedList<Color>> rails;
	// same for homes
	Map<Color, LinkedList<Color>> homes;
	// this allows us to get a player's home when we only know their color
	// without having to hard-code a variable for it
	
	// Builds initial board (constructor, if you will)
	public BackgammonModel(Color player1, Color player2) {
		this.player1 = player1;
		this.player2 = player2;
		
		this.points = new LinkedList<LinkedList<Color>>();
		this.rails = new HashMap<Color, LinkedList<Color>>();
		this.homes = new HashMap<Color, LinkedList<Color>>();
		
		for (int i=0; i<24; i++) {
			// put empty points (empty list) into the list of points
			this.points.add(new LinkedList<Color>());
		}
		// put empty lists in the rails and homes
		this.rails.put(player1, new LinkedList<Color>());
		this.rails.put(player2, new LinkedList<Color>());
		this.homes.put(player1, new LinkedList<Color>());
		this.homes.put(player2, new LinkedList<Color>());
		
//		count = new int[24];
//		color = new int[24];
//
//		for (int i = 0; i < 24; i++) {
//			count[i] = 0;
//			color[i] = empty;
//		}

		setColumn(1, 2, player1);
		setColumn(6, 5, player2);
		setColumn(8, 3, player2);
		setColumn(12, 5, player1);
		setColumn(13, 5, player2);
		setColumn(17, 3, player1);
		setColumn(19, 5, player1);
		setColumn(24, 2, player2);

	}

	// Setting a column up depending on the point #, number of occupants, and
	// the color of the occupants
	// overload convenience method
	public void setColumn(int pos, int num, java.awt.Color color) {
		this.setColumn(this.points.get(pos - 1), num, color);
	}
	public void setColumn(LinkedList<Color> point, int num, java.awt.Color color) {
		for (int i=0; i<num; i++) {
			point.push(color);
		}
	}

	// overload method, takes point numbers for move
	// not used
	public void move(int f, int t) {
		this.move(this.points.get(f-1), this.points.get(t-1));
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
			dest.add(source.remove(source.size()-1));
		} else if (dest.size() == 1 && !dest.contains(source.get(0))) {
			// blot
			// move opponent's piece to rail
			this.move(dest, this.rails.get(dest.get(0)));
			// move our piece to now-empty point
			this.move(source, dest);
		} else {
			// illegal move, do nothing
		}
	}

	/*
	// move piece from board to board
	public void move(int sourcePoint, int destPoint) {
		// array indexing
		sourcePoint -= 1;
		destPoint -= 1;
		
		if (color[destPoint] == empty || color[destPoint] == color[sourcePoint]) {
			// regular move
			count[sourcePoint]--;
			color[destPoint] = color[sourcePoint];
			if (count[sourcePoint] == 0) {
				color[sourcePoint] = empty;
			}
			count[destPoint]++;
		} else if (color[destPoint] != empty
				&& color[destPoint] != color[sourcePoint]
				&& count[destPoint] == 1) {
			// blot other player
			rail[color[destPoint]]++;

			color[destPoint] = color[sourcePoint];
			count[sourcePoint]--;
			if (count[sourcePoint] == 0) {
				color[sourcePoint] = empty;
			}
		} else {
			// illegal move, do nothing
		}
	}
	*/

	/*
	// can bear off?
	public boolean yesBear(int color) {
		int sum = 0;

		if (color == white) {
			for (int i = 0; i <= 5; i++) {
				if (getColor(i) == white) {
					sum += getCount(i);
				}
			}
			sum += white_HQ;
		}

		if (color == black) {
			for (int i = 18; i <= 23; i++) {
				if (getColor(i) == black) {
					sum += getCount(i);
				}
			}
			sum += black_HQ;
		}
		if (sum == 15) {
			return true;
		}
		return false;
	}
	*/

	/*
	public void bearOff(int sourcePoint, int colr) {

		// bear piece off
		if (yesBear(colr) == true) {
			count[sourcePoint]--;
			if (colr == white) {
				white_home++;
			} else {
				black_home++;
			}
		}
	}
	*/

	/*
	// returns the color of the occupant of the point
	public int getColor(int point) {
		return color[point - 1]; // array indexing
	}
	*/
	
	public Color getColor(List<Color> point) {
		return point.get(0);
	}

	/*
	// returns the number of pieces in a point
	public int getCount(int point) {
		return count[point - 1]; // array indexing
	}
	*/

	/*
	// enter from rail
	public boolean canEnter(int colr, int dest) {
		if (color[dest] == empty || color[dest] == colr) {
			// can enter any empty point or point of own color
			return true;
		} else if (color[dest] != empty && color[dest] != colr && count[dest] == 1) {
			// can enter any opponent's blot
			return true;
		}
		return false;
	}
	*/
	
	/*
	public void enterFromRail(int colr, int dest) {
		// array indexing
		dest -= 1;
		
		if (color[dest] == empty || color[dest] == colr) {
			// regular enter
			rail[colr]--;
			color[dest] = colr;
			count[dest]++;
		} else if (color[dest] != empty
				&& color[dest] != colr
				&& count[dest] == 1) {
			// blot other player
			rail[colr]--;
			rail[color[dest]]++;
			color[dest] = colr;
		} else {
			// illegal move, do nothing
		}

		
//		if (canEnter(colr, dest)) {
//			// white
//			if (dest <= 23 && dest >= 18) {
//				count[dest]++;
//				color[dest] = white;
//			}
//			// black
//			if (dest <= 5 && dest >= 0) {
//				count[dest]++;
//				color[dest] = black;
//			}
//		} else {
//			// illegal move, do nothing
//		}

	}
	*/

	/*
	// takes the home counts and returns if they've won or not
	public boolean hasWon(int home) {
		if (home == 15) {
			return true;
		}
		return false;
	}
	*/

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
		String ret = "";
		// draw white home
		ret += "W ";
		for (int i = 0; i < this.homes.get(player1).size(); i++) {
			ret += "O";
		}
		ret += "\n";
		// draw half board
		for (int i = 0; i < this.points.size() / 2; i++) {
			ret += ((i + 1) / 10 < 1 ? " " : "") + (i + 1) + " ";
			for (int c = 0; c < this.points.get(i).size(); c++) {
				ret += (getColor(this.points.get(i)) == player1 ? "#" : "O");
			}
			ret += "\n";
		}
		// draw rails
		ret += "  ";
		for (int i=0; i<this.rails.get(player2).size(); i++) {
			ret += "O";
		}
		ret += "\n";
		ret += "  ";
		for (int i=0; i<this.rails.get(player1).size(); i++) {
			ret += "#";
		}
		ret += "\n";
		// draw half board
		for (int i = this.points.size() / 2; i < this.points.size(); i++) {
			ret += ((i + 1) / 10 < 1 ? " " : "") + (i + 1) + " ";
			for (int c = 0; c < this.points.get(i).size(); c++) {
				ret += (getColor(this.points.get(i)) == player1 ? "#" : "O");
			}
			ret += "\n";
		}

		// draw black home
		ret += "B ";
		for (int i = 0; i < this.homes.get(player2).size(); i++) {
			ret += "#";
		}
		ret += "\n";

		return ret;
	}

}
