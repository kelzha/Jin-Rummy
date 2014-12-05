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
	
	public GameFrame(){
		setTitle("Jin Rummy");
		setLayout(new BorderLayout());
		JPanel aPanel = new JPanel();
		aPanel.setLayout(new GridLayout(1, 3));
		add(aPanel, BorderLayout.CENTER);
		
		JPanel aMiddlePanel = makeCenterPanel(3, 1);
		
		JPanel aRightPanel = makeCenterPanel(3, 1);
		aRightPanel.add(makeNewPanel());
		
		CardPanel aPlayer1 = new CardPanel("Human");
		CardPanel aPlayer2 = new CardPanel("Computer");
		aEngine.addCardPanel(aPlayer1);
		aMiddlePanel.add(aPlayer1);
		aEngine.addCardPanel(aPlayer2);
		
		LogPanel aLog = new LogPanel();
		aEngine.addObserver(aLog);
		ShowPanel aDiscard = new ShowPanel();
		aMiddlePanel.add(aDiscard);
		aEngine.addObserver(aDiscard);
		aMiddlePanel.add(aPlayer2);
		
		JButton aButton = new JButton("Next Turn");
		aButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try {
					aEngine.nextTurn();
				} catch (EmptyDeckException e) {
					aEngine.newGame(aEngine.getHumanPlayer(), aEngine.getComputerPlayer());
				}
			}
		});
		
		aRightPanel.add(aButton);
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
