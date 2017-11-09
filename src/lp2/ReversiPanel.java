package lp2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ReversiPanel extends JPanel {

	private static final int SQUARESIZE = 100;
	
	private Reversi board;
	
	public ReversiPanel(Reversi board) {
		super();
		this.board = board;
		setPreferredSize(new Dimension(SQUARESIZE*board.WIDTH,SQUARESIZE*board.HEIGHT));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Draw a background
		g.setColor(new Color(50, 150, 50)); //Dark green
		g.fillRect(0, 0, SQUARESIZE*board.WIDTH, SQUARESIZE*board.HEIGHT);
		
		// Draw lines to delimit the board squares
		g.setColor(Color.GRAY);
		for(int i = 1; i < board.WIDTH; i++) {
			g.drawLine(SQUARESIZE*i, 0, SQUARESIZE*i, SQUARESIZE*board.HEIGHT);
		}
		for(int j = 1; j < board.HEIGHT; j++) {
			g.drawLine(0, SQUARESIZE*j, SQUARESIZE*board.WIDTH, SQUARESIZE*j);
		}
		
		// Draw the circles for the game pieces
		for(int i = 0; i < board.WIDTH; i++) {
			for(int j = 0; j < board.HEIGHT; j++) {
				switch(board.getSquare(i,j)) {
					case WHITE:
						g.setColor(Color.WHITE);
						g.fillOval(SQUARESIZE*i+2, SQUARESIZE*(board.HEIGHT-j-1)+2, SQUARESIZE - 4, SQUARESIZE - 4);
						break;
					case BLACK:
						g.setColor(Color.BLACK);
						g.fillOval(SQUARESIZE*i+2, SQUARESIZE*(board.HEIGHT-j-1)+2, SQUARESIZE - 4, SQUARESIZE - 4);
						break;
					default:
						break;
				}
			}
		}
		
	}
}
