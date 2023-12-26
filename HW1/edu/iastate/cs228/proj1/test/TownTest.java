package edu.iastate.cs228.proj1.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.iastate.cs228.hw1.State;
import edu.iastate.cs228.hw1.Town;

import java.io.FileNotFoundException;

/**
 * 
 * @author Aditi Nachnani
 *
 */
class TownTest {

	@Test
	void gettersAndConstructorTest() throws FileNotFoundException {
		Town t2 = new Town(4,4); //uses first contructor
		t2.randomInit(10); 
		
		int length = 4;
		int width = 4;
		
		 assertEquals('O', t2.toString().charAt(0)); 
		 
		assertEquals(length, t2.getLength()); //tests if length is returning expected value which is 4
		assertEquals(width, t2.getWidth()); //tests if length is returning expected value which is 4

	}

}
