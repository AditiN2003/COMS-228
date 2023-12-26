package edu.iastate.cs228.hw1;

/**
 * 
 * @author Aditi Nachnani
 *
 */
public class Reseller extends TownCell {

	public Reseller(Town p, int r, int c) {
		super(p,r,c);
	}

	@Override
	public State who() {
		return State.RESELLER;
	}

	@Override
	public TownCell next(Town tNew) {
		
		this.census(nCensus);
		
		if(nCensus[CASUAL]<=3 ||nCensus[EMPTY]>=3) {
			return new Empty(tNew,row, col);
		}else if(nCensus[CASUAL]>=5) {
			return new Streamer(tNew,row,col);
		}else {
			return new Reseller(tNew,row,col);
		}	
	}

}
