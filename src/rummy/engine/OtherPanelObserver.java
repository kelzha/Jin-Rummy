package rummy.engine;

import rummy.engine.Player.Action;
import rummy.model.Card;
import rummy.model.Hand;

public interface OtherPanelObserver {
	void End(int pHumanScore, int pCompScore);
	void Move(boolean pTurn, Action pAction, Card pDiscard, Card pDraw);
	void New(Card pCard);
}
