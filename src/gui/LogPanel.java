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
	
	public JTextArea getTextArea(){
		return aDisplay;
	}
	
	@Override
	public void End(int pHumanScore, int pCompScore) {
		if(pHumanScore > pCompScore){
			aDisplay.append("Computer has won with a score of " + pCompScore + " - " + pHumanScore + "!\n");
		} else if(pHumanScore < pCompScore){
			aDisplay.append("Computer has won with a score of " + pHumanScore + " - " + pCompScore + "!\n");
		} else{
			aDisplay.append("It's a tie with a score of " + pHumanScore + " - " + pCompScore + "!\n");
		}
	}
	
	@Override
	public void Draw(boolean pTurn, Action pAction, Card pCard){
		if(pTurn){
			if(pAction == Action.DECK){
				aDisplay.append("Human draws from deck.\n");
			} else if(pAction == Action.DISCARD){
				aDisplay.append("Human draws from discard.\n");
			}
		} else{
			if(pAction == Action.DECK){
				aDisplay.append("Computer draws from deck.\n");
			} else if(pAction == Action.DISCARD){
				aDisplay.append("Computer draws from discard.\n");
			}
		}
	}
	
	public void Discard(boolean pTurn, Card pCard){
		if(pTurn){
			aDisplay.append("Human discards " + pCard + ".\n");
		} else{
			aDisplay.append("Computer discards " + pCard + ".\n");
		}
	}
	
	public void New(Card pCard){
		aDisplay.append("New Game has started!\n");
	}
	
	
}
