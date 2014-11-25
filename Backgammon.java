package jbackgammon;

import java.awt.Color;

import klondikeSolitaire.Deck;
import edu.princeton.cs.introcs.StdDraw;

public class Backgammon {

	public static void main(String[] args) {
		// choose rules
		
		// roll for first
		
		// initialize model and play
		 new Backgammon().draw();
	}
	
	private BackgammonModel model;
	private java.awt.Color currentPlayer;
	
	public Backgammon() {
		this(StdDraw.BLACK);
	}
	
	public Backgammon(java.awt.Color startingPlayer) {
		this.model = new BackgammonModel();
		this.currentPlayer = startingPlayer;
	}
	
	public void draw() {
		// draw board
		drawBoard();
		// draw pieces
		
		// draw moving piece under mouse if needed
	}
	
	private static final Color BROWN = new Color(97, 68, 14);
	private static final Color LIGHT_BROWN = new Color(181, 139, 62);
	private static final Color DARK_BROWN = new Color(115, 87, 36);

	private static final Color BLACK = StdDraw.BLACK;
	private static final Color BACKGROUND = new Color(74, 51, 8);

	// Quadrant 1
	private static final double[] quad1PointX = { 3.1, 3.325, 3.6 };
	private static final double[] quad1PointY = { 3.99, 2.5, 3.99 };

	// Quadrant 2
	private static final double[] quad2PointX = { -0.1, 0.125, 0.4 };
	private static final double[] quad2PointY = { 3.99, 2.5, 3.99 };

	// Quadrant 3
	private static final double[] quad3PointX = { -0.1, 0.125, 0.4 };
	private static final double[] quad3PointY = { 0.01, 1.5, 0.01 };

	// Quadrant 4
	private static final double[] quad4PointX = { 3.1, 3.325, 3.6 };
	private static final double[] quad4PointY = { 0.01, 1.5, 0.01 };

	// base circle size
	private static final int baseUnit = 40;

	public static void drawBoard() {
		// Background
		StdDraw.setCanvasSize(16*baseUnit, 12*baseUnit);
		StdDraw.setXscale(0, 16*baseUnit);
		StdDraw.setYscale(0, 12*baseUnit);
		StdDraw.clear(BLACK);
		StdDraw.setPenColor(BACKGROUND);
//		StdDraw.filledRectangle(4*baseUnit, 5*baseUnit, 3*baseUnit, 4*baseUnit);

		StdDraw.filledRectangle(16*baseUnit/2, 12*baseUnit/2, 16*baseUnit/2, 12*baseUnit/2);
		
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledCircle(0, 0, 0.5*baseUnit);
		
//		drawSpike(1, LIGHT_BROWN, DARK_BROWN, quad1PointX, quad1PointY);
//		drawSpike(1, LIGHT_BROWN, DARK_BROWN, quad2PointX, quad2PointY);
//		drawSpike(0, LIGHT_BROWN, DARK_BROWN, quad3PointX, quad3PointY);
//		drawSpike(0, LIGHT_BROWN, DARK_BROWN, quad4PointX, quad4PointY);
		// Outlines
		StdDraw.setPenColor(BLACK);
//		StdDraw.rectangle(1.398, 2, 1.5, 2);
//		StdDraw.rectangle(4.598, 2, 1.5, 2);

		for (int i=0; i<6; i++) {
			// draw top spike
			double[] x = {baseUnit*i, 0.5*(baseUnit*i+baseUnit*(i+1)), baseUnit*(i+1)};
			double[] y = {0, 5*baseUnit, 0};
			StdDraw.setPenColor((i%2==0 ? DARK_BROWN : LIGHT_BROWN));
			StdDraw.filledPolygon(x, y);
			
			// draw bottom spike
			double[] y2 = {12*baseUnit, (7*baseUnit), 12*baseUnit};
			StdDraw.setPenColor((i%2==0 ? LIGHT_BROWN : DARK_BROWN));
			StdDraw.filledPolygon(x, y2);
		}
		for (int i=0; i<10; i++) {
			StdDraw.filledCircle(i*2.5*baseUnit, 200, baseUnit);
		}
		
		StdDraw.line(300.0, 0.0, 300.0, 400.0);
	}

	public static void drawSpike(int start, Color color1, Color color2,
			double[] x, double[] y) {
		int count = 0;
//		if (start == 1){
//			count = -1;
//		}
		while (count < 3) {
			if (start == 0) {
				StdDraw.setPenColor(color1);
				StdDraw.filledPolygon(x, y);
				for (int i = 0; i < x.length; i++) {
					x[i] = x[i] + 0.5;
				}
				start = 1;
			}
			if (start == 1) {
				StdDraw.setPenColor(color2);
				StdDraw.filledPolygon(x, y);
				for (int i = 0; i < x.length; i++) {
					x[i] = x[i] + 0.5;
				}
				start = 0;
			}
			count++;
		}
	}
	
	/** Plays the game. */
	public void run() {
//		Deck source = null;
		while (true) {
			draw();
			while (!StdDraw.mousePressed()) {
				// Wait for mouse press
			}
//			if (waitingForSource) {
//				if (StdDraw.mouseY() > 0.8) { // Clicked above
//					Deck discardPile = model.getDiscardPile();
//					if (mouseColumn() == 1) {
//						model.drawNextCard();
//					} else if ((mouseColumn() == 2) && (discardPile.size() > 0)) {
//						source = discardPile;
//						waitingForSource = false;
//					}
//				} else { // Clicked on tableau
//					source = model.getTableau(mouseColumn() - 1);
//					if (source.size() > 0) {
//						waitingForSource = false;
//					}
//				}
//			} else { // Waiting for destination
//				waitingForSource = true;
//				if (StdDraw.mouseY() > 0.8) { // Clicked above
//					model.moveToFoundation(source, mouseColumn() - 4);
//				} else { // Clicked on tableau
//					model.moveToTableau(source, mouseColumn() - 1);
//				}
//			}
//			while (StdDraw.mousePressed()) {
//				// Wait for mouse release
//			}
		}
	}

}
