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

	public BackgammonModel(){
		count = new int[24];
		color = new int[24];
		
		for (int i = 0; i < 24; i++){
			count[i] = 0;
			color[i] = empty;
		}
	}

	// move piece from board to board
	// decrement from
	// increment to

	// bear piece off
	// can bear off?
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
