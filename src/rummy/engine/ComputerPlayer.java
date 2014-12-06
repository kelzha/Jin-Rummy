package rummy.engine;

import java.util.Iterator;

import rummy.engine.Player.Action;
import rummy.model.Card;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String pName) {
		super(pName);
	}
	
	public Card discard(){
		aHand.autoMatch();
		Iterator<Card> i = (aHand.getUnmatchedCards()).iterator();
		Card removedCard = i.next();
		while(i.hasNext()){
			Card tempCard = i.next();
			if(removedCard.compareTo(tempCard) == -1){
				removedCard = tempCard;
			}
		}
		aHand.remove(removedCard);
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
