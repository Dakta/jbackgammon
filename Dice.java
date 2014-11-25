package jbackgammon;

import java.util.List;
import java.util.ArrayList;

import edu.princeton.cs.introcs.*;

public class Dice {
	
	private static List<Integer> dice = new ArrayList<Integer>();

	public static List diceRoller() {
		int die1 = StdRandom.uniform(1,7);
		int die2 = StdRandom.uniform(1,7);
		dice.add(die1);
		dice.add(die2);
		return dice;
	}

	public static boolean isDouble(List dice) {
		if (dice.get(0) == dice.get(1) ) {
			return true;
		} else {
			return false;
		}

	}
	
	public static List fourMoves(List dice){
		dice = diceRoller();
		if (isDouble(dice)){
			dice.addAll(dice);
			return dice;
		}
		else{
			return dice;
		}
		
	}
	
	
	
}
