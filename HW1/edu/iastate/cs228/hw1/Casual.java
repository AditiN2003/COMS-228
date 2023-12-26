package edu.iastate.cs228.hw1;

/**
 * 
 * @author Aditi Nachnani
 *
 */
public class Casual extends TownCell {

	public Casual(Town p, int r, int c) {
		super(p,r,c);
	}

	@Override
	public State who() {
		return State.CASUAL;
	}

	@Override
	public TownCell next(Town tNew) {

		this.census(nCensus); //calls census to check neighbors
					
		 if((nCensus[EMPTY]+nCensus[OUTAGE]<=1)) {
			return tNew.grid[row][col] = new Reseller(tNew,row, col);
		}else if(nCensus[RESELLER]>0) {
			return tNew.grid[row][col] = new Outage(tNew,row, col);
		}else if(nCensus[STREAMER]>0 || nCensus[CASUAL]>=5) {
			return tNew.grid[row][col] = new Streamer(tNew,row, col);
		}else {
			return tNew.grid[row][col] = new Casual (tNew,row, col);
		}	
	}

}
