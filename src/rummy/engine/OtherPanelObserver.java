package rummy.engine;

import rummy.engine.Player.Action;
import rummy.model.Card;
import rummy.model.Hand;

public interface OtherPanelObserver {
	void End(int pHumanScore, int pCompScore);
	//void Move(boolean pTurn, Action pAction, Card pDiscard, Card pDraw);
	void Draw(boolean pTurn, Action pAction, Card pCard);
	void New(Card pCard);
	void Discard(boolean pTurn, Card pCard);
}
