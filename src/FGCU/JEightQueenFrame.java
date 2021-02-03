package FGCU;
/**
 * This file has two classes for the 8 queens problem. See instructions
 * as well as comments in this code.
 * The GUI code is given as is, it is secondary to the task
 * plus the concepts are previous to this course.
 * We will concentrate on the recursion:
 * how it works / tracing the steps and calls, detecting conflicts etc. 
 */
 
import java.util.*;
import java.awt.*;
import javax.swing.*;

/**
 * This class is used to draw a chess board with queens.
 * Where the queens are placed on the board is controlled by
 * the instance variable: boolean board[][]. The size of board is 8x8.
 * Each element in board indicates if a queen is there. For example,
 * if board[4][5]==true, there is a queen at row 4 and column 5.
 * 
 * The value of this 2D array is passed in at the constructor. 
 * This class does not decide which cells have queens.
 */
class JChessBoardPanel extends JPanel{
	
	// this variable is used to indicate which cell has a queen
	private boolean board[][];
	
	// the user should pass a 2D boolean array to tell
	// which cells have queens
	public JChessBoardPanel(boolean board[][]){
		super();
		this.board=board;
	}
	
	private boolean equ(boolean p1, boolean p2){
		return (!p1&&!p2) || (p1&&p2); 
	}
	
	public void drawChessBoard(Graphics g){
		g.setColor(Color.BLACK);
		int height=getHeight(), width=getWidth();
		int cellHeight=height/8;
		int cellWidth=width/8;
		int i,j; // controls loops

		//drawing color for the cells
		for(i=0;i<8;i++)//rows
			for(j=0;j<8;j++){//columes
				if(equ(i%2==0,j%2==0)){
					g.fillRect(j*cellWidth,i*cellHeight,
						cellWidth,cellHeight);
				}
			}
		
	}
	////////////////////////////////////////////////////////////
	// this method draws the queens as circles based on the 
	// values equal to true in the board[]
	public void drawQueens(Graphics g){
		int i,j;
		g.setColor(Color.RED);
		int height=getHeight();
		int width=getWidth();
		int cellHeight=height/8;
		int cellWidth=width/8;
		
		for(i=0;i<8;i++)//rows
			for(j=0;j<8;j++){//columns
				if(board[i][j]){
					int centerX,centerY,radius;
					centerX=cellWidth*j+cellWidth/4;
					centerY=cellHeight*i+cellHeight/4;
					radius=cellWidth/2;
					g.fillOval(centerX,centerY,radius,radius);
				}
			}
	}
	
	public void paint(Graphics g){
		super.paint(g);
		drawChessBoard(g);
		drawQueens(g);
	}
}

/*
 * This JFrame-based class is the container of JChessBoardPanel.
 * When the user creates a JEightQueenFrame object, he/she is supposed
 * to pass a 2D (8x8) boolean array, board, to tell where the queens should be
 * placed.  The size of board is 8x8. Each element in board indicates if a 
 * queen is there. For example, if board[4][5]==true, there is a queen at 
 * row 4 and column 5.
 */
public class JEightQueenFrame extends JFrame {
	private boolean board[][];
	private JChessBoardPanel chessBoard;
		
	public JEightQueenFrame(boolean board[][]){

		super();

		this.board=board;
		
		//setting the layout
		getContentPane().setLayout(new BorderLayout());
		
		//adding the ChessBoard
		getContentPane().add(new JChessBoardPanel(board),
			BorderLayout.CENTER);
		
		//other settings
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(590,610);
		setResizable(false);
		setVisible(true);
	}
	
	////////////////////////////////////////////////////////////
	//check to see if we can place a queen at (x, y)
	// x is column, y is row
	// TODO understand this method and how it detects conflicts
	// as well as how it is used by trial() 
	public static boolean good(boolean board[][], int x, int y){
		int i=0,j=0;
		
		for(i=0;i<y;i++){//rows
			for(j=0;j<8;j++)//cols
			
				//if there is a queen at (i,j)
				if(board[i][j]){	
					//if same column  
					//OR diagonal conflict
					if(j==x || Math.abs(j-x) == (y-i))
						return false;
				}

			}
		return true;
	}
	
	////////////////////////////////////////////////////////////
	// recursive method that uses backtracking
	// recursively attempts to place queens on the board 
	// so that there are no conflicts (row, col, diagonals)
	// n is the row we are currently attempting to place a queen
	// TODO understand this method and how it works, tracing, etc.
	public static boolean trial(boolean board[][],int n){
		for(int i=0;i<8;i++){
			board[n][i]=true;
			if(n==7 && good(board,i,n))
				return true;
			if(n<7 && good(board,i,n) && trial(board,n+1))
				return true;
			board[n][i]=false;
		}
		return false;
	}
		
	////////////////////////////////////////////////////////////	
	public static void main(String args[]){
		//the board 8x8 initially set all to false
		boolean [][]board = new boolean[8][8];		
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++){
				board[i][j]=false;
			}
		
		//see if we can place a queen at row 0
		trial(board, 0);

		// show the board with the queens
		JEightQueenFrame queenFrame = new JEightQueenFrame(board);
	}
}

