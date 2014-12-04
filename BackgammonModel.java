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


	List<BackgammonState> state;

	// this allows us to get a player's home when we only know their color
	// without having to hard-code a variable for it
	
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

		// count = new int[24];
		// color = new int[24];
		//
		// for (int i = 0; i < 24; i++) {
		// count[i] = 0;
		// color[i] = empty;
		// }

		setColumn(points, 1, 2, player1);
		setColumn(points, 6, 5, player2);
		setColumn(points, 8, 3, player2);
		setColumn(points, 12, 5, player1);
		setColumn(points, 13, 5, player2);
		setColumn(points, 17, 3, player1);
		setColumn(points, 19, 5, player1);
		setColumn(points, 24, 2, player2);

		// update the game state history
		this.state.add(new BackgammonState(player1, player2, points, rails, homes));

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
//		this.move(this.getState().points.get(f - 1), this.getState().points.get(t - 1));
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
//		return this.player1;
		return this.getState().player1;
	}
	
	public Color getPlayer2(){
//		return this.player2;
		return this.getState().player2;
	}
	
	
//	public List<Color> getRail(Color player){
////		return this.rails.get(player);
//		return this.getState().rails.get(player);
//	}
//	
//	public List<Color> getHome(Color player){
////		return this.homes.get(player);
//		return this.getState().homes.get(player);
//	}
	
	public List<Color> getPoint(int point){
//		return this.points.get(point - 1 );
		return this.getState().points.get(point - 1);
	}
	
	public List<List<Color>> getPoints(){
//		return this.points;
		return this.getState().points;
	}
	
	public List<Color> getPlayer1Rail(){
		return this.getState().rails.get(this.getState().player1);
	}
	
	public List<Color> getPlayer2Rail(){
		return this.getState().rails.get(this.getState().player2);
	}
//
//	public List<Color> getPlayer1Home(){
//		return this.getState().homes.get(this.player1);
//	}
//	
//	public List<Color> getPlayer2Home(){
//		return this.getState().homes.get(this.player2);
//	}
	
	
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
		return this.getState().toString();
	}

	public void setState(BackgammonState previousState) {
		// unused?
	}

	public void undoState() {
		if (this.state.size() > 1) {
			this.state.remove(this.state.size() - 1);
		}
		// now update the model?
//		this.points = new LinkedList<List<Color>>();
//		for (List<Color> point: this.getState().points) {
//			this.points.add(new LinkedList<Color>(point));
//		}
//		this.rails = new HashMap<Color, List<Color>>();
//		for (Color rail: this.getState().rails.keySet()) {
//			this.rails.put(rail, new LinkedList<Color>(rails.get(rail)));
//		}
//		this.homes = new HashMap<Color, List<Color>>();
//		for (Color home: this.getState().homes.keySet()) {
//			this.homes.put(home, new LinkedList<Color>(homes.get(home)));
//		}
	}

}
