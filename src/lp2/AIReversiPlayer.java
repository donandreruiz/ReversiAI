package lp2;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import lp2.ConnectFour.Color;
import lp2.GameState.Player;
import lp2.GameState.Status;

public class AIReversiPlayer implements GamePlayer<Pair<Integer>> {
	private Player me;
	public HashMap<Reversi, Integer> transpTable;
	
	
	public AIReversiPlayer(Player p) {
		me = p;
	}

	@Override
	public String getName() {
		// TODO Replace with a name for your AI (school appropriate please)
		return "El Papi";
	}
	
	private int eval(Reversi gs) {
		int[][] evalTable = {
	            { 50, -1, 5, 2, 2, 5, -1, 50 },
	            { -1, -10, 1, 1, 1, 1, -10, -1 },
	            { 5, 1, 1, 1, 1, 1, 1, 5 },
	            { 2, 1, 1, 0, 0, 1, 1, 2 },
	            { 2, 1, 1, 0, 0, 1, 1, 2 },
	            { 5, 1, 1, 1, 1, 1, 1, 5 },
	            { -1, -10, 1, 1, 1, 1, -10, -1 },
	            { 50, -1, 5, 2, 2, 5, -1, 50 } };

	   
	   	int sum = 0;
		Color myPlayer = Color.BLACK;
		Color opp = Color.BLANK;
		
		if(me != Player.ONE){
			myPlayer = Color.BLANK;
			opp = Color.BLACK;
		}
	
		
		for (int i = 0; i < gs.HEIGHT-1; i++){
			for (int j = 0; j < gs.WIDTH-1; j++){
				if (gs.getSquare(i, j).equals(myPlayer)){
					sum += evalTable[i][j];
				}else if (gs.getSquare(i, j).equals(opp)){
					sum -= evalTable[i][j];
				}
			}
		}
		return sum;
	}
	
	
	@Override
	public Pair<Integer> getMove(GameState<Pair<Integer>> gs) {
		if(!(gs instanceof Reversi))
			throw new IllegalArgumentException("Cannot play non-Reversi game.");
		Reversi r = (Reversi)gs;
		int best = Integer.MIN_VALUE;
		Pair<Integer> move = null;
		long endTime = System.currentTimeMillis();
		long startTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		int depth = 0;
		
		// TODO Implement your Reversi AI's behavior
		while(duration < 4000){
			List<Pair<Integer>> moves  = gs.getMoves();
			for(Pair<Integer> neighborMove : moves){
				
				Reversi copyStates = (Reversi) r.copyState();
				copyStates.playMove(neighborMove);
				long time = System.currentTimeMillis();
				int score = alphabeta(copyStates, false, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, startTime, time);
		
				if(score >= best){
					best = score;
					move = neighborMove;
				}
			}
			endTime = System.currentTimeMillis();
			duration = endTime - startTime;
			
		}
		return move;
	}
	
	
	public int alphabeta(Reversi gs, boolean myTurn, int d, int alpha, int beta, long startTime, long endTime){
		if(endTime - startTime > 4500){
			return Integer.MIN_VALUE;
		}
		
		int v;
		
		if(gs.getGameStatus() != Status.ONGOING || d == 0){
			return eval(gs);
		}
		
		
		if(!transpTable.isEmpty()){
			if(transpTable.containsKey(gs)){
				return transpTable.get(gs);
			}
		}
		
		if(gs.currentPlayer() == me){
			myTurn = true;
		}else{
			myTurn = false;
		}
		
		if(myTurn){
			v = Integer.MIN_VALUE;
			List<Pair<Integer>> moves  = gs.getMoves();
			for(Pair<Integer> neighborMove : moves){
				Reversi copyStates = (Reversi) gs.copyState();
				copyStates.playMove(neighborMove);
				v = Math.max(v, alphabeta(copyStates,myTurn,d-1,alpha, beta, startTime, System.currentTimeMillis()));
				alpha  = Math.max(alpha, v);
				if(beta <= alpha){
					break;
				}
			}
			transpTable.put(gs, v);
			return v;
		}else{
			v = Integer.MAX_VALUE;
			List<Pair<Integer>> moves  = gs.getMoves();
			for(Pair<Integer> neighborMove : moves){
				Reversi copyStates = (Reversi) gs.copyState();
				copyStates.playMove(neighborMove);
				v = Math.min(v, alphabeta(copyStates,myTurn,d-1,alpha, beta, startTime, System.currentTimeMillis()));
				beta  = Math.min(beta, v);
				if(beta <= alpha){
					break;
				}
			}
			transpTable.put(gs, v);
			return v;
		}
	}
}
