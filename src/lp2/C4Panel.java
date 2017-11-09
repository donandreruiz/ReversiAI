package lp2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class C4Panel extends JPanel {
	private static final int SQUARESIZE = 100;
	
	private ConnectFour board;
	
	public C4Panel(ConnectFour board) {
		super();
		this.board = board;
		setPreferredSize(new Dimension(SQUARESIZE*ConnectFour.WIDTH,SQUARESIZE*ConnectFour.HEIGHT));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Draw a background
		g.setColor(new Color(170, 210, 250)); //Light blue
		g.fillRect(0, 0, SQUARESIZE*ConnectFour.WIDTH, SQUARESIZE*ConnectFour.HEIGHT);
		
		// Draw lines to delimit the board squares
		g.setColor(Color.GRAY);
		for(int i = 1; i < ConnectFour.WIDTH; i++) {
			g.drawLine(SQUARESIZE*i, 0, SQUARESIZE*i, SQUARESIZE*ConnectFour.HEIGHT);
		}
		for(int j = 1; j < ConnectFour.HEIGHT; j++) {
			g.drawLine(0, SQUARESIZE*j, SQUARESIZE*ConnectFour.WIDTH, SQUARESIZE*j);
		}
		
		// Draw the circles for the game pieces
		for(int i = 0; i < ConnectFour.WIDTH; i++) {
			for(int j = 0; j < ConnectFour.HEIGHT; j++) {
				switch(board.getSquare(i,j)) {
					case RED:
						g.setColor(Color.RED);
						g.fillOval(SQUARESIZE*i+2, SQUARESIZE*(ConnectFour.HEIGHT-j-1)+2, SQUARESIZE - 4, SQUARESIZE - 4);
						break;
					case BLACK:
						g.setColor(Color.BLACK);
						g.fillOval(SQUARESIZE*i+2, SQUARESIZE*(ConnectFour.HEIGHT-j-1)+2, SQUARESIZE - 4, SQUARESIZE - 4);
						break;
					default:
						break;
				}
			}
		}
		
	}
}
