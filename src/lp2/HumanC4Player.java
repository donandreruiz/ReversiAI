package lp2;

import java.util.Scanner;

import lp2.GameState.Player;

public class HumanC4Player implements GamePlayer<Integer> {

	private Scanner s;
	private Player me;
	private String name;
	
	public HumanC4Player(Player p, String n) {
		s = new Scanner(System.in);
		me = p;
		name = n;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Integer getMove(GameState<Integer> gs) {
		if(! (gs instanceof ConnectFour))
			throw new IllegalArgumentException("Cannot play non-Connect four game.");
		
		int move;
		do {
			System.out.println("Human player " + (me == Player.ONE ? "Red" : "Black") + "'s turn");
			System.out.println("Available columns for moves:" + gs.getMoves());
			System.out.println("What move do you want to play? ");
			move = s.nextInt();
		} while(!gs.getMoves().contains(move));
		
		return move;
	}
}
