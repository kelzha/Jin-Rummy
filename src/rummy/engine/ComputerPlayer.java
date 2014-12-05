package rummy.engine;

import java.util.Iterator;

import rummy.engine.Player.Action;
import rummy.model.Card;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String pName) {
		super(pName);
	}

	@Override
	public Card discard(Card pCard) {
		Iterator<Card> i = (aHand.getUnmatchedCards()).iterator();
		Card removedCard = i.next();
		aHand.remove(removedCard);
		aHand.add(pCard);
		return removedCard;
	}
	
	@Override
	public Action decide() {
		aHand.autoMatch();
		aScore = aHand.score();
		if(aScore < 10){
			return Action.KNOCK;
		}
		return Action.DECK;	
	}
}
