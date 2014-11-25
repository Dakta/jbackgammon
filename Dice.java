package jbackgammon;

import edu.princeton.cs.introcs.*;

public class Dice {
	int dice1;
	int dice2;

	boolean rolled = false;
	
	//actually rolls the dice
	public void rollDice() {
		dice1 = StdRandom.uniform(1, 7);
		dice2 = StdRandom.uniform(1, 7);
		rolled = true;
	}
	//getters which i know we will need
	public int getDice1(){
		return dice1;
	}
	
	public int getDice2(){
		return dice2;
	}
	//reset the dice for next roll
	public void resetDice(){
		dice1 = 0;
		dice2 = 0;
		rolled = false;
	}
	//checks for doubles
	public boolean isDoubles(int dice1, int dice2){
		if (dice1 == dice2){
			return true;
		}
		else{
			return false;
		}
	}
	//sets the dice to values for drawing
	public void setDiceValues(int roll1, int roll2){
		dice1 = roll1;
		dice2 = roll2;
	}
	
}
