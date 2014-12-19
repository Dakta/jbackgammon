package jbackgammon;

import java.awt.Color;

import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

public class Backgammon {
	
	//COLORS!!!!!
	private static final Color BROWN = new Color(97, 68, 14);
	private static final Color LIGHT_BROWN = new Color(181, 139, 62);
	private static final Color DARK_BROWN = new Color(115, 87, 36);
	private static final Color BLACK = StdDraw.BLACK;
	private static final Color BACKGROUND = new Color(74, 51, 8);
	private static final Color DARK_GREEN = new Color(41,107,37);
	// base circle size
	private static final int baseUnit = 40;
	private static final double WIDTH = 15*baseUnit;
	private static final double HEIGHT = 12*baseUnit;

	private BackgammonModel model;
	private boolean drawPiece;

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
		double x = 0;
		double y = 0;
		for (int i = 1; i <= model.getPoints().size(); i++) {
			drawPoint(i);
		}

		// draw rails
		// player 2 on top, player 1 on bottom
		x = WIDTH/2;
		y = HEIGHT/2;
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.text(x, y, "Rail");
		StdDraw.setPenColor(model.getPlayer1());
		for (int i = 0; i < model.getPlayer1Rail().size(); i++) {
			y -= baseUnit;
			StdDraw.filledCircle(x, y, 0.5 * baseUnit);
		}
		// reset
		y = HEIGHT/2;
		StdDraw.setPenColor(model.getPlayer2());
		for (int i = 0; i < model.getPlayer2Rail().size(); i++) {
			y += baseUnit;
			StdDraw.filledCircle(x, y, 0.5 * baseUnit);
		}

		// draw homes
		// player 2 on top
		x = 0.5*baseUnit;
		y = HEIGHT; // top down
		StdDraw.text(x, y, "B");
		StdDraw.text(x, 0, "W");
		StdDraw.setPenColor(model.getPlayer1());
		for (int i = 0; i < model.getPlayer1Home().size(); i++) {
			y -= 0.15*baseUnit;
			StdDraw.filledRectangle(x, y, 0.5 * baseUnit, 0.1 * baseUnit);
		}
		// player 1 on bottom
		y = 0; // bottom up
		StdDraw.setPenColor(model.getPlayer2());
		for (int i = 0; i < model.getPlayer2Home().size(); i++) {
			y += 0.15*baseUnit;
			StdDraw.filledRectangle(x, y, 0.5 * baseUnit, 0.1 * baseUnit);
		}

		
		// current player indicator
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.text(18.5 * baseUnit, 9 * baseUnit, "Current Player");
		StdDraw.setPenColor(model.getCurrentPlayer());
		StdDraw.filledCircle(21 * baseUnit, 9 * baseUnit, 0.5 * baseUnit);

		// Roll dice button. Does not do anything yet. Its just there as a
		// reminder.
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(18 * baseUnit, 10.5 * baseUnit, baseUnit,
				0.5 * baseUnit);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(18 * baseUnit, 10.5 * baseUnit, "Skip Turn");
		
		// Undo button
		StdDraw.setPenColor(StdDraw.PINK);
		StdDraw.filledRectangle(21 * baseUnit, 10.5 * baseUnit, baseUnit,
				0.5 * baseUnit);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(21 * baseUnit, 10.5 * baseUnit, "Undo");
		
		// dice
		drawDice();
		
