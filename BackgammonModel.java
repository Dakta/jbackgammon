package jbackgammon;

import java.awt.Color;

/*==============================================================================
 * The viewing angle for this whole thing is from the view on the white home 
 * quadrant's side. If you still aren't sure I'll send you a picture.
 * =============================================================================
 */
public class BackgammonModel {

	static final int empty = 0;
	static final int white = 1;
	static final int black = 2;
	// current player
	private Color currentPlayer;

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
	// decrement from
	// increment to
	public void move(int sourcePoint, int destPoint, int colr) {
		if (color[destPoint] == empty || color[destPoint] == colr) {
			count[sourcePoint]--;
			count[destPoint]++;
		} else {// not sure what to do if the move is illegal
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

	// returns the color of the occupant of the point
	public int getColor(int point) {
		return color[point];
	}

	// returns the number of pieces in a point
	public int getCount(int point) {
		return count[point];
	}

	// bear piece off
	// decrement from
	// increment home
	public void bearOff(int sourcePoint, int colr) {
		if (yesBear(colr) == true) {
			count[sourcePoint]--;
			if (colr == white) {
				white_home++;
			} else {
				black_home++;
			}
		}
	}

	// blotting
	// decrement from
	// increment rail

	// making sure the blotting can happen by checking to see if the colors are
	// different and the destination count is 1
	public boolean canBlot(int source, int dest) {
		if (color[source] != color[dest] && count[dest] == 1) {
			return true;
		}
		return false;
	}

	// blotting that fucker out
	public void blotMe(int source, int dest) {
		int colr = color[dest];
		if (canBlot(source, dest) == true) {
			count[dest]--;
			if (colr == white) {
				white_rail++;
			} else {
				black_rail++;
			}
			count[source]--;
			color[dest] = color[source];
		}
	}

	// enter from rail
	// decrement rail
	// increment to

	public boolean canEnter(int dest, int colr) {
		if (color[dest] != empty || color[dest] == colr) {
			return true;
		}
		return false;
	}

	// Two options for this one. Not sure which one will work better. The second
	// one
	// doesn't depend on the canEnter method
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

		// // white
		// if (colr == white) {
		// if (dest <= 23 && dest >= 18) {
		// if (color[dest] == empty) {
		// count[dest]++;
		// }
		// }
		// }
		// // black
		// if (colr == black) {
		// if (dest <= 5 && dest >= 0) {
		// if (color[dest] == empty) {
		// count[dest]++;
		// color[dest] = colr;
		// }
		// }
		//
		// }

	}

	// has won?
	// white rail == 15
	// black rail == 15
	// null

	// takes the home counts and returns if they've won or not
	public boolean hasWon(int home) {
		if (home == 15) {
			return true;
		}
		return false;
	}

}
