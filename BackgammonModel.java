package jbackgammon;

import java.awt.Color;

public class BackgammonModel<playerOne, playerTwo> {

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
		} else {//not sure what to do if the move is illegal
		}
	}
	
	// can bear off?
	public boolean yesBear(int color){
		int sum = 0;
		
		if (color == white){
			for (int i = 0; i <= 5; i++){
				if (getColor(i) == white){
					sum += getCount(i);
				}
			}
			sum += white_home;
		}
		
		if (color == black){
			for (int i = 18; i <= 23; i++){
				if (getColor(i) == black){
					sum += getCount(i);
				}
			}
			sum += black_home;
		}
		if (sum == 15){
			return true;
		}
		return false;
	}
	
	//returns the color of the occupant of the point
	public int getColor(int point){
		return color[point];
	}
	//returns the number of pieces in a point
	public int getCount(int point){
		return count[point];
	}
	
	// bear piece off
	// decrement from
	// increment home

	// blotting
	// decrement from
	// increment rail

	// enter from rail
	// decrement rail
	// increment to

	// has won?
	// white rail == 15
	// black rail == 15
	// null

}