		// draw moving piece under mouse
		drawCurrentPiece();

		
		
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
		StdDraw.clear(DARK_GREEN);
		StdDraw.setPenColor(BACKGROUND);
//		StdDraw.filledRectangle(model.getPoints().size() * baseUnit / 2,
//				12 * baseUnit / 2, model.getPoints().size() * baseUnit / 2,
//				12 * baseUnit / 2);
		StdDraw.filledRectangle(WIDTH / 2, HEIGHT / 2, WIDTH / 2, HEIGHT / 2);

	}
	
	public void drawDie(double centerX, int centerY, int value){
		
		double pip = 0.125 * baseUnit;
		
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledSquare(centerX * baseUnit, centerY * baseUnit, 0.575*baseUnit);
		StdDraw.setPenColor(BLACK);
				
		if (value == 1){
			StdDraw.filledCircle(centerX * baseUnit, centerY * baseUnit, pip);	
		} else if (value == 2){
			StdDraw.filledCircle((centerX - 0.3) * baseUnit, (centerY + 0.3) * baseUnit, pip);
			StdDraw.filledCircle((centerX + 0.3) * baseUnit, (centerY - 0.3) * baseUnit, pip);
		} else if (value == 3){
			StdDraw.filledCircle((centerX - 0.3) * baseUnit, (centerY + 0.3) * baseUnit, pip);
			StdDraw.filledCircle((centerX + 0.3) * baseUnit, (centerY - 0.3) * baseUnit, pip);
			StdDraw.filledCircle(centerX * baseUnit, centerY * baseUnit, pip);
		} else if (value == 4){
			StdDraw.filledCircle((centerX - 0.3) * baseUnit, (centerY + 0.3) * baseUnit, pip);
			StdDraw.filledCircle((centerX + 0.3) * baseUnit, (centerY - 0.3) * baseUnit, pip);
			StdDraw.filledCircle((centerX - 0.3) * baseUnit, (centerY - 0.3) * baseUnit, pip);
			StdDraw.filledCircle((centerX + 0.3) * baseUnit, (centerY + 0.3) * baseUnit, pip);
		} else if (value == 5){
			StdDraw.filledCircle((centerX - 0.3) * baseUnit, (centerY + 0.3) * baseUnit, pip);
			StdDraw.filledCircle((centerX + 0.3) * baseUnit, (centerY - 0.3) * baseUnit, pip);
			StdDraw.filledCircle((centerX - 0.3) * baseUnit, (centerY - 0.3) * baseUnit, pip);
			StdDraw.filledCircle((centerX + 0.3) * baseUnit, (centerY + 0.3) * baseUnit, pip);
			StdDraw.filledCircle(centerX * baseUnit, centerY * baseUnit, pip);
		} else if (value == 6){
			StdDraw.filledCircle((centerX - 0.3) * baseUnit, (centerY + 0.3) * baseUnit, pip);
			StdDraw.filledCircle((centerX + 0.3) * baseUnit, (centerY - 0.3) * baseUnit, pip);
			StdDraw.filledCircle((centerX - 0.3) * baseUnit, (centerY - 0.3) * baseUnit, pip);
			StdDraw.filledCircle((centerX + 0.3) * baseUnit, (centerY + 0.3) * baseUnit, pip);
			StdDraw.filledCircle((centerX + 0.3) * baseUnit, centerY * baseUnit, pip);
			StdDraw.filledCircle((centerX - 0.3) * baseUnit, centerY * baseUnit, pip);
		}
	}
	
	public void drawDice() {
		double x = 18;
		for (int i=0; i<model.getState().movesLeft.size(); i++) {
			this.drawDie(x, 5, model.getState().movesLeft.get(i));
			x += 1.5;
		}
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
					&& 9 * baseUnit <= StdDraw.mouseY() && 10 * baseUnit <= StdDraw.mouseY()) {
				// if they click on the undo button
				model.undoState();
				continue;

			}
			
			if (17*baseUnit <= StdDraw.mouseX() && StdDraw.mouseX() <= 19 *baseUnit
						&& 9 * baseUnit <= StdDraw.mouseY() && 10 * baseUnit <= StdDraw.mouseY()) {
				// if they click the dice button
				model.nextTurn();
				continue;
				// keep track of number of moves left for current player
				// decrement when player moves
				// call model.nextTurn() when it reaches 0
			}
				
			
			
			if (WIDTH/2 - 0.5*baseUnit < StdDraw.mouseX() && StdDraw.mouseX() < WIDTH/2 + 0.5*baseUnit) {
				// clicked on a rail
				Color player = StdDraw.RED;
				if (StdDraw.mouseY() < HEIGHT/2) {
					// player 1 rail
					player = model.getPlayer1();
				} else {
					// player 2 rail
					player = model.getPlayer2();
				}
				if (model.getRail(player).size() < 1) {
					// ignore click on empty rail
					continue;
				}
				Double[] mousePos = getMouseUp();
				model.enterFromRail(player, getPointFromPos(mousePos[0], mousePos[1]));
			} else if (StdDraw.mouseX() > baseUnit && StdDraw.mouseX() < 14 * baseUnit) {
				// make sure we don't have to enter
				if (model.getRail(model.getCurrentPlayer()).size() > 0) {
					continue;
				}
				// clicked on a point
				int sourcePoint = getPointFromPos(StdDraw.mouseX(), StdDraw.mouseY());
				if (model.getPoint(sourcePoint).size() < 1
						|| BackgammonModel.getColor(model.getPoint(sourcePoint)) != model.getCurrentPlayer()) {
					// try again
					continue;
				}
				while (true) {
					// get destination
					Double[] mousePos = getMouseUp();
					// either moving or bearing off
					if (mousePos[0] < baseUnit) { // if x < baseUnit, dest is a home
						// clicked on one of the homes (left edge)
						if (model.getCurrentPlayer() == model.getPlayer1()
								&& mousePos[1] > HEIGHT/2) {
							// player 1
							model.bearOff(model.getPlayer1(), sourcePoint);
							break;
						} else if (model.getCurrentPlayer() == model.getPlayer2()
									&& mousePos[1] < HEIGHT/2) {
							// player 2
							model.bearOff(model.getPlayer2(), sourcePoint);
							break;
						}
					} else {
						// clicked on another point
						model.move(sourcePoint, getPointFromPos(mousePos[0], mousePos[1]));
						break;
					}
				}
			}

//			model.nextTurn();
			if (model.getState().movesLeft.size() < 1) {
				model.nextTurn();
			}
			
//			StdOut.println(model.getState());
		}
	}

}
