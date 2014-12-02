package jbackgammon;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.HashMap;

public class BackgammonState {
	private Color player1;
	private Color player2;

	List<List<Color>> points;
	// rails is a map where the key is a color, the value is a list
	Map<Color, List<Color>> rails;
	// same for homes
	Map<Color, List<Color>> homes;

	public BackgammonState(Color player1, Color player2, List<List<Color>> points, Map<Color, List<Color>> rails, Map<Color, List<Color>> homes) {
		this.player1 = player1;
		this.player2 = player2;

		this.points = new LinkedList<List<Color>>();
		for (List<Color> point: points) {
			this.points.add(new LinkedList<Color>(point));
		}
		this.rails = new HashMap<Color, List<Color>>();
		for (Color rail: rails.keySet()) {
			this.rails.put(rail, new LinkedList<Color>(rails.get(rail)));
		}
		this.homes = new HashMap<Color, List<Color>>();
		for (Color home: homes.keySet()) {
			this.homes.put(home, new LinkedList<Color>(homes.get(home)));
		}
	}

	public String toString() {
		String ret = "";
		// draw white home
		ret += "W ";
		for (int i = 0; i < this.homes.get(player1).size(); i++) {
			ret += "O";
		}
		ret += "\n";
		// draw half board
		for (int i = 0; i < this.points.size() / 2; i++) {
			ret += ((i + 1) / 10 < 1 ? " " : "") + (i + 1) + " ";
			for (int c = 0; c < this.points.get(i).size(); c++) {
				ret += (BackgammonModel.getColor(this.points.get(i)) == player1 ? "#" : "O");
			}
			ret += "\n";
		}
		// draw rails
		ret += "  ";
		for (int i = 0; i < this.rails.get(player2).size(); i++) {
			ret += "O";
		}
		ret += "\n";
		ret += "  ";
		for (int i = 0; i < this.rails.get(player1).size(); i++) {
			ret += "#";
		}
		ret += "\n";
		// draw half board
		for (int i = this.points.size() / 2; i < this.points.size(); i++) {
			ret += ((i + 1) / 10 < 1 ? " " : "") + (i + 1) + " ";
			for (int c = 0; c < this.points.get(i).size(); c++) {
				ret += (BackgammonModel.getColor(this.points.get(i)) == player1 ? "#" : "O");
			}
			ret += "\n";
		}

		// draw black home
		ret += "B ";
		for (int i = 0; i < this.homes.get(player2).size(); i++) {
			ret += "#";
		}
		ret += "\n";

		return ret;
	}

}
