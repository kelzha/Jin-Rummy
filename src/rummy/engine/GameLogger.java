package rummy.engine;

import java.util.logging.Logger;

import rummy.engine.Player.Action;
import rummy.model.Card;
import rummy.model.Hand;

public class GameLogger implements OtherPanelObserver{
	private static Logger log = Logger.getLogger("GameLogger");
	
	@Override
	public void End(int pHumanScore, int pCompScore) {
		if(pHumanScore > pCompScore){
			log.info("COMPUTER HAS WON WITH A SCORE OF " + pCompScore + " - " + pHumanScore + "\n");
		} else if(pHumanScore < pCompScore){
			log.info("Human HAS WON WITH A SCORE OF " + pHumanScore + " - " + pCompScore + "\n");
		} else{
			log.info("IT'S A TIE WITH A SCORE OF " + pHumanScore + " - " + pCompScore + "\n");
		}
	}
	@Override
	public void Move(boolean pTurn, Action pAction, Card pCard, Card pCard2) {
		if(pTurn){
			if(pAction == Action.DECK){
				log.info("HUMAN DRAWS FROM DECK AND DISCARDS " + pCard + "\n");
			} else if(pAction == Action.DISCARD){
				log.info("HUMAN DRAWS FROM DISCARD AND DISCARDS " + pCard + "\n");
			}
		} else{
			if(pAction == Action.DECK){
				log.info("COMPUTER DRAWS FROM DECK AND DISCARDS " + pCard + "\n");
			} else if(pAction == Action.DISCARD){
				log.info("COMPUTER DRAWS FROM DISCARD AND DISCARDS " + pCard + "\n");
			}
		}
	}
	
	public void New(Card pCard){
		log.info("NEW GAME HAS STARTED\n");
	}
	
}
