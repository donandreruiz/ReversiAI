package lp2;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import lp2.GameState.Player;

public class LP2 {
	public enum GameName {CONNECTFOUR, REVERSI};
	
	public static final String c4Name = "Connect Four";
	public static final String revName = "Reversi";
	
	//public static final GameName gameToPlay = GameName.CONNECTFOUR;
	public static final GameName gameToPlay = GameName.REVERSI;
	
	public static void main(String[] args) {
		String title;
		JPanel gamePanel;
		GameRunner gr;
		
		//Set up the window
		JFrame f = new JFrame();
		
		//Set up the game to be played
		if(gameToPlay == GameName.CONNECTFOUR) {
//			GamePlayer<Integer> p1 = new HumanC4Player(Player.ONE, "Player One");
			//GamePlayer<Integer> p2 = new HumanC4Player(Player.TWO, "Player Two");
			GamePlayer<Integer> p1 = new AIC4PlayerRuiz(Player.ONE);
			GamePlayer<Integer> p2 = new AIC4PlayerRuiz(Player.TWO);
			ConnectFour c4Game = new ConnectFour();
			
			gr = new TwoPlayerGameRunner<Integer>(c4Game, p1, p2, f);
			title = c4Name;
			gamePanel = new C4Panel(c4Game);
		} else {
			GamePlayer<Pair<Integer>> p1 = new HumanRevPlayer(Player.ONE, "Player One");
			//GamePlayer<Pair<Integer>> p2 = new HumanRevPlayer(Player.TWO, "Player Two");
			//GamePlayer<Pair<Integer>> p1 = new AIReversiPlayer(Player.ONE);
			GamePlayer<Pair<Integer>> p2 = new AIReversiPlayer(Player.TWO);
			Reversi revGame = new Reversi();
			
			gr = new TwoPlayerGameRunner<Pair<Integer>>(revGame, p1, p2, f);
			title = revName;
			gamePanel = new ReversiPanel(revGame);
		}
		

		f.setTitle(title);
		f.setLocation(50, 50);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.getContentPane().add(gamePanel);
		f.pack();
		f.setVisible(true);
		
		//Start the game
		gr.runGame();
	}
}
