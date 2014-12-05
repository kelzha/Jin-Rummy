package rummy.engine;

import java.io.Serializable;

import rummy.engine.Player.Action;
import rummy.model.Card;
import rummy.model.Hand;

public interface CardPanelObserver extends Serializable {
	
	void End(int pHumanScore, int pCompScore);
	void Move(Card pDiscard, Card pDraw);
	void New(Hand pHand);
	
}
