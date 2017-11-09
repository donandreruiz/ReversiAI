package lp2;

import java.util.List;
import java.util.Random;

import lp2.GameState.Player;
import lp2.GameState.Status;

public class AIReversiPlayer implements GamePlayer<Pair<Integer>> {
	private Player me;
	
	public AIReversiPlayer(Player p) {
		me = p;
	}

	@Override
	public String getName() {
		// TODO Replace with a name for your AI (school appropriate please)
		return "El Papi";
	}
	
	private int eval(Reversi gs) {
		// TODO Implement a board evaluation function
		Random rand = new Random(); 
		int value = rand.nextInt(100); 
		return value;
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
		if(endTime - startTime > 5000){
			return Integer.MIN_VALUE;
		}
		
		int v;
		if(gs.getGameStatus() != Status.ONGOING || d == 0){
			return eval(gs);
		}
		
		if(myTurn){
			v = Integer.MIN_VALUE;
			List<Pair<Integer>> moves  = gs.getMoves();
			for(Pair<Integer> neighborMove : moves){
				Reversi copyStates = (Reversi) gs.copyState();
				copyStates.playMove(neighborMove);
				v = Math.max(v, alphabeta(copyStates,whosTurn(copyStates),d-1,alpha, beta, startTime, System.currentTimeMillis()));
				alpha  = Math.max(alpha, v);
				if(beta <= alpha){
					break;
				}
			}
			return v;
		}else{
			v = Integer.MAX_VALUE;
			List<Pair<Integer>> moves  = gs.getMoves();
			for(Pair<Integer> neighborMove : moves){
				Reversi copyStates = (Reversi) gs.copyState();
				copyStates.playMove(neighborMove);
				v = Math.min(v, alphabeta(copyStates,whosTurn(copyStates),d-1,alpha, beta, startTime, System.currentTimeMillis()));
				beta  = Math.min(beta, v);
				if(beta <= alpha){
					break;
				}
			}
			return v;
		}
	}
	
	public boolean whosTurn(Reversi gs){
		if(gs.currentPlayer() == me && gs.getMoves().isEmpty()){
			return false;
		}else if(gs.currentPlayer() != me && gs.getMoves().isEmpty()){
			return true;
		}else if(gs.currentPlayer() == me && !gs.getMoves().isEmpty()){
			return true;
		}
		
		return false;
	}
}
