package edu.iastate.cs228.hw1;

/**
 * 
 * @author Aditi Nachnani
 *	Also provide appropriate comments for this class
 *
 */
public abstract class TownCell {

	protected Town plain;
	protected int row;
	protected int col;
	
	
	// constants to be used as indices.
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;
	
	public static final int NUM_CELL_TYPE = 5;
	
	//Use this static array to take census.
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
	    col = c;
	}
	
	/**
	 * Checks all neigborhood cell types in the neighborhood.
	 * Refer to homework pdf for neighbor definitions (all adjacent
	 * neighbors excluding the center cell).
	 * Use who() method to get who is present in the neighborhood
	 *  
	 * @param counts of all customers
	 */
	public void census(int nCensus[]) {
		// zero the counts 	 all customers
		nCensus[RESELLER] = 0; 
		nCensus[EMPTY] = 0; 
		nCensus[CASUAL] = 0; 
		nCensus[OUTAGE] = 0; 
		nCensus[STREAMER] = 0; 
		int x; //variable for row
		int y;	//variable for col
	if (plain.getLength()>1 && plain.getWidth()>1) {
		if(row>0) {
			if(plain.grid[row-1][col]!=null) {
				x = row -1;
				y = col;
				helper(nCensus,x,y); 
			}
		}
		if(row>0 && col<plain.getWidth()-1) {
			if(plain.grid[row-1][col+1]!=null){
				x = row -1;
				y = col+1;
				helper(nCensus,x,y);
			}
		}
		if(col<plain.getWidth()-1) {
			if(plain.grid[row][col+1]!=null) {
				x = row;
				y = col+1;
				helper(nCensus,x,y);
			}
		}
		if(row<plain.getLength()-1 && col<plain.getWidth()-1) {
			if(plain.grid[row+1][col+1]!=null) {
				x = row+1;
				y = col+1;
				helper(nCensus,x,y);
			}
		} if(row<plain.getLength()-1) {
			if(plain.grid[row+1][col]!=null) {
				x = row+1;
				y = col;
				helper(nCensus,x,y);
			}
		}
		if(row<plain.getLength()-1 && col>0) {
			if(plain.grid[row+1][col-1]!=null) {
				x = row+1;
				y = col-1;
				helper(nCensus,x,y);
			}
		}
		if(col>0) {
			if(plain.grid[row][col-1]!=null) {
				x = row;
				y = col-1;
				helper(nCensus,x,y);
			}
		}
		if(col>0 && row>0) {
			if(plain.grid[row-1][col-1]!=null) {
				x = row -1;
				y = col-1;
				helper(nCensus,x,y);
			}
		}
	}
}

	/**
	 * increments indices of objects if it is a neighbor
	 * 
	 * @param nCensus: nCensus list passed into census
	 * @param x: row position
	 * @param y: col position
	 */
	private void helper(int nCensus[], int x, int y) {
		if(plain.grid[x][y].who()==State.RESELLER) {
			nCensus[RESELLER]++;
		}
		else if(plain.grid[x][y].who()==State.EMPTY) {
			nCensus[EMPTY]++;
		}
		else if(plain.grid[x][y].who()==State.CASUAL) {
			nCensus[CASUAL]++;
		}else if(plain.grid[x][y].who()==State.OUTAGE) {
			nCensus[OUTAGE]++;
		}
		else if(plain.grid[x][y].who()==State.STREAMER) {
			nCensus[STREAMER]++;
		}
	}
	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	public abstract TownCell next(Town tNew);
}
