package jbackgammon;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

public class Backgammon {
	
	//COLORS!!!!!
	private static final Color BROWN = new Color(97, 68, 14);
	private static final Color LIGHT_BROWN = new Color(181, 139, 62);
	private static final Color DARK_BROWN = new Color(115, 87, 36);
	private static final Color BLACK = StdDraw.BLACK;
	private static final Color BACKGROUND = new Color(74, 51, 8);

	// base circle size
	private static final int baseUnit = 40;
	private static final double WIDTH = 15*baseUnit;
	private static final double HEIGHT = 12*baseUnit;

	private BackgammonModel model;
	private boolean drawPiece;
	private List<Color> currentPoint;

	public static void main(String[] args) {
		// choose rules

		// roll for first

		// initialize model and play
		new Backgammon().run();
	}

	public Backgammon() {
		this(StdDraw.BLACK);
	}

	public Backgammon(Color startingPlayer) {
		this.model = new BackgammonModel(StdDraw.BLACK, StdDraw.WHITE);
		this.drawPiece = false;
	}

	public void draw() {
		// draw board
		drawBoard();
		// draw pieces
		int x = 0;
		int y = 0;
		for (int i = 1; i <= model.getPoints().size(); i++) {
			drawPoint(i);
		}

		// draw rails
		x = 2 * baseUnit;
		y = 11 * baseUnit;
		StdDraw.text(baseUnit, 10.5 * baseUnit, "Rail");
		StdDraw.setPenColor(model.getPlayer1());
		for (int i = 0; i < model.getPlayer1Rail().size(); i++) {
			StdDraw.filledCircle(x + 0.5 * baseUnit, y, 0.5 * baseUnit);
			x += baseUnit;
		}
		// reset
		x = 2 * baseUnit;
		y = 10 * baseUnit;
		StdDraw.setPenColor(model.getPlayer2());
		for (int i = 0; i < model.getPlayer2Rail().size(); i++) {
			StdDraw.filledCircle(x + 0.5 * baseUnit, y, 0.5 * baseUnit);
			x += baseUnit;
		}

		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.text((28-13) * baseUnit, 12.5 * baseUnit, "|");
		
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.text(10 * baseUnit, 10.5 * baseUnit, "Current Player");
		StdDraw.setPenColor(model.getCurrentPlayer());
		StdDraw.filledCircle(12.5 * baseUnit, 10.5 * baseUnit, 0.5 * baseUnit);

		// Roll dice button. Does not do anything yet. Its just there as a
		// reminder.
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(18 * baseUnit, 10.5 * baseUnit, baseUnit,
				0.5 * baseUnit);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(18 * baseUnit, 10.5 * baseUnit, "Roll Dice");
		
		// Undo button
		StdDraw.setPenColor(StdDraw.PINK);
		StdDraw.filledRectangle(21 * baseUnit, 10.5 * baseUnit, baseUnit,
				0.5 * baseUnit);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(21 * baseUnit, 10.5 * baseUnit, "Undo");
		// draw moving piece under mouse
		drawCurrentPiece();

		StdOut.println(getPointFromPos(StdDraw.mouseX(), StdDraw.mouseY()));
		
		StdDraw.show(40);
	}

	private void drawCurrentPiece() {
		if (drawPiece) {
			StdDraw.setPenColor(model.getCurrentPlayer());
			StdDraw.filledCircle(StdDraw.mouseX(), StdDraw.mouseY(),
					0.6 * baseUnit);
		}
	}

	private void drawBoard() {
		// Background
		StdDraw.clear(BLACK);
		StdDraw.setPenColor(BACKGROUND);
//		StdDraw.filledRectangle(model.getPoints().size() * baseUnit / 2,
//				12 * baseUnit / 2, model.getPoints().size() * baseUnit / 2,
//				12 * baseUnit / 2);
		StdDraw.filledRectangle(WIDTH / 2, HEIGHT / 2, WIDTH / 2, HEIGHT / 2);

	}

