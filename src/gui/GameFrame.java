package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import rummy.engine.ComputerPlayer;
import rummy.engine.EmptyDeckException;
import rummy.engine.GameEngine;
import rummy.engine.HumanPlayer;
import rummy.engine.Player;
import rummy.engine.Player.Action;
import rummy.model.Card;

public class GameFrame extends JFrame{
	public static final Color BACKGROUND_COLOR = new Color(93, 195, 96);
	private GameEngine aEngine = new GameEngine();
	
	private JPanel makeNewPanel(){
		JPanel aTemp = new JPanel();
		aTemp.setBackground(BACKGROUND_COLOR);
		return aTemp;
	}
	
	private JPanel makeCenterPanel(int x, int y){
		JPanel aPrettyPanel = new JPanel();
		aPrettyPanel.setLayout(new GridLayout(x,y));
		return aPrettyPanel;
	}
	
	private void remake(JPanel pToRemake, JPanel pRemake, JPanel pPlayer){
		pToRemake.removeAll();
		pToRemake.add(pPlayer);
		pToRemake.add(pRemake);
		pToRemake.add(makeNewPanel());
		pToRemake.revalidate();
		pToRemake.repaint();
	}
	
	private void setImagePanel(JPanel pPanel, String pLocation){
		JLabel aLabel = new JLabel(new ImageIcon(CardImages.class.getClassLoader().getResource(pLocation)));
		pPanel.add(aLabel);
		pPanel.setBackground(BACKGROUND_COLOR);
	}
	
	public GameFrame(){
		setTitle("Jin Rummy");
		setLayout(new BorderLayout());
		final JPanel aPanel = new JPanel();
		aPanel.setLayout(new GridLayout(1, 3));
		add(aPanel, BorderLayout.CENTER);
		
		final JPanel aHumanPanel = makeCenterPanel(5,1);
		final JPanel aDiscardPanel = makeCenterPanel(3,1);
		final JPanel aCompPanel = makeCenterPanel(3, 1);
		final JPanel aMiddlePanel = makeCenterPanel(3, 1);
		final JPanel aHumanTurnPanel = makeCenterPanel(1, 1);
		setImagePanel(aHumanTurnPanel, "images/human.png");
		final JPanel aCompTurnPanel = makeCenterPanel(1, 1);
		setImagePanel(aCompTurnPanel, "images/comp.png");
		final JPanel aWinPanel = makeCenterPanel(1, 1);
		setImagePanel(aWinPanel, "images/win.png");
		final JPanel aLosePanel = makeCenterPanel(1, 1);
		setImagePanel(aLosePanel, "images/lose.png");
		
		final JPanel aRightPanel = makeCenterPanel(3, 1);
		aRightPanel.add(aHumanTurnPanel);
		
		final CardPanel aPlayer1 = new CardPanel("Human");
		final CardPanel aPlayer2 = new CardPanel("Computer");
		aEngine.addCardPanel(aPlayer1);
		aMiddlePanel.add(aPlayer1);
		aEngine.addCardPanel(aPlayer2);
		
		final LogPanel aLog = new LogPanel();
		aEngine.addObserver(aLog);
		ShowPanel aDiscard = new ShowPanel();
		aMiddlePanel.add(aDiscard);
		aEngine.addObserver(aDiscard);
		aMiddlePanel.add(aPlayer2);
		
		JButton aDrawDeckButton = new JButton("Draw from Deck");
		aDrawDeckButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try {
					aEngine.Draw(Action.DECK);
					remake(aRightPanel, aDiscardPanel, aHumanTurnPanel);
				} catch (EmptyDeckException e) {
					aEngine.newGame(aEngine.getHumanPlayer(), aEngine.getComputerPlayer());
				}
			}
		});
		
		JButton aDrawDiscardButton = new JButton("Draw from Discard");
		aDrawDiscardButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try {
					aEngine.Draw(Action.DISCARD);
					remake(aRightPanel, aDiscardPanel, aHumanTurnPanel);
				} catch (EmptyDeckException e) {
					aEngine.newGame(aEngine.getHumanPlayer(), aEngine.getComputerPlayer());
				}
			}
		});
		
		JButton aEndButton = new JButton("Knock");
		aEndButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try {
					if(aEngine.getHumanPlayer().getScore() > 10){
						aLog.getTextArea().append("You must have less than 10 score to knock!\n");
					} else{
						aEngine.Draw(Action.KNOCK);
						if(aEngine.getWinner().equals("Human")){
							remake(aRightPanel, aWinPanel, makeNewPanel());
						} else{
							remake(aRightPanel, aLosePanel, makeNewPanel());
						}
					}
				} catch (EmptyDeckException e) {
					aEngine.newGame(aEngine.getHumanPlayer(), aEngine.getComputerPlayer());
				}
			}
		});
		
		aHumanPanel.add(makeNewPanel());
		aHumanPanel.add(aDrawDeckButton);
		aHumanPanel.add(aDrawDiscardButton);
		aHumanPanel.add(aEndButton);
		aHumanPanel.add(makeNewPanel());
		
		JButton aDiscardButton = new JButton("Discard");
		aDiscardButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Card aCard = aPlayer1.isUp();
				if(aCard == null){
					aLog.getTextArea().append("You must choose a card to discard!\n");
				} else{
					aEngine.Discard(aCard);
					remake(aRightPanel, aCompPanel, aCompTurnPanel);
				}
			}
		});
		aDiscardPanel.add(makeNewPanel());
		aDiscardPanel.add(aDiscardButton);
		aDiscardPanel.add(makeNewPanel());
		
		JButton aCompButton = new JButton("Computer Turn");
		aCompButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try {
					aEngine.Draw(null);
					if(!aEngine.getEnd()){
						aEngine.Discard(null);
						remake(aRightPanel, aHumanPanel, aHumanTurnPanel);
					} else{
						if(aEngine.getWinner().equals("Human")){
							remake(aRightPanel, aWinPanel, makeNewPanel());
						} else{
							remake(aRightPanel, aLosePanel, makeNewPanel());
						}
					}
				} catch (EmptyDeckException e) {
					aEngine.newGame(aEngine.getHumanPlayer(), aEngine.getComputerPlayer());
				}
			}
		});
		
		aCompPanel.add(makeNewPanel());
		aCompPanel.add(aCompButton);
		aCompPanel.add(makeNewPanel());
		
		aRightPanel.add(aHumanPanel);
		aRightPanel.add(makeNewPanel());
		aPanel.add(aLog, BorderLayout.CENTER);
		aPanel.add(aMiddlePanel, BorderLayout.CENTER);
		aPanel.add(aRightPanel, BorderLayout.CENTER);
		aEngine.newGame(new HumanPlayer("HUMAN"), new ComputerPlayer("COMP"));
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLocationRelativeTo(null);
		pack();
		setVisible( true );
	}
	
	public static void main(String args[]){
		new GameFrame();
	}
}
