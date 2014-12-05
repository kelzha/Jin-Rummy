package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import rummy.engine.CardPanelObserver;
import rummy.engine.Player.Action;
import rummy.model.Card;
import rummy.model.Hand;

public class CardPanel extends JPanel implements CardPanelObserver{
	
	private HashMap<JLabel, Card> aCards = new HashMap<>();
	
	public CardPanel(String aName){
		super(new OverlapLayout(new Point(40, 0)));
		setBorder(new TitledBorder( aName + "'s Hand" ));
		
		Insets ins = new Insets(10, 5, 0, 0);
		((OverlapLayout)getLayout()).setPopupInsets(ins);
		setBackground( GameFrame.BACKGROUND_COLOR );
	}
	
	public void initialize(Hand pHand){
		System.out.println(pHand);
		aCards.clear();
		removeAll();
		Object[] pCards = pHand.getCards().toArray();
		Arrays.sort(pCards);
		for(Object pCard : pCards){
			JLabel aLabel = new JLabel(CardImages.getCard((Card) pCard));
			aLabel.addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e)
				{
				    Component c = e.getComponent();
				    Boolean constraint = ((OverlapLayout)getLayout()).getConstraints(c);

				    if (constraint == null || constraint == OverlapLayout.POP_DOWN)
				    {
				    	popAllDown();
				    	((OverlapLayout)getLayout()).addLayoutComponent(c, OverlapLayout.POP_UP);
				    }
				    else
				    {
				    	((OverlapLayout)getLayout()).addLayoutComponent(c, OverlapLayout.POP_DOWN);
				    }

				    c.getParent().invalidate();
				    c.getParent().validate();
				}
			});
			aCards.put(aLabel, (Card) pCard);
			add(aLabel);
		}
		validate();
		repaint();
	}
	
	private void popAllDown()
	{
		Component[] lChildren = getComponents();
		for( Component component : lChildren )
		{
			((OverlapLayout)getLayout()).addLayoutComponent(component, OverlapLayout.POP_DOWN);
		}
	}
	
	public Card isUp()
	{
		for( Component component : getComponents() )
		{
			Boolean constraint = ((OverlapLayout)getLayout()).getConstraints(component);
			if (constraint != null && constraint == OverlapLayout.POP_UP)
			{
				return aCards.get(component);
			}
		}
		return null;
	}
	
	public void New(Hand pHand){
		initialize(pHand);
	}
	
	public void Move(Card pDiscard, Card pDraw){
		for(JLabel label : aCards.keySet()){
			if(aCards.get(label).equals(pDiscard)){
				aCards.remove(label);
				break;
			}
		}

		JLabel aDraw = new JLabel(CardImages.getCard(pDraw));
		aCards.put(aDraw, pDraw);
		removeAll();
		Hand tempHand = new Hand();
		for(Card aCard : aCards.values()){
			tempHand.add(aCard);
		}
		initialize(tempHand);
		validate();
		repaint();
	}
	
	public void End(int pHumanScore, int pCompScore){
		
	}
}