	public void drawPoint(int i) {
		double x = (i + 0.5) * baseUnit;
		double y = 0;
		// draw spike
		double[] xi = { baseUnit * i,0.5 * (baseUnit * i + baseUnit * (i + 1)), baseUnit * (i + 1) };
		double[] yi = { 0, 5 * baseUnit, 0 };

		if (i > 12) {
			// flip xi and yi by subtracting
 			for (int n=0; n<xi.length; n++) {
				xi[n] = (2 * WIDTH) - xi[n] - 3*baseUnit;
				yi[n] = HEIGHT - yi[n];
			}
			x = (2 * WIDTH) - x - 3*baseUnit;
			y = HEIGHT - y;
		}
		// add space for rail
		if (i > 12) {
			if (i > 18) {
				x -= baseUnit;
	 			for (int n=0; n<xi.length; n++) {
					xi[n] -= baseUnit;
				}
			}
		} else {
			if (i > 6) {
				x += baseUnit;
	 			for (int n=0; n<xi.length; n++) {
					xi[n] += baseUnit;
				}
			}
		}
		
		StdDraw.setPenColor((i % 2 == 0 ? DARK_BROWN : LIGHT_BROWN));
		StdDraw.filledPolygon(xi, yi);
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.text(x, y, ""+i);
		
		// draw stack of pieces
		for (int c = 0; c < model.getPoint(i).size(); c++) {
			if (i > 12) {
				StdDraw.setPenColor(model.getColor(model.getPoint(i)));
				StdDraw.filledCircle(x, y - 0.5 * baseUnit, 0.5 * baseUnit);
				// move up one
				y -= baseUnit;
			} else {
				StdDraw.setPenColor(model.getColor(model.getPoint(i)));
				StdDraw.filledCircle(x, y + 0.5 * baseUnit, 0.5 * baseUnit);
				// move up one
				y += baseUnit;
			}
		}
	}
	
	public int getPointFromPos(Double x, Double y) {
		int point;
		if (y > HEIGHT/2) {
			// flip logic
			point = 24 - (int) (x / baseUnit);
			point += 2;

			if (point > 18) {
				point--;
			}
		} else {
			point = (int) (x / baseUnit);
			if (point > 6) {
				point --;
			}
		}
		return point;
	}
	
	// returns array of Double [x, y]
	public Double[] getMouseUp() {
		drawPiece = true;
		while (!StdDraw.mousePressed()) {
			// Wait for mouse press
			draw();
		}
		drawPiece = false;
		while (StdDraw.mousePressed()) {
			// Wait for mouse release
			// We don't support click-drag mechanic
		}
		return new Double[] {StdDraw.mouseX(), StdDraw.mouseY()};
	}

	/** Plays the game. */
	public void run() {
		// only do this once
		StdDraw.setCanvasSize(model.getPoints().size() * baseUnit, 12 * baseUnit);
		StdDraw.setXscale(0, model.getPoints().size() * baseUnit);
		StdDraw.setYscale(0, 12 * baseUnit);

		while (true) {
			while (!StdDraw.mousePressed()) {
				// Wait for mouse press
				draw();
			}
			while (StdDraw.mousePressed()) {
				// Wait for mouse release
				// We don't support click-drag mechanic
			}
			
			if (20*baseUnit <= StdDraw.mouseX() && StdDraw.mouseX() <= 22* baseUnit
					&& 10*baseUnit <= StdDraw.mouseY() && 11*baseUnit <= StdDraw.mouseY()) {
				// if they click on the undo button
				model.undoState();
				StdOut.println("undo clicked");
//					model.setState(model.getPreviousState());
//					continue;
			} else {
				// clicked on a rail
				if (9.5*baseUnit <= StdDraw.mouseY() && StdDraw.mouseY() < 10.5*baseUnit) {
					// player 2 rail
					Double[] mousePos = getMouseUp();
					model.enterFromRail(model.getPlayer2(), (int) (mousePos[0] / baseUnit) + 1);
				} else if (10.5*baseUnit <= StdDraw.mouseY() && StdDraw.mouseY() <= 11.5*baseUnit) {
					// player 1 rail
					Double[] mousePos = getMouseUp();
					model.enterFromRail(model.getPlayer1(), (int) (mousePos[0] / baseUnit) + 1);
				} else {
					// clicked on a point
					int sourcePoint = getPointFromPos(StdDraw.mouseX(), StdDraw.mouseY());
					Double[] mousePos = getMouseUp();
					// click another point, or home?
					// if (baseUnit <= mousePos[0] && mousePos[0] <= 23*baseUnit) {
					if (true) {
						// clicked another point, so move
						model.move(sourcePoint, getPointFromPos(mousePos[0], mousePos[1]));
					} else {
						// clicked a home, so bear off
						model.bearOff(model.getCurrentPlayer(), sourcePoint);
					}
				}
			// } else if (...) { // clicked on a point
			// } else if (...) { // clicked on a home
				// they clicked a point/home/rail
			}

			model.nextTurn();
			
			StdOut.println(model.getState());
		}
	}

}
