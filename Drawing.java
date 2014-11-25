package jbackgammon;

import java.awt.Color;

import edu.princeton.cs.introcs.StdDraw;

public class Drawing {

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


	public static void boardDraw() {
		// Background
		StdDraw.setCanvasSize(600, 400);
		StdDraw.setXscale(0, 6);
		StdDraw.setYscale(0, 4);
		StdDraw.clear(BACKGROUND);
		StdDraw.setPenColor(BROWN);
		StdDraw.filledRectangle(1.398, 2, 1.5, 2);
		StdDraw.filledRectangle(4.598, 2, 1.5, 2);

		spikeDraw(1, LIGHT_BROWN, DARK_BROWN, quad1PointX, quad1PointY);
		spikeDraw(1, LIGHT_BROWN, DARK_BROWN, quad2PointX, quad2PointY);
		spikeDraw(0, LIGHT_BROWN, DARK_BROWN, quad3PointX, quad3PointY);
		spikeDraw(0, LIGHT_BROWN, DARK_BROWN, quad4PointX, quad4PointY);
		// Outlines
		StdDraw.setPenColor(BLACK);
		StdDraw.rectangle(1.398, 2, 1.5, 2);
		StdDraw.rectangle(4.598, 2, 1.5, 2);

	}

	public static void spikeDraw(int start, Color color1, Color color2,
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

	public static void main(String[] args) {
		boardDraw();
	}
}
