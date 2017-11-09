package lp2;

import java.util.Scanner;

import lp2.GameState.Player;

public class HumanRevPlayer implements GamePlayer<Pair<Integer>> {
	private Player me;
	private String name;
	private Scanner s;
	
	public HumanRevPlayer(Player p, String n) {
		me = p;
		name = n;
		s = new Scanner(System.in);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Pair<Integer> getMove(GameState<Pair<Integer>> gs) {
		if(! (gs instanceof Reversi))
			throw new IllegalArgumentException("Cannot play non-Reversi game.");

		Pair<Integer> move;
		do {
			System.out.println("Human player " + (me == Player.ONE ? "White" : "Black") + "'s turn");
			System.out.println("Available columns for moves:" + gs.getMoves());
			System.out.println("What move do you want to play?");
			int moveRow = s.nextInt();
			int moveCol = s.nextInt();
			move = new Pair<Integer>(moveRow,moveCol);
		} while(!gs.getMoves().contains(move));
		
		return move;
	}

}
