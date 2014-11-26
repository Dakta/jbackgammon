package jbackgammon;

import edu.princeton.cs.introcs.StdRandom;

/*==============================================================================
 * The viewing angle for this whole thing is from the view on the white home 
 * quadrant's side. If you still aren't sure I'll send you a picture.
 * =============================================================================
 */
public class BackgammonModel {

	static final int empty = 0;
	static final int white = 1;
	static final int black = 2;

	// Which point
	int count[];
	// Type of point
	int color[];

	// white rail int
	// black rail int
	int white_rail = 0;
	int black_rail = 0;

	// white/black HQ counts
	int white_HQ = 0;
	int black_HQ = 0;
	// white home int
	// black home int
	int white_home = 0;
	int black_home = 0;
	// dice initializing
	int dice1;
	int dice2;
	boolean rolled = false;

	// board position
	// Array of Points<Color>, is an int

	// Builds initial board (constructor, if you will)
	public BackgammonModel() {
		count = new int[24];
		color = new int[24];

		for (int i = 0; i < 24; i++) {
			count[i] = 0;
			color[i] = empty;
		}

		setColumn(0, 2, black);
		setColumn(5, 5, white);
		setColumn(7, 3, white);
		setColumn(11, 5, black);
		setColumn(12, 5, white);
		setColumn(16, 3, black);
		setColumn(18, 5, black);
		setColumn(23, 2, white);

	}

	// Setting a column up depending on the point #, number of occupants, and
	// the color of the occupants

	public void setColumn(int point, int num, int colr) {
		count[point] = num;
		if (num == 0) {
			colr = empty;
		}
		color[point] = colr;
	}

	// move piece from board to board
	public void move(int sourcePoint, int destPoint) {
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
			if (color[destPoint] == white) {
				white_rail++;
			} else {
				black_rail++;
			}
			color[destPoint] = color[sourcePoint];
			count[sourcePoint]--;
			if (count[sourcePoint] == 0) {
				color[sourcePoint] = empty;
			}
		} else {
			// illegal move, do nothing
		}
	}

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

	// returns the color of the occupant of the point
	public int getColor(int point) {
		return color[point - 1]; // array indexing
	}

	// returns the number of pieces in a point
	public int getCount(int point) {
		return count[point - 1]; // array indexing
	}

	// enter from rail
	public boolean canEnter(int dest, int colr) {
		if (color[dest] == empty || color[dest] == colr) {
			return true;
		}
		return false;
	}

	public void enterFromRail(int dest, int colr) {

		if (canEnter(dest, colr) == true) {
			// white
			if (dest <= 23 && dest >= 18) {
				count[dest]++;
				color[dest] = white;
			}
			// black
			if (dest <= 5 && dest >= 0) {
				count[dest]++;
				color[dest] = black;
			}
		}

	}

	// takes the home counts and returns if they've won or not
	public boolean hasWon(int home) {
		if (home == 15) {
			return true;
		}
		return false;
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
		String ret = "";
		// draw white home
		ret += "W ";
		for (int i = 0; i < this.white_home; i++) {
			ret += "O";
		}
		ret += "\n";
		// draw half board
		for (int i = 0; i < this.count.length / 2; i++) {
			ret += ((i + 1) / 10 < 1 ? " " : "") + (i + 1) + " ";
			for (int c = 0; c < this.count[i]; c++) {
				ret += (this.color[i] == white ? "O" : "#");
			}
			ret += "\n";
		}
		// draw rails
		ret += "  ";
		for (int i = 0; i < this.white_rail; i++) {
			ret += "O";
		}
		ret += "\n";
		ret += "  ";
		for (int i = 0; i < this.black_rail; i++) {
			ret += "#";
		}
		ret += "\n";
		// draw half board
		for (int i = this.count.length / 2; i < this.count.length; i++) {
			ret += ((i + 1) / 10 < 1 ? " " : "") + (i + 1) + " ";
			for (int c = 0; c < this.count[i]; c++) {
				ret += (this.color[i] == white ? "O" : "#");
			}
			ret += "\n";
		}

		// draw black home
		ret += "B ";
		for (int i = 0; i < this.black_home; i++) {
			ret += "#";
		}
		ret += "\n";

		return ret;
	}

}
