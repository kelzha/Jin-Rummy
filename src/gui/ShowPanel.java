package gui;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import rummy.engine.OtherPanelObserver;
import rummy.engine.Player.Action;
import rummy.model.Card;
import rummy.model.Hand;

public class ShowPanel extends JPanel implements OtherPanelObserver{
	private static final int WIDTH = 150;
	private static final int HEIGHT = 150;
	private Card aCard;
	
	public ShowPanel()
	{
		setBorder( new EmptyBorder( 20, 5, 5, 5 ));
		setBackground( GameFrame.BACKGROUND_COLOR );
		setMinimumSize(new Dimension(WIDTH,HEIGHT));
	}

	@Override
	public void End(int pHumanScore, int pCompScore) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void Draw(boolean pTurn, Action pAction, Card pCard){
		if(pCard.equals(aCard)){
			removeAll();
			repaint();
		}
	}
	
	@Override
	public void Discard(boolean pTurn, Card pCard){
		removeAll();
		JLabel aDiscard = new JLabel(CardImages.getCard(pCard));
		add(aDiscard);
		aCard = pCard;
		repaint();
	}
	
	/*@Override
	public void Move(boolean pTurn, Action pAction, Card pDiscard, Card pDraw) {
		removeAll();
		JLabel aDiscard = new JLabel(CardImages.getCard(pDiscard));
		add(aDiscard);
		validate();
		repaint();
	}*/

	@Override
	public void New(Card pCard) {
		removeAll();
		JLabel aDiscard = new JLabel(CardImages.getCard(pCard));
		add(aDiscard);
		aCard = pCard;
		repaint();
	}
}
