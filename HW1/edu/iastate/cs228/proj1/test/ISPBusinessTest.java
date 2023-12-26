package edu.iastate.cs228.proj1.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import edu.iastate.cs228.hw1.ISPBusiness;
import edu.iastate.cs228.hw1.Town;

/**
 * 
 * @author Aditi Nachnani
 *
 */
class ISPBusinessTest {

	/*
	 * Makes a new town with 10 for seed and checks the profit 
	 * before and after updating
	 */
	@Test
	void updateAndProfitTest() {
		
		int profit=1; //profit for the 4x4 from the given file
		int profit2=4;//second iteration profit 
		Town t = new Town(4, 4);
		t.randomInit(10);
		Town t2 = ISPBusiness.updatePlain(t);
		
		assertEquals(profit, ISPBusiness.getProfit(t));
		assertEquals(profit2, ISPBusiness.getProfit(t2));

		}

}
