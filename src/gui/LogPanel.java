package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

import rummy.engine.OtherPanelObserver;
import rummy.engine.Player.Action;
import rummy.model.Card;

public class LogPanel extends JPanel implements OtherPanelObserver{
	private JTextArea aDisplay = new JTextArea(25,38);
	private static final Color BACKGROUND_COLOR = new Color(255, 207, 121);
	
	public LogPanel(){
		setBorder(new TitledBorder("Game Log"));
		setBackground(new Color(255, 207, 121));
		aDisplay.setBackground(new Color(255, 225, 154));
	    aDisplay.setEditable ( false ); // set textArea non-editable
	    JScrollPane scroll = new JScrollPane(aDisplay);
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
	    DefaultCaret caret = (DefaultCaret)aDisplay.getCaret();
	    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	    add(scroll, BorderLayout.CENTER);
	}
	
	@Override
	public void End(int pHumanScore, int pCompScore) {
		if(pHumanScore > pCompScore){
			aDisplay.append("COMPUTER HAS WON WITH A SCORE OF " + pCompScore + " - " + pHumanScore + "\n");
		} else if(pHumanScore < pCompScore){
			aDisplay.append("Human HAS WON WITH A SCORE OF " + pHumanScore + " - " + pCompScore + "\n");
		} else{
			aDisplay.append("IT'S A TIE WITH A SCORE OF " + pHumanScore + " - " + pCompScore + "\n");
		}
	}
	@Override
	public void Move(boolean pTurn, Action pAction, Card pCard, Card pCard2) {
		if(pTurn){
			if(pAction == Action.DECK){
				aDisplay.append("HUMAN DRAWS FROM DECK AND DISCARDS " + pCard + "\n");
			} else if(pAction == Action.DISCARD){
				aDisplay.append("HUMAN DRAWS FROM DISCARD AND DISCARDS " + pCard + "\n");
			}
		} else{
			if(pAction == Action.DECK){
				aDisplay.append("COMPUTER DRAWS FROM DECK AND DISCARDS " + pCard + "\n");
			} else if(pAction == Action.DISCARD){
				aDisplay.append("COMPUTER DRAWS FROM DISCARD AND DISCARDS " + pCard + "\n");
			}
		}
	}
	
	public void New(Card pCard){
		aDisplay.append("NEW GAME HAS STARTED\n");
	}
	
	
}
