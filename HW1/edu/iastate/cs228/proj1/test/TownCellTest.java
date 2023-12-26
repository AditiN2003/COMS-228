package edu.iastate.cs228.proj1.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import edu.iastate.cs228.hw1.State;
import edu.iastate.cs228.hw1.Town;
import edu.iastate.cs228.hw1.TownCell;

/**
 * 
 * @author Aditi Nachnani
 *
 */
class TownCellTest {

	/*
	 * Makes a new town object and checks census of that Town Cell object to see the neighbors 
	 * Also uses next(t) to see what object it turns to when creating a new town for another iteration
	 */
	@Test
	void censusAndNextTest() {

		Town t = new Town(4, 4);
		t.randomInit(10);
		t.grid[0][0].census(TownCell.nCensus);
		assertEquals("[1, 2, 0, 0, 0]", Arrays.toString(TownCell.nCensus));

		assertEquals(State.OUTAGE, t.grid[0][0].who());
		assertEquals(State.EMPTY, t.grid[0][0].next(t).who()); // after iter 1

	}

}
