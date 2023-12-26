package edu.iastate.cs228.hw1;


/**
 * 
 * @author Aditi Nachnani
 *
 */
public class Streamer extends TownCell{

	public Streamer(Town p, int r, int c) {
		super(p,r,c);
	}

	@Override
	public State who() {
		return State.STREAMER;
	}

	@Override
	public TownCell next(Town tNew) {
		this.census(nCensus);		
		if((nCensus[EMPTY]+nCensus[OUTAGE]<=1)) {
			return tNew.grid[row][col] = new Reseller(tNew,row, col);
		}else if(nCensus[RESELLER]>0) {
			return tNew.grid[row][col] = new Outage(tNew,row, col);
		}else if(nCensus[OUTAGE]>0) {
			return tNew.grid[row][col] = new Empty(tNew,row, col);
		}else if(nCensus[CASUAL]>=5){
			return tNew.grid[row][col] = new Streamer(tNew,row, col);
		}else {
			return tNew.grid[row][col] = new Streamer(tNew,row, col);
		}
	}

}
