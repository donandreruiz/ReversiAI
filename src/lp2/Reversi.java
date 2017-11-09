package lp2;

import java.util.ArrayList;
import java.util.List;

public class Reversi implements GameState<Pair<Integer>>{
	public enum Color{BLANK, WHITE, BLACK};
	public final int WIDTH = 8;
	public final int HEIGHT = 8;
	
	private Color[][] board;
	private Player turn;
	
	private static Status colorToResult(Color c) {
		if(c == Color.BLANK)
			throw new IllegalArgumentException("Blank does not have a corresponding result");
		return c == Color.BLACK ? Status.TWOWIN : Status.ONEWIN;
	}
	
	private static Color playerToColor(Player p) {
		return p == Player.ONE ? Color.WHITE : Color.BLACK;
	}
	
	public Reversi() {
		this.board = new Color[WIDTH][HEIGHT];
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				this.board[i][j] = Color.BLANK;
			}
		}
		
		this.board[3][3] = Color.WHITE;
		this.board[3][4] = Color.BLACK;
		this.board[4][3] = Color.BLACK;
		this.board[4][4] = Color.WHITE;
		
		turn = Player.ONE;
	}
	
//	public Reversi(Reversi r) {
//		this.board = r.board;
//		this.turn = r.turn;
//	}
	
	public Reversi(Reversi rev) {
        this.board = new Color[WIDTH][HEIGHT];
        for(int i = 0; i < WIDTH; i++) {
            for(int j = 0; j < HEIGHT; j++) {
                this.board[i][j] = rev.board[i][j];
            }
        }
        this.turn = rev.turn;
    }
	
	public GameState<Pair<Integer>> copyState() {
		return new Reversi(this);
	}
	
	@Override
	public Player currentPlayer() {
		return turn;
	}

	@Override
	public List<Pair<Integer>> getMoves() {
		return getMovesForPlayer(turn);
	}
	
	private boolean validSquare(int i, int j) {
		return i >=0 && i < WIDTH && j >= 0 && j < HEIGHT;
	}
	
	private boolean moveSearch(int i, int j, int di, int dj, Color start, Color opp) {
		//If the square one move in the direction is the opponent's color
		if(validSquare(i+di,j+dj) && board[i+di][j+dj] == opp) {
			int x = i + di;
			int y = j + dj;
			
			//Keep moving until at a square that is not the opponent's
			while(validSquare(x,y) && board[x][y] == opp) {
				x+=di;
				y+=dj;
			}
			
			//If that square is the current player's, (i,j) is a valid move
			if(validSquare(x,y) && board[x][y] == start)
				return true;
		}
		return false;
	}
	
	private List<Pair<Integer>> getMovesForPlayer(Player p) {
		List<Pair<Integer>> moves = new ArrayList<Pair<Integer>>();
		Color pc = playerToColor(p);
		Color oppc = pc == Color.BLACK ? Color.WHITE : Color.BLACK;
		
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				if(board[i][j] != Color.BLANK)
					continue;
				
				//Search left
				if (moveSearch(i,j,-1,0,pc,oppc))
					moves.add(new Pair<Integer>(i,j));
				
				//Search up and left
				if (moveSearch(i,j,-1,1,pc,oppc))
					moves.add(new Pair<Integer>(i,j));
				
				//Search up
				if (moveSearch(i,j,0,1,pc,oppc))
					moves.add(new Pair<Integer>(i,j));
				
				//Search up and right
				if (moveSearch(i,j,1,1,pc,oppc))
					moves.add(new Pair<Integer>(i,j));
				
				//Search right
				if (moveSearch(i,j,1,0,pc,oppc))
					moves.add(new Pair<Integer>(i,j));
				
				//Search down and right
				if (moveSearch(i,j,1,-1,pc,oppc))
					moves.add(new Pair<Integer>(i,j));
				
				//Search down
				if (moveSearch(i,j,0,-1,pc,oppc))
					moves.add(new Pair<Integer>(i,j));
				
				//Search down and left
				if (moveSearch(i,j,-1,-1,pc,oppc))
					moves.add(new Pair<Integer>(i,j));
			}
		}
		
		return moves;
	}
	
	private void flipSquares(int i, int j, int di, int dj, Color pc, Color opp) {
		int x = i + di;
		int y = j + dj;
					
		//Keep moving until at a square that is not the opponent's
		while(validSquare(x,y) && board[x][y] == opp) {	
			board[x][y] = pc;
			x+=di;
			y+=dj;
		}
	}

	@Override
	public void playMove(Pair<Integer> move) {
		int i = move.getFirst();
		int j = move.getSecond();
		
		if(!validSquare(i,j))
			throw new IllegalArgumentException("Must play inside the board " + move);
		if(board[i][j] != Color.BLANK)
			throw new IllegalArgumentException("Cannot play on top of another square " + move);
		
		Color pc = playerToColor(turn);
		Color oppc = pc == Color.BLACK ? Color.WHITE : Color.BLACK;
		
		board[i][j] = pc;
		
		//For each direction, if there is a sequence of oppc circles followed by a pc circle
		// then flip all of the oppc circles
		
		//Search left
		if (moveSearch(i,j,-1,0,pc,oppc)){
			flipSquares(i,j,-1,0,pc,oppc);
		}
		
		//Search up and left
		if (moveSearch(i,j,-1,1,pc,oppc)) {
			flipSquares(i,j,-1,1,pc,oppc);
		}
		
		//Search up
		if (moveSearch(i,j,0,1,pc,oppc)) {
			flipSquares(i,j,0,1,pc,oppc);
		}
		
		//Search up and right
		if (moveSearch(i,j,1,1,pc,oppc)) {
			flipSquares(i,j,1,1,pc,oppc);
		}
		
		//Search right
		if (moveSearch(i,j,1,0,pc,oppc)) {
			flipSquares(i,j,1,0,pc,oppc);
		}
		
		//Search down and right
		if (moveSearch(i,j,1,-1,pc,oppc)) {
			flipSquares(i,j,1,-1,pc,oppc);
		}
		
		//Search down
		if (moveSearch(i,j,0,-1,pc,oppc)) {
			flipSquares(i,j,0,-1,pc,oppc);
		}
		
		//Search down and left
		if (moveSearch(i,j,-1,-1,pc,oppc)) {
			flipSquares(i,j,-1,-1,pc,oppc);
		}
		
		//Switch to the other player's turn
		turn = turn == Player.ONE ? Player.TWO : Player.ONE;
		
		//If the next player has no moves, they are skipped
		if(getMovesForPlayer(turn).isEmpty())
			turn = turn == Player.ONE ? Player.TWO : Player.ONE;
	}

	@Override
	public Status getGameStatus() {
		//The game is over when there are no more moves
		if(getMoves().size() != 0) {
			return Status.ONGOING;
		}
		
		//Count the squares
		int whiteCount = 0;
		int blackCount = 0;
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				switch(board[i][j]) {
				case BLACK:
					blackCount++;
					break;
				case WHITE:
					whiteCount++;
					break;
				default:
					break;
				}
			}
		}
		
		//Whoever has the most squares wins
		if(whiteCount > blackCount)
			return colorToResult(Color.WHITE);
		else if(whiteCount == blackCount) {
			return Status.DRAW;
		} else { //whiteCount < blackCount
			return colorToResult(Color.BLACK);
		}
	}
	
	public Color getSquare(int i, int j) {
		if (!validSquare(i,j))
			throw new IllegalArgumentException("Square index out of bounds: (" + i + "," + j + ")");
		
		return board[i][j];
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Reversi))
			return false;
		Reversi rev = (Reversi) o;
		
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				if(board[i][j] != rev.board[i][j])
					return false;
			}
		}
		return turn == rev.turn;
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
				case WHITE:
					sum += 3;
					break;
				default:
					sum += 5;
					break;
				}
			}
		}
		
		sum += (turn == Player.ONE ? 37 : 0);
		return sum;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("Turn: " + (turn == Player.ONE ? "W" : "B") + "\n");
		sb.append("Board:\n");
		for(int j = HEIGHT-1; j >= 0; j--) {
			for(int i = 0; i < WIDTH; i++) {
				switch(board[i][j]) {
				case BLACK:
					sb.append('B');
					break;
				case WHITE:
					sb.append('W');
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
