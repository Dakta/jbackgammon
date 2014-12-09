package jbackgammon;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.introcs.StdOut;
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
	List<Integer> dice;

	List<BackgammonState> state;
	
	// Builds initial board (constructor, if you will)
	public BackgammonModel(Color player1, Color player2) {
		this.state = new LinkedList<BackgammonState>();
		
		// points is a list of lists of colors
		List<List<Color>> points;
		// rails is a map where the key is a color, the value is a list
		Map<Color, List<Color>> rails;
		// same for homes
		Map<Color, List<Color>> homes;

		points = new LinkedList<List<Color>>();
		rails = new HashMap<Color, List<Color>>();
		homes = new HashMap<Color, List<Color>>();

		for (int i = 0; i < 24; i++) {
			points.add(new LinkedList<Color>());
		}
		
		// put empty lists in the rails and homes
		rails.put(player1, new LinkedList<Color>());
		rails.put(player2, new LinkedList<Color>());
		homes.put(player1, new LinkedList<Color>());
		homes.put(player2, new LinkedList<Color>());

		// fill out starting positions
		setColumn(points, 1, 2, player1);
		setColumn(points, 6, 5, player2);
		setColumn(points, 8, 3, player2);
		setColumn(points, 12, 5, player1);
		setColumn(points, 13, 5, player2);
		setColumn(points, 17, 3, player1);
		setColumn(points, 19, 5, player1);
		setColumn(points, 24, 2, player2);

		Color currentPlayer = player1;
		
		// update the game state history
		this.state.add(new BackgammonState(player1, player2, currentPlayer, points, rails, homes));

	}

	// Setting a column up depending on the point #, number of occupants, and
	// the color of the occupants
	// overload convenience method
	public void setColumn(List<List<Color>>points, int pos, int num, java.awt.Color color) {
		this.setColumn(points.get(pos - 1), num, color);
	}

	public void setColumn(List<Color> point, int num, java.awt.Color color) {
		for (int i = 0; i < num; i++) {
			point.add(color);
		}
	}

	
	// move a piece from one point to another
	public void move(int f, int t) {
		BackgammonState newState = new BackgammonState(this.getState());
		newState.move(newState.points.get(f - 1), newState.points.get(t - 1));
		this.state.add(newState);
	}

	public void enterFromRail(Color player, int dest) {
		BackgammonState newState = new BackgammonState(this.getState());
		newState.move(newState.rails.get(player), newState.points.get(dest - 1));
		this.state.add(newState);
	}
	public void bearOff(Color player, int source) {
		BackgammonState newState = new BackgammonState(this.getState());
		newState.move(newState.points.get(source - 1), newState.homes.get(player));
		this.state.add(newState);
	}

	public void nextTurn() {
		BackgammonState newState = new BackgammonState(this.getState());
		newState.currentPlayer = this.getCurrentPlayer() == this.getPlayer1() ? this.getPlayer2() : this.getPlayer1();
		this.state.add(newState);
	}

	// gets current state
	public BackgammonState getState(){
		return this.getState(0);
	}
	// gets previous state
	public BackgammonState getPreviousState(){
		return this.getState(1);
	}
	// gets a state i back from current
	public BackgammonState getState(int i){
		return this.state.get((this.state.size() - 1) - i);
	}
	
	public static Color getColor(List<Color> point) {
		return point.get(0);
	}
	
	public Color getPlayer1(){
		return this.getState().player1;
	}
	
	public Color getPlayer2(){
		return this.getState().player2;
	}
	
		
	public List<Color> getPoint(int point){
		return this.getState().points.get(point - 1);
	}
	
	public List<List<Color>> getPoints(){
		return this.getState().points;
	}
	
	public List<Color> getPlayer1Rail(){
		return this.getState().rails.get(this.getState().player1);
	}
	
	public List<Color> getPlayer2Rail(){
		return this.getState().rails.get(this.getState().player2);
	}
	
	public List<Color> getPlayer1Home(){
		return this.getState().homes.get(this.getState().player1);
	}
	
	public List<Color> getPlayer2Home(){
		return this.getState().homes.get(this.getState().player2);
	}
	
	// actually rolls the dice
	public List<Integer> rolledDice() {
		this.dice = new LinkedList<Integer>(); 
		dice1 = StdRandom.uniform(1, 7);
		dice2 = StdRandom.uniform(1, 7);
		this.dice.add(dice1);
		this.dice.add(dice2);
		rolled = true;
		return this.dice;
	}

	// getters which i know we will need
	public int getDice1(List<Integer> dice) {
		return dice.get(0);
	}

	public int getDice2(List<Integer> dice) {
		return dice.get(1);
	}

	// checks for doubles
	public boolean doubles(List<Integer> dice){
		if (dice.get(0) == dice.get(1)){
			return true;
		} else {
		return false;
		}
	}

	public String toString() {
		return this.getState().toString();
	}

	public void undoState() {
		// simple undo, works by removing the "current" state
		if (this.state.size() > 1) {
			this.state.remove(this.state.size() - 1);
		}
	}
	
	public Color getCurrentPlayer() {
		return this.getState().currentPlayer;
	}

}
