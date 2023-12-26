package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


/**
 *  @author Aditi Nachnani
 *
 */
public class Town {
	
	private int length, width;  //Row and col (first and second indices)
	public TownCell[][] grid;
	
	/**
	 * Constructor to be used when user wants to generate grid randomly, with the given seed.
	 * This constructor does not populate each cell of the grid (but should assign a 2D array to it).
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		this.length=length; 
		this.width=width; 
		grid = new TownCell[length][width]; 
	}
	
	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of catching it.
	 * Ensure that you close any resources (like file or scanner) which is opened in this function.
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		File file = new File("src/"+inputFileName);
		Scanner read = new Scanner(file);

		this.width= Integer.parseInt(read.next());
		this.length = Integer.parseInt(read.next());
		grid = new TownCell[length][width];
		
		while (read.hasNext()) { //reads the file and stores it into the grid by creating the specific objects
		 for (int i = 0; i < grid.length; i++) {
			 for (int j = 0; j<grid[i].length; j++) {
				 String in = read.next();
				 switch(in) {
					 case "C":
						 grid[i][j]= new Casual(this,i,j);
						 break;
					 case "S":
						 grid[i][j]= new Streamer(this,i,j);
						 break;
					 case "R":
						 grid[i][j]= new Reseller(this,i,j);
						 break;
					 case "E":
						 grid[i][j]= new Empty(this,i,j);
						 break;
					 case "O":
			 		 grid[i][j]= new Outage(this,i,j);
						 break;
				 }
			 }
		 }
		}
		
		read.close();
	}
	
	/**
	 * Returns width of the grid.
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns length of the grid.
	 * @return
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following class object:
	 * Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {
		Random rand = new Random(seed);
		 for (int i = 0; i <length; i++) {
			 for (int j = 0; j<width; j++) {
				int newRandomValue = rand.nextInt(5);
				 switch(newRandomValue) {
					 case 2:
						 grid[i][j]= new Casual(this,i,j);
						 break;
					 case 4:
						 grid[i][j]= new Streamer(this,i,j);
						 break;
					 case 0:
						 grid[i][j]= new Reseller(this,i,j);
						 break;
					 case 1:
						 grid[i][j]= new Empty(this,i,j);
						 break;
					 case 3:
						 grid[i][j]= new Outage(this,i,j);
						 break;
				 }
			 }
		 }
	}
	
	/**
	 * Output the town grid. For each square, output the first letter of the cell type.
	 * Each letter should be separated either by a single space or a tab.
	 * And each row should be in a new line. There should not be any extra line between 
	 * the rows.
	 */
	@Override
	public String toString() {
		String s = "";
		int count = 0;
		String temp ="";
		
		for (TownCell[]grid2: grid) {
			for (TownCell t: grid2) {
				count++;
				if (count%this.length==0) {
					temp = "\n";
				}else {
					temp = "\t";
				}
				s = s + t.who().toString().charAt(0) + temp;
			}
		}
		return s.substring(0,s.length()-1); //to not print the new line
	}
}
