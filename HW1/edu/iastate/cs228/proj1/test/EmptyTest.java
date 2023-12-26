package edu.iastate.cs228.proj1.test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 
 * @author Aditi Nachnani
 *
 */

import org.junit.jupiter.api.Test;

import edu.iastate.cs228.hw1.Casual;
import edu.iastate.cs228.hw1.Empty;
import edu.iastate.cs228.hw1.State;
import edu.iastate.cs228.hw1.Town;

class EmptyTest {

	/*
	 * Makes a new empty object and checks who() to see the identity of the object
	 */
	@Test
	void whoTest() {
		int row = 1; 
		int col = 3;
		Town t = new Town(4, 4);
		Empty c = new Empty(t, row,col);

		assertEquals(State.EMPTY, c.who());
	}

}
