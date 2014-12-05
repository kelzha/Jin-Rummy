package rummy.engine;

import java.util.ArrayList;
import java.io.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

import rummy.engine.Player.Action;
import rummy.model.Card;
import rummy.model.Deck;
import rummy.model.Hand;

public class GameEngine implements Serializable {
	private Player aHuman;
	private Player aComp;
	private Deck aDeck;
	private boolean aTurn = true;
	private boolean aEnd = false;
	private Card aDiscard;
	private ArrayList<OtherPanelObserver> aObservers = new ArrayList<>();
	private ArrayList<CardPanelObserver> aCardPanels = new ArrayList<>();
	
	public void newGame(Player pHuman, Player pComp){
		aHuman = pHuman;
		aComp = pComp;
		aEnd = false;
		
		aDeck = new Deck();
		Hand humanHand = new Hand();
		Hand compHand = new Hand();
		for(int i = 0; i < 10; i++){
			humanHand.add(aDeck.draw());
			compHand.add(aDeck.draw());
		}
		
		aHuman.newHand(humanHand);
		aComp.newHand(compHand);
		aDiscard = aDeck.draw();
		notifyObserversNew();
	}
	
	public void endGame(){
		notifyObserversEnd();
		aEnd = true;
	}
	
	public boolean getTurn(){
		return aTurn;
	}
	
	public void Draw(Action pAction) throws EmptyDeckException{
		if(pAction != null){
			System.out.println(pAction);
			Card aCard = null;
			if(pAction == Action.DISCARD){
				aCard = aDiscard;
				aHuman.Draw(aDiscard);
			} else if(pAction == Action.DECK){
				aCard = aDeck.draw();
				aHuman.Draw(aCard);
			} else if(pAction == Action.KNOCK){
				endGame();
				return;
			}
			notifyObserversDraw(pAction, aCard);
		} else{
			Action action = aComp.decide();
			Card aDraw = null;
			if(action == Action.DISCARD){
				aDraw = aDiscard;
				aComp.Draw(aDiscard);
			} else if(action == Action.DECK){
				if(aDeck.size() == 2){
					throw new EmptyDeckException("DECK IS EMPTY");
				}
				aDraw = aDeck.draw();
				aComp.Draw(aDraw);
			} else if(action == Action.KNOCK){
				endGame();
				return;
			}
			notifyObserversDraw(action, aDraw);
		}
	}
	
	public void Discard(Card pCard){
		aDiscard = pCard;
		if(pCard != null){
			aHuman.discard(pCard);
		} else{
			System.out.println(aDiscard);
			aDiscard = ((ComputerPlayer) aComp).discard();
			System.out.println(aDiscard);
		}
		notifyObserversDiscard(aDiscard);
		aTurn = !aTurn;
		System.out.println(aTurn);
	}
	
	public void CompTurn() throws EmptyDeckException{
		Draw(null);
		Discard(null);
	}
	
	public Player getHumanPlayer(){
		return aHuman;
	}

	public Player getComputerPlayer(){
		return aComp;
	}
	
	/*public void Autoplay(){
		while(!aEnd){
			try {
				nextTurn();
			} catch (EmptyDeckException e) {
				newGame(aHuman, aComp);
			}
		}
	}*/
	
	public void addCardPanel(CardPanelObserver pObserver){
		aCardPanels.add(pObserver);
	}
	
	public void removeCardPanel(CardPanelObserver pObserver){
		aCardPanels.remove(pObserver);
	}
	
	public void addObserver(OtherPanelObserver pObserver){
		aObservers.add(pObserver);
	}
	
	public void removeObserver(OtherPanelObserver pObserver){
		aObservers.remove(pObserver);
	}
	
	private void notifyObserversEnd(){
		for(OtherPanelObserver observer : aObservers){
			observer.End(aHuman.getScore(), aComp.getScore());
		}
		
		for(CardPanelObserver observer : aCardPanels){
			observer.End(aHuman.getScore(), aComp.getScore());
		}
	}
	
	/*private void notifyObserversMove(Action pAction, Card pDraw){
		for(OtherPanelObserver observer : aObservers){
			observer.Move(aTurn, pAction, aDiscard, pDraw);
		}
		if(aTurn){
			//aCardPanels.get(0).Move(aDiscard, pDraw);
		} else{
			aCardPanels.get(1).Draw(pDraw);
			aCardPanels.get(1).Discard(aDiscard);
		}
	}*/
		
	private void notifyObserversNew(){
		for(OtherPanelObserver observer : aObservers){
			observer.New(aDiscard);
		}
		aCardPanels.get(0).New(aHuman.getHand());
		aCardPanels.get(1).New(aComp.getHand());
	}
	
	private void notifyObserversDraw(Action pAction, Card pCard){
		for(OtherPanelObserver observer : aObservers){
			observer.Draw(aTurn, pAction, pCard);
		}
		if(aTurn){
			aCardPanels.get(0).Draw(pCard);
		} else{
			aCardPanels.get(1).Draw(pCard);
		}
	}
	
	private void notifyObserversDiscard(Card pCard){
		for(OtherPanelObserver observer : aObservers){
			observer.Discard(aTurn, pCard);
		}
		if(aTurn){
			aCardPanels.get(0).Discard(pCard);
		} else{
			aCardPanels.get(1).Discard(pCard);
		}
	}
	
	public void saveGame(){
		try{
			FileOutputStream fileOut = new FileOutputStream("gameState.ser");
			ObjectOutputStream fout = new ObjectOutputStream(fileOut);
			fout.writeObject(this);
			fout.close();
			fileOut.close();
		} catch(IOException i){
			System.out.println("SOMETHING BAD HAPPENED");
		}
	}
	
	public static GameEngine loadGame(){
		try{
			FileInputStream fileIn = new FileInputStream("gameState.ser");
			ObjectInputStream fin = new ObjectInputStream(fileIn);
			GameEngine load = (GameEngine) fin.readObject();
			fin.close();
			return load;
		} catch(Exception i){
			System.out.println("GAME WASNT SAVED OR SOMETHING WASNT SERIALIZABLE");
			return null;
		}
	}
	
	/*public static void main(String[] args){
		GameEngine test = new GameEngine();
		Player human = new HumanPlayer("HUMAN");
		Player comp = new ComputerPlayer("COMP");
		test.newGame(human, comp);
		test.addObserver(new GameLogger());
		
		Hand one = human.getHand();
		Hand two = comp.getHand();
		
		for(int i = 0; i < 30; i++){
			try {
				test.nextTurn();
				//System.out.println(one);
				//System.out.println(two);
				//System.out.println();
				if(i == 15){
					test.saveGame();
					System.out.println(one);
					System.out.println(two);
				}
			} catch (EmptyDeckException e) {
				e.printStackTrace();
			}
		}
		test = loadGame();
		human = test.getHumanPlayer();
		comp = test.getComputerPlayer();
		
		System.out.println(human.getHand() + "\n" + comp.getHand());
		//test.Autoplay();
	}*/
}

class MyException extends Exception{
	public MyException(Throwable t){
		super(t);
	}
}