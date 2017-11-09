package lp2;

import java.util.List;
import lp2.ConnectFour.Color;
import lp2.GameState.Player;
import lp2.GameState.Status;

public class AIC4PlayerRuiz2 implements GamePlayer<Integer> {
	private Player me;
	
	public AIC4PlayerRuiz2(Player p) {
		me = p;
	}

	@Override
	public String getName() {
		return "papi2";
	}
	
	private int eval(ConnectFour gs) {
		
	   int[][] evalTable = {{3, 4, 5, 7, 5, 4, 3}, 
	            {4, 6, 8, 10, 8, 6, 4},
	            {5, 8, 11, 13, 11, 8, 5}, 
	            {5, 8, 11, 13, 11, 8, 5},
	            {4, 6, 8, 10, 8, 6, 4},
	            {3, 4, 5, 7, 5, 4, 3}};

		int sum = 0;
		Color myPlayer;
		Color opp;
		
		if(gs.currentPlayer() == me && me == Player.ONE){
			myPlayer = Color.RED;
			opp = Color.BLACK;
		}else{
			opp = Color.RED;
			myPlayer = Color.BLACK;
		}
		
		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 6; j++){
				if (gs.getSquare(i, j) == Color.RED){
					sum += evalTable[i][j];
				}else if (gs.getSquare(i, j) == Color.BLACK){
					sum -= evalTable[i][j];
				}
			}
		}
		return sum;
	}
	
	@Override
	public Integer getMove(GameState<Integer> gs) {
		int depth = 6;
		int best = Integer.MIN_VALUE;
		if(!(gs instanceof ConnectFour))
			throw new IllegalArgumentException("Cannot play non-Connect four game.");
		ConnectFour c4 = (ConnectFour)gs;
		
		
			System.out.println("AI " + (me == Player.ONE ? "Red" : "Black") + "'s turn");
			List<Integer> listMoves = c4.getMoves();
			int move = listMoves.get(0);
			for(Integer moves: listMoves){
				 ConnectFour copyStates = (ConnectFour) c4.copyState();
				 copyStates.playMove(moves);
				 int v = minimax(copyStates, false, depth);
				 System.out.println(v + " this is v");
				 System.out.println(best + " this is best");
				 if(v > best){
					 best = v;
					 move = moves;
					 System.out.println(move + " this is move");
				 }
			 }
		
		return move;
	}

	
	public int minimax(ConnectFour s, Boolean myTurn, int depth){
		int best;
		
		if(s.getGameStatus() == Status.DRAW){
			return 0;
		}else if((s.getGameStatus() == Status.ONEWIN && me == Player.ONE) || (s.getGameStatus() == Status.TWOWIN && me == Player.TWO )){
			return Integer.MAX_VALUE - depth - 1;
		}else if((s.getGameStatus() == Status.TWOWIN && me == Player.ONE) || (s.getGameStatus() == Status.ONEWIN && me == Player.TWO)){
			return Integer.MIN_VALUE + depth - 1;
		}
		 if(depth == 0){
			 return eval(s);
		 }
		 if(myTurn){
			 best  = Integer.MIN_VALUE;
			 List<Integer> listMoves = s.getMoves();
			 for(Integer moves: listMoves){
				 ConnectFour copyStates = (ConnectFour) s.copyState();
				 copyStates.playMove(moves);
				 int v = minimax(copyStates, false, depth -1);
				 if(v > best){
					 best = v;
				 }
			 }
			 return best;
		 }else{
			 best = Integer.MAX_VALUE;
			 List<Integer> listMoves = s.getMoves();
			 for(Integer moves: listMoves){
				 ConnectFour copyStates = (ConnectFour) s.copyState();
				 copyStates.playMove(moves);
				 int v = minimax(copyStates, true, depth -1);
				 if(v < best){
					 best = v;
				 }
			 }
			 return best;
		 }		
	}
	
}
