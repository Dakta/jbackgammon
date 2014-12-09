package jbackgammon;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

public class BackgammonModelTest {

	private BackgammonModel model;
	
	@Before
	public void setUp() throws Exception {
		model = new BackgammonModel(StdDraw.WHITE, StdDraw.BLACK);
	}

	@Test
	public void testToString() {
		assertEquals("W \n 1 ##\n 2 \n 3 \n 4 \n 5 \n 6 OOOOO\n 7 \n 8 OOO\n 9 \n10 \n11 \n12 #####\n  \n  \n13 OOOOO\n14 \n15 \n16 \n17 ###\n18 \n19 #####\n20 \n21 \n22 \n23 \n24 OO\nB \n", model.toString());
	}

	/*
	@Test
	public void testGetCount() {
		assertEquals(5, model.getCount(6));
		assertEquals(0, model.getCount(15));
	}
	
	@Test
	public void testGetColor() {
		assertEquals(BackgammonModel.white, model.getColor(6));
		assertEquals(BackgammonModel.black, model.getColor(12));
		assertEquals(BackgammonModel.empty, model.getColor(5));
	}
	*/
	
	@Test
	public void testMove() {
		model.move(12, 13); // try an illegal move
		assertEquals("W \n 1 ##\n 2 \n 3 \n 4 \n 5 \n 6 OOOOO\n 7 \n 8 OOO\n 9 \n10 \n11 \n12 #####\n  \n  \n13 OOOOO\n14 \n15 \n16 \n17 ###\n18 \n19 #####\n20 \n21 \n22 \n23 \n24 OO\nB \n", model.toString());
		model.move(6, 8);
		assertEquals("W \n 1 ##\n 2 \n 3 \n 4 \n 5 \n 6 OOOO\n 7 \n 8 OOOO\n 9 \n10 \n11 \n12 #####\n  \n  \n13 OOOOO\n14 \n15 \n16 \n17 ###\n18 \n19 #####\n20 \n21 \n22 \n23 \n24 OO\nB \n", model.toString());
		model.move(17, 18);
		assertEquals("W \n 1 ##\n 2 \n 3 \n 4 \n 5 \n 6 OOOO\n 7 \n 8 OOOO\n 9 \n10 \n11 \n12 #####\n  \n  \n13 OOOOO\n14 \n15 \n16 \n17 ##\n18 #\n19 #####\n20 \n21 \n22 \n23 \n24 OO\nB \n", model.toString());
	}
	
	@Test
	public void testBlot() {
		// set up a couple single-piece points
		model.move(17, 18);
		model.move(6, 7);
		assertEquals("W \n 1 ##\n 2 \n 3 \n 4 \n 5 \n 6 OOOO\n 7 O\n 8 OOO\n 9 \n10 \n11 \n12 #####\n  \n  \n13 OOOOO\n14 \n15 \n16 \n17 ##\n18 #\n19 #####\n20 \n21 \n22 \n23 \n24 OO\nB \n", model.toString());
		// now blot
		model.move(12, 7);
		assertEquals("W \n 1 ##\n 2 \n 3 \n 4 \n 5 \n 6 OOOO\n 7 #\n 8 OOO\n 9 \n10 \n11 \n12 ####\n  O\n  \n13 OOOOO\n14 \n15 \n16 \n17 ##\n18 #\n19 #####\n20 \n21 \n22 \n23 \n24 OO\nB \n", model.toString());
		model.move(13, 18);
		assertEquals("W \n 1 ##\n 2 \n 3 \n 4 \n 5 \n 6 OOOO\n 7 #\n 8 OOO\n 9 \n10 \n11 \n12 ####\n  O\n  #\n13 OOOO\n14 \n15 \n16 \n17 ##\n18 O\n19 #####\n20 \n21 \n22 \n23 \n24 OO\nB \n", model.toString());
	}

	@Test
	public void testHasWon(){
		// assertEquals(false, model.hasWon(model.player1));
		
	}

	@Test
	public void testCanEnter(){
		// not yet written
		// move validation will probably occur in Backgammon.java
	}
	
}