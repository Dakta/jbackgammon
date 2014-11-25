package jbackgammon;

import edu.princeton.cs.introcs.*;

public class Dice {
	int dice1;
	int dice2;

	boolean rolled = false;
	
	public void rollDice() {
		dice1 = StdRandom.uniform(1, 7);
		dice2 = StdRandom.uniform(1, 7);
		rolled = true;
	}
	
	public int getDice1(){
		return dice1;
	}
	
	public int getDice2(){
		return dice2;
	}
	
	public void resetDice(){
		dice1 = 0;
		dice2 = 0;
		rolled = false;
	}
	public boolean isDoubles(int dice1, int dice2){
		if (dice1 == dice2){
			return true;
		}
		else{
			return false;
		}
	}
	
	
}
