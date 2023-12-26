package edu.iastate.cs228.hw1;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Aditi Nachnani
 *
 * The ISPBusiness class performs simulation over a grid 
 * plain with cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {
	
	private static double totalProfit=0; // to track the profit overtime
	
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());
		
		 for (int i = 0; i < tOld.getLength(); i++) {
			 for (int j = 0; j<tOld.getWidth(); j++) {
				 tNew.grid[i][j]= tOld.grid[i][j].next(tNew); //stores the new objects into a new town
			 }
		 } 
		return tNew;
	}
	
	/**
	 * Returns the profit for the current state in the town grid.
	 * @param town
	 * @return
	 */
	public static int getProfit(Town town) {
		int count = 0; 
		for (int i = 0; i < town.toString().length(); i++) {
			if(town.toString().charAt(i)=='C') {
			count++;
			}
		}
		return count;
	}
	
	/**
	 *  Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should print the profit percentage
	 *  with two digits after the decimal point:  Example if profit is 35.5600004, your output
	 *  should be:
	 *
	 *	35.56%
	 *  
	 * Note that this method does not throw any exception, so you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args
	 * 
	 */
	public static void main(String []args) {
     	int inputNum = 0; 
     	Town t=null;
     	double potential=0;
		Scanner scnr = new Scanner(System.in);
		System.out.println("How to populate grid (type 1 or 2): 1: from a file. 2: randomly with seed");
		inputNum = scnr.nextInt();
		if (inputNum==1) {
			System.out.println("Please enter file path:");
			String fileName = scnr.next();
			try {
				 t = new Town(fileName);
				 totalProfit = totalProfit + getProfit(t);
			}catch(FileNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}else {
			System.out.println("Provide rows, cols and seed integer separated by spaces: ");
			scnr.nextLine();
			String randInput = scnr.nextLine();
			String[] result = randInput.split(" ");
			 t = new Town(Integer.parseInt(result[1]), Integer.parseInt(result[0]));
			 t.randomInit(Integer.parseInt(result[2]));
			 totalProfit = totalProfit + getProfit(t);

		}
		
		for (int j = 1; j<12;j++) {
			t = updatePlain(t);
			totalProfit = totalProfit + getProfit(t);
			potential = totalProfit/(t.getLength()*t.getWidth()*12)*100;
		}
		System.out.printf("%.2f%%%n", potential);
		
		scnr.close();
	}
}
