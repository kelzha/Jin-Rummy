package rummy.engine;

import java.io.Serializable;

import rummy.model.Card;
import rummy.model.Hand;

public abstract class Player implements Serializable {
	protected Hand aHand;
	protected int aScore;
	public enum Action {DECK, DISCARD, KNOCK};
	private String aName;
	
	abstract Action decide();
	public void discard(Card pCard){
		aHand.remove(pCard);
	}
	
	protected Player (String pName){
		aName = pName;
	}
	
	public String getName(){
		return aName;
	}
	
	public void newHand(Hand pHand){
		aHand = pHand;
		aHand.autoMatch();
		aScore = aHand.score();
	}
	
	public Hand getHand(){
		return aHand;
	}
	
	public int getScore(){
		aHand.autoMatch();
		aScore = aHand.score();
		return aScore;
	}
	
	public void Draw(Card pCard){
		aHand.add(pCard);
	}
	
	/*public final Card takeTurn(Card pCard){
		return CompDiscard(pCard);
	}*/
}
