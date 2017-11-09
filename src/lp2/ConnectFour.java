package lp2;

import java.util.ArrayList;
import java.util.List;

public class ConnectFour implements GameState<Integer> {
	public static enum Color{BLANK, RED, BLACK};
	public static final int WIDTH = 7;
	public static final int HEIGHT = 6;
	
	private Color[][] board;
	private Player turn;
	
	private static Status colorToResult(Color c) {
		if(c == Color.BLANK)
			throw new IllegalArgumentException("Blank does not have a corresponding result");
		return c == Color.BLACK ? Status.TWOWIN : Status.ONEWIN;
	}
	
	private static Color playerToColor(Player p) {
		return p == Player.ONE ? Color.RED : Color.BLACK;
	}
	
	public ConnectFour() {
		this.board = new Color[WIDTH][HEIGHT];
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				this.board[i][j] = Color.BLANK;
			}
		}
		turn = Player.ONE;
	}
	
	public ConnectFour(ConnectFour c4) {
		this.board = new Color[WIDTH][HEIGHT];
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				this.board[i][j] = c4.board[i][j];
			}
		}
		this.turn = c4.turn;
	}
	
	public GameState<Integer> copyState() {
		return new ConnectFour(this);
	}
	
	/*
	 * Returns the moves that could be played in a position.
	 * An empty list if the game is over.
	 */
	public List<Integer> getMoves() {
		List<Integer> moves = new ArrayList<Integer>();
		
		if (getGameStatus() != Status.ONGOING)
			return moves;
		
		// A move can be played in a column if there is an empty space
		for(int i = 0; i < WIDTH; i++) {
			if(board[i][HEIGHT - 1] == Color.BLANK) {
				moves.add(i);
			}
		}
		
		return moves;
	}
	
	public Player currentPlayer() {
		return turn;
	}
	
	public void playMove(Integer i) {
		if(board[i][HEIGHT - 1] != Color.BLANK)
			throw new IllegalArgumentException("Cannot play in a filled column: " + i);
		
		// Find lowest blank square
		int j = HEIGHT - 1;
		while (j > 0 && board[i][j-1] == Color.BLANK) {
			j--;
		}

		// Play the move and switch the turn
		board[i][j] = playerToColor(turn);
		if(turn == Player.ONE) {
			turn = Player.TWO;
		} else {
			turn = Player.ONE;
		}
	}
	
	private boolean validSquare(int i, int j) {
		return i >=0 && i < WIDTH && j >= 0 && j < HEIGHT;
	}
	
	public Color getSquare(int i, int j) {
		if (!validSquare(i,j))
			throw new IllegalArgumentException("Square index out of bounds: (" + i + "," + j + ")");
		
		return board[i][j];
	}
	
	public Status getGameStatus() {
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				if(board[i][j] != Color.BLANK) {
					Color c = board[i][j];
					
					//Check down
					if(j >= 3) {
						if (c == board[i][j-1] && c == board[i][j-2] && c == board[i][j-3]) {
							return colorToResult(c);
						}
					}
					//Check right
					if(i+4 <= WIDTH) {
						if (c == board[i+1][j] && c == board[i+2][j] && c == board[i+3][j])
							return colorToResult(c);
					}
					//Check down/right
					if(j >= 3 && i+4 <= WIDTH) {
						if (c == board[i+1][j-1] && c == board[i+2][j-2] && c == board[i+3][j-3])
							return colorToResult(c);
					}
					//Check down/left
					if(j >= 3 && i >= 3) {
						if (c == board[i-1][j-1] && c == board[i-2][j-2] && c == board[i-3][j-3])
							return colorToResult(c);
					}
				}
			}
		}
		
		for(int i = 0; i < WIDTH; i++) {
			if(board[i][HEIGHT - 1] == Color.BLANK) {
				return Status.ONGOING;
			}
		}
		
		return Status.DRAW;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof ConnectFour))
			return false;
		ConnectFour c4 = (ConnectFour) o;
		
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				if(board[i][j] != c4.board[i][j])
					return false;
			}
		}
		return turn == c4.turn;
	}
	
	@Override
	public int hashCode() {
		int sum = 0;
		
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				sum *= 11;
				switch(board[i][j]) {
				case BLACK:
					sum += 2;
					break;
				case RED:
					sum += 3;
					break;
				default:
					sum += 5;
					break;
				
				}
			}
		}
		
		sum += (turn == Player.ONE ? 37 : 41);
		return sum;
	}
	

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("Turn: " + (turn == Player.ONE ? "R" : "B") + "\n");
		sb.append("Board:\n");
		for(int j = HEIGHT-1; j >= 0; j--) {
			for(int i = 0; i < WIDTH; i++) {
				switch(board[i][j]) {
				case BLACK:
					sb.append('B');
					break;
				case RED:
					sb.append('R');
					break;
				default:
					sb.append('.');
					break;
				}
			}
			sb.append('\n');
		}
		
		return sb.toString();
	}
}
