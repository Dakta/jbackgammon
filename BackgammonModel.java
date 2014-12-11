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
		
		List<Integer> movesLeft = rollDice();
		
		// update the game state history
		this.state.add(new BackgammonState(player1, player2, currentPlayer, movesLeft, points, rails, homes));
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
		if (this.getPoint(f).size() == 0) {
			// no piece to move
			StdOut.println("no piece to move");
			return;
		}

		if ((getCurrentPlayer() == getPlayer1()
				&& f < t)
			|| (getCurrentPlayer() == getPlayer2()
					&& t < f)) {
			// correct direction
			if (this.getState().movesLeft.contains(Math.abs(f - t))) {
				// correct distance
				BackgammonState newState = new BackgammonState(this.getState());
				// "use" the move
				newState.movesLeft.remove(new Integer(Math.abs(f - t)));
				// move the piece
				if (newState.move(newState.points.get(f - 1), newState.points.get(t - 1))) {
					// if the move was valid
					// push the undo state
					this.state.add(newState);
				}
			}
		}
	}

	public void enterFromRail(Color player, Integer dest) {
		if ((player == this.getPlayer1()
				&& dest < 7)
			|| (player == this.getPlayer2()
				&& dest > 18)) {
			// make sure it's in the correct quadrant
			Integer dist = 0; // if this doesn't get changed below, something seriously bad is hapened
			if (player == this.getPlayer1()) {
				dist = dest;
			} else if (player == this.getPlayer2()) {
				dist = 25 - dest;
			}
			if (this.getState().movesLeft.contains(dist)) {
				// make sure they can move there
				BackgammonState newState = new BackgammonState(this.getState());
				// "use" the move
				newState.movesLeft.remove(dist);
				// move the piece
				if (newState.move(newState.rails.get(player), newState.points.get(dest - 1))) {
					// if the move was valid
					// push the undo state
					this.state.add(newState);
				}
			}
		}
	}
	
	public boolean canBearOff(Color player){
		int count = 0;
		if (player == this.getPlayer2()){
			for (int i = 1; i < 7; i++){
				if (this.getPoint(i).size() > 0) {
					count += (BackgammonModel.getColor(this.getPoint(i)) == player) ? this.getPoint(i).size() : 0;
				}
			}
			count += this.getPlayer2Home().size();
				
		} else if (player == this.getPlayer1()){
			for (int i = 19; i < 25; i++){
				if (this.getPoint(i).size() > 0) {
					count += (BackgammonModel.getColor(this.getPoint(i)) == player) ? this.getPoint(i).size() : 0;
				}
			}
			count += this.getPlayer1Home().size();
		}		
		return (count == 15);
	}
	
	public void bearOff(Color player, int source) {
		if (canBearOff(player)){
			Integer dist = 0; // if this doesn't get changed below, something seriously bad is hapened
			if (player == this.getPlayer1()) {
				dist = 25 - source;
			} else if (player == this.getPlayer2()) {
				dist = source;
			}
			if (this.getState().movesLeft.contains(dist)) {
				BackgammonState newState = new BackgammonState(this.getState());
				// "use" the move
				newState.movesLeft.remove(dist);
				// move the piece
				newState.move(newState.points.get(source - 1), newState.homes.get(player));
				// push the undo state
				this.state.add(newState);
			}
		} else {
		
		}
	}

	public void nextTurn() {
		BackgammonState newState = new BackgammonState(this.getState());
		// switch player
		newState.currentPlayer = this.getCurrentPlayer() == this.getPlayer1() ? this.getPlayer2() : this.getPlayer1();
		// roll dice
		newState.movesLeft = rollDice();
		// go!
		this.state.add(newState);
	}

	// gets current state
	public BackgammonState getState(){
		return this.state.get(this.state.size() - 1);
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

	public List<Color> getRail(Color player) {
		return this.getState().rails.get(player);
	}

	public List<Color> getPlayer1Rail(){
		return this.getRail(this.getState().player1);
	}
	
	public List<Color> getPlayer2Rail(){
		return this.getRail(this.getState().player2);
	}
	
	public List<Color> getPlayer1Home(){
		return this.getState().homes.get(this.getState().player1);
	}
	
	public List<Color> getPlayer2Home(){
		return this.getState().homes.get(this.getState().player2);
	}
	
	
	// rolls some dice to get moves
	public List<Integer> rollDice() {
		// roll some random-ass numbers
		List<Integer> moves = new LinkedList<Integer>();
		
		moves.add(StdRandom.uniform(1, 7));
		moves.add(StdRandom.uniform(1, 7));
		if (moves.get(0) == moves.get(1)) {
			// doubles, so double it
			moves.add(moves.get(0));
			moves.add(moves.get(0));
		}
		return moves;
	}

	// getters which i know we will need
//	public Integer[] getDice() {
//		return this.getState().dice;
//	}
	

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

//	public int getMovesLeft() {
//		return this.getState().movesLeft;
//	}

}
