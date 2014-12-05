package rummy.engine;

import java.util.Iterator;

import rummy.model.Card;

public class HumanPlayer extends Player{
	
	public HumanPlayer(String pName) {
		super(pName);
	}
	
	@Override
	public Action decide() {
		aHand.autoMatch();
		aScore = aHand.score();
		if(aScore < 10){
			return Action.KNOCK;
		}
		return Action.DISCARD;
	}
}
