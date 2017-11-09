package lp2;

import java.util.List;

public interface GameState<T> {
	public enum Player {ONE, TWO};
	public static enum Status {ONGOING, DRAW, ONEWIN, TWOWIN};

	/*
	 * Creates a copy of the current game state
	 */
	public GameState<T> copyState();
	
	/*
	 * Returns the Player enum value corresponding to the current player
	 */
	public Player currentPlayer();
	
	/*
	 * Returns a list of the valid moves in the current position
	 */
	public List<T> getMoves();
	
	/*
	 * Plays the move t on the board, mutating the game state
	 */
	public void playMove(T t);
	
	/*
	 * Returns the Status enum value corresponding to the current status of the game
	 */
	public Status getGameStatus();
}
