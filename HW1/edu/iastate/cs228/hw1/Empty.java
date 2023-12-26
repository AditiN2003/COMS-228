package edu.iastate.cs228.hw1;

/**
 * 
 * @author Aditi Nachnani
 *
 */

public class Empty extends TownCell {

	public Empty(Town p, int r, int c) {
		super(p,r,c);
	}

	@Override
	public State who() {
		return State.EMPTY;
	}

	@Override
	public TownCell next(Town tNew) {
		this.census(nCensus);
		
		if((nCensus[EMPTY]+nCensus[OUTAGE]<=1)) {
			return tNew.grid[row][col]= new Reseller(tNew,row, col);
		}else {
			return  tNew.grid[row][col]=new Casual(tNew,row, col);
		}
	}

}
