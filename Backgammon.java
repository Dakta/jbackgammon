package jbackgammon;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

import klondikeSolitaire.Deck;
import edu.princeton.cs.introcs.StdDraw;

public class Backgammon {
	
	//COLORS!!!!!
	private static final Color BROWN = new Color(97, 68, 14);
	private static final Color LIGHT_BROWN = new Color(181, 139, 62);
	private static final Color DARK_BROWN = new Color(115, 87, 36);
	private static final Color BLACK = StdDraw.BLACK;
	private static final Color BACKGROUND = new Color(74, 51, 8);

	// base circle size
	private static final int baseUnit = 40;

	private BackgammonModel model;
	private int currentPlayer;
	private boolean waitingForSource;
	private int currentPoint;

	public static void main(String[] args) {
		// choose rules

		// roll for first

		// initialize model and play
		new Backgammon().run();
	}

	public Backgammon() {
		this(BackgammonModel.black);
	}

	public Backgammon(int startingPlayer) {
		this.model = new BackgammonModel();
		this.currentPlayer = startingPlayer;
		this.waitingForSource = true;
		this.currentPoint = 0;

	}

	public void draw() {
		// draw board
		drawBoard();
		// draw pieces
		int x = 0;
		int y = 0;
		for (int i = 0; i < model.count.length; i++) {
			// draw spike
			double[] xi = { baseUnit * i,
					0.5 * (baseUnit * i + baseUnit * (i + 1)),
					baseUnit * (i + 1) };
			double[] yi = { 0, 5 * baseUnit, 0 };
			StdDraw.setPenColor((i % 2 == 0 ? DARK_BROWN : LIGHT_BROWN));
			StdDraw.filledPolygon(xi, yi);

			// draw stack of pieces
			y = 0;
			for (int c = 0; c < model.count[i]; c++) {
				StdDraw.setPenColor((model.color[i] == model.white ? StdDraw.WHITE
						: StdDraw.BLACK));
				StdDraw.filledCircle(x + 0.5 * baseUnit, y + 0.5 * baseUnit,
						0.5 * baseUnit);
				// move up one
				y += baseUnit;
			}
			// move over one
			x += baseUnit;
		}

		// draw rails
		x = 2 * baseUnit;
		y = 11 * baseUnit;
		StdDraw.text(baseUnit, 10.5 * baseUnit, "Rail");
		StdDraw.setPenColor(StdDraw.WHITE);
		for (int i = 0; i < model.white_rail; i++) {
			StdDraw.filledCircle(x + 0.5 * baseUnit, y, 0.5 * baseUnit);
			x += baseUnit;
		}
		// reset
		x = 2 * baseUnit;
		y = 10 * baseUnit;
		StdDraw.setPenColor(StdDraw.BLACK);
		for (int i = 0; i < model.black_rail; i++) {
			StdDraw.filledCircle(x + 0.5 * baseUnit, y, 0.5 * baseUnit);
			x += baseUnit;
		}

		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.text(10 * baseUnit, 10.5 * baseUnit, "Current Player");
		StdDraw.setPenColor(currentPlayer == model.black ? StdDraw.BLACK
				: StdDraw.WHITE);
		StdDraw.filledCircle(12.5 * baseUnit, 10.5 * baseUnit, 0.5 * baseUnit);

		// Roll dice button. Does not do anything yet. Its just there as a
		// reminder.
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(20 * baseUnit, 10.5 * baseUnit, baseUnit,
				0.5 * baseUnit);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(20 * baseUnit, 10.5 * baseUnit, "Roll Dice");
		// draw moving piece under mouse
		drawCurrentPiece();

		StdDraw.show(40);
	}

	private void drawCurrentPiece() {
		if (!waitingForSource) {
			StdDraw.setPenColor(currentPlayer == model.black ? StdDraw.BLACK
					: StdDraw.WHITE);
			StdDraw.filledCircle(StdDraw.mouseX(), StdDraw.mouseY(),
					0.6 * baseUnit);
		}
	}

	private void drawBoard() {
		// Background
		StdDraw.clear(BLACK);
		StdDraw.setPenColor(BACKGROUND);
		StdDraw.filledRectangle(model.count.length * baseUnit / 2,
				12 * baseUnit / 2, model.count.length * baseUnit / 2,
				12 * baseUnit / 2);

	}

	/** Returns the point under the mouse. */
	public int mousePoint() {
		// int result = (int) Math.round(StdDraw.mouseX() / model.count.length /
		// 2 + 1);
		int result = (int) StdDraw.mouseX() / baseUnit + 1;
		return result;
	}

	/** Plays the game. */
	public void run() {
		// only do this once
		StdDraw.setCanvasSize(model.count.length * baseUnit, 12 * baseUnit);
		StdDraw.setXscale(0, model.count.length * baseUnit);
		StdDraw.setYscale(0, 12 * baseUnit);

		while (true) {
			while (!StdDraw.mousePressed()) {
				// Wait for mouse press
				draw();
			}
			if (waitingForSource) {
				currentPoint = mousePoint();
				waitingForSource = false;
			} else { // Waiting for destination
				waitingForSource = true;
				model.move(currentPoint - 1, mousePoint() - 1);
				currentPlayer = currentPlayer == BackgammonModel.black ? BackgammonModel.white
						: BackgammonModel.black;
			}
			while (StdDraw.mousePressed()) {
				// Wait for mouse release
				// We don't support click-drag mechanic
			}
		}
	}

}
