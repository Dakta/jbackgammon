package jbackgammon;

import java.awt.Color;

import klondikeSolitaire.Deck;
import edu.princeton.cs.introcs.StdDraw;

public class Backgammon {

	public static void main(String[] args) {
		// choose rules
		
		// roll for first
		
		// initialize model and play
		 new Backgammon().run();
	}
	
	private BackgammonModel model;
	private int currentPlayer;
	private boolean waitingForSource;
	private int currentPoint;
	
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
		for (int i=0; i<model.count.length; i++) {
			// draw spike
			double[] xi = {baseUnit*i, 0.5*(baseUnit*i+baseUnit*(i+1)), baseUnit*(i+1)};
			double[] yi = {0, 5*baseUnit, 0};
			StdDraw.setPenColor((i%2==0 ? DARK_BROWN : LIGHT_BROWN));
			StdDraw.filledPolygon(xi, yi);
			
			// draw stack of pieces
			y = 0;
			for (int c=0; c<model.count[i]; c++) {
				StdDraw.setPenColor((model.color[i] == model.white ? StdDraw.WHITE : StdDraw.BLACK));
				StdDraw.filledCircle(x+0.5*baseUnit, y+0.5*baseUnit, 0.5*baseUnit);
				// move up one
				y += baseUnit;
			}
			// move over one
			x += baseUnit;
		}

		// draw rails
		x = 2*baseUnit;
		y = 11*baseUnit;
		StdDraw.text(baseUnit, 10.5*baseUnit, "Rail");
		StdDraw.setPenColor(StdDraw.WHITE );
		for (int i=0; i<model.white_rail; i++) {
			StdDraw.filledCircle(x+0.5*baseUnit, y, 0.5*baseUnit);
			x += baseUnit;
		}
		// reset
		x = 2*baseUnit;
		y = 10*baseUnit;
		StdDraw.setPenColor(StdDraw.BLACK );
		for (int i=0; i<model.black_rail; i++) {
			StdDraw.filledCircle(x+0.5*baseUnit, y, 0.5*baseUnit);
			x += baseUnit;
		}
		
		// draw moving piece under mouse
		StdDraw.show(40);
	}
	
	private static final Color BROWN = new Color(97, 68, 14);
	private static final Color LIGHT_BROWN = new Color(181, 139, 62);
	private static final Color DARK_BROWN = new Color(115, 87, 36);

	private static final Color BLACK = StdDraw.BLACK;
	private static final Color BACKGROUND = new Color(74, 51, 8);

	// base circle size
	private static final int baseUnit = 40;

	public void drawBoard() {
		// Background
		StdDraw.clear(BLACK);
		StdDraw.setPenColor(BACKGROUND);
		StdDraw.filledRectangle(model.count.length*baseUnit/2, 12*baseUnit/2, model.count.length*baseUnit/2, 12*baseUnit/2);
		
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
	
	/** Returns the point under the mouse. */
	public int mousePoint() {
//		int result = (int) Math.round(StdDraw.mouseX() / model.count.length / 2 + 1);
		int result = (int) StdDraw.mouseX() / baseUnit + 1;
		return result;
	}
	
	/** Plays the game. */
	public void run() {
		// only do this once
		StdDraw.setCanvasSize(model.count.length*baseUnit, 12*baseUnit);
		StdDraw.setXscale(0, model.count.length*baseUnit);
		StdDraw.setYscale(0, 12*baseUnit);
		
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
				model.move(currentPoint-1, mousePoint()-1);
				currentPlayer = currentPlayer == BackgammonModel.black ? BackgammonModel.white : BackgammonModel.black;
			}
			while (StdDraw.mousePressed()) {
				// Wait for mouse release
				// We don't support click-drag mechanic
			}
		}
	}

}
