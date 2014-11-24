package jbackgammon;

import java.awt.Color;

import edu.princeton.cs.introcs.StdDraw;

public class Drawing {

	private static final Color BACKGROUND = new Color(97, 68, 14);
	private static final Color BROWN = new Color(74, 51, 8);
	private static final Color LIGHT_BROWN = new Color(181, 139, 62);
	private static final Color BLACK = StdDraw.BLACK;
	private static final Color DARK_BROWN = new Color(115, 87, 36);

	// Quadrant 1

	// Light Brown Points
	private static final double[] lightQuad1PointX = { 3.6125, 3.85, 4.1 };
	private static final double[] lightQuad1PointY = { 3.99, 2.5, 3.99 };
	// Dark Brown Points
	private static final double[] darkQuad1PointX = { 3.1, 3.325, 3.6 };
	private static final double[] darkQuad1PointY = { 3.99, 2.5, 3.99 };

	// Quadrant 2

	// Light Brown Points
	private static final double[] lightQuad2PointX = { 0.4125, 0.65, 0.9 };
	private static final double[] lightQuad2PointY = { 3.99, 2.5, 3.99 };
	// Dark Brown Points
	private static final double[] darkQuad2PointX = { -0.1, 0.125, 0.4 };
	private static final double[] darkQuad2PointY = { 3.99, 2.5, 3.99 };

	// Quadrant 3

	// Light Brown Points
	private static final double[] lightQuad3PointX = { -0.1, 0.125, 0.4 };
	private static final double[] lightQuad3PointY = { 0.01, 1.5, 0.01 };
	// Dark Brown Points
	private static final double[] darkQuad3PointX = { 0.4125, 0.65, 0.9 };
	private static final double[] darkQuad3PointY = { 0.01, 1.5, 0.01 };

	// Quadrant 4

	// Light Brown Points
	private static final double[] lightQuad4PointX = { 3.1, 3.325, 3.6 };
	private static final double[] lightQuad4PointY = { 0.01, 1.5, 0.01 };
	// Dark Brown Points
	private static final double[] darkQuad4PointX = { 3.6125, 3.85, 4.1 };
	private static final double[] darkQuad4PointY = { 0.01, 1.5, 0.01 };

	public static void main(String[] args) {
		boardDraw();
	}

	public static void bordersAndBar() {
		// Squares
		StdDraw.setPenColor(BACKGROUND);
		StdDraw.filledRectangle(1.398, 2, 1.5, 2);
		StdDraw.filledRectangle(4.598, 2, 1.5, 2);
		// Outlines
		StdDraw.setPenColor(BLACK);
		StdDraw.rectangle(1.398, 2, 1.5, 2);
		StdDraw.rectangle(4.598, 2, 1.5, 2);

	}

	public static void boardDraw() {
		StdDraw.setCanvasSize(600, 400);
		StdDraw.setXscale(0, 6);
		StdDraw.setYscale(0, 4);
		StdDraw.clear(BROWN);
		bordersAndBar();
		
		spikeDraw(LIGHT_BROWN, lightQuad1PointX, lightQuad1PointY);
		spikeDraw(DARK_BROWN, darkQuad1PointX, darkQuad1PointY);
		
		spikeDraw(LIGHT_BROWN, lightQuad2PointX, lightQuad2PointY);
		spikeDraw(DARK_BROWN, darkQuad2PointX, darkQuad2PointY);
		
		spikeDraw(LIGHT_BROWN, lightQuad3PointX, lightQuad3PointY);
		spikeDraw(DARK_BROWN, darkQuad3PointX, darkQuad3PointY);
		
		spikeDraw(LIGHT_BROWN, lightQuad4PointX, lightQuad4PointY);
		spikeDraw(DARK_BROWN, darkQuad4PointX, darkQuad4PointY);
		
	}

	public static void spikeDraw(Color color, double[] x, double[] y) {
		for (int spike = 0; spike < 3; spike++) {
			StdDraw.setPenColor(color);
			StdDraw.filledPolygon(x, y);
			for (int i = 0; i < x.length; i++) {
				x[i] = x[i] + 1;
			}
		}
	}

}
