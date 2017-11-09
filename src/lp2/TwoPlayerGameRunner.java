package lp2;

import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import lp2.GameState.Player;
import lp2.GameState.Status;

public class TwoPlayerGameRunner<T> implements GameRunner {
	private GameState<T> game;
	private GamePlayer<T> p1;
	private GamePlayer<T> p2;
	private JFrame f;
	
	public TwoPlayerGameRunner(GameState<T> game, GamePlayer<T> p1, GamePlayer<T> p2, JFrame f) {
		this.game = game;
		this.p1 = p1;
		this.p2 = p2;
		this.f = f;
	}
	
	public void runGame() {
		while(game.getGameStatus() == Status.ONGOING) {
//			Scanner s = new Scanner(System.in);
//			s.nextLine();
			T move;
			if(game.currentPlayer() == Player.ONE) {
				move = p1.getMove(game.copyState());
			} else {
				move = p2.getMove(game.copyState());
			}
			
			try {
				game.playMove(move);
			} catch (IllegalArgumentException e) {
				System.out.println(e);
				System.out.println("Illegal move played by " + (game.currentPlayer() == Player.ONE ? "Player 1" : "Player 2"));
				System.out.println("Winner is " + (game.currentPlayer() == Player.ONE ? "Player 2" : "Player 1") + "!");
				return;
			}
			
			// Redraw the UI after each move is played
			SwingUtilities.updateComponentTreeUI(f);
		}
		
		Status res = game.getGameStatus();
		if(res == Status.DRAW)
			System.out.println("The game has ended in a draw");
		else
			System.out.println("The winner is " + (res == Status.ONEWIN ? p1.getName() : p2.getName()) + "!");
	}
}
