package edu.iastate.cs228.proj1.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.iastate.cs228.hw1.Casual;
import edu.iastate.cs228.hw1.Reseller;
import edu.iastate.cs228.hw1.State;
import edu.iastate.cs228.hw1.Town;

/**
 * 
 * @author Aditi Nachnani
 *
 */
class ResellerTest {

	/*
	 * Makes a new reseller object and checks who() to see the identity of the object
	 */
	@Test
	void whoTest() {
		int row = 1; 
		int col = 3;
		Town t = new Town(4, 4);
		Reseller c = new Reseller(t, row,col);

		assertEquals(State.RESELLER, c.who());
	}

}
