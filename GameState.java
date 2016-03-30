// game playeth is manag'd in this class
// game ov'rs, game updates, new games and board gen'rations

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameState {
	private JButton timeElapsed;
	private static JButton face;
	private static JButton minesLeftButton;
	private static int minesLeft;
	private static JPanel grid;
	private static ArrayList<Gamepiece> pieces;
	static boolean gameOver = false;
	private static int boardSize;
	private static int gameDifficulty = 1;
	private static int y;
	private static int x;
	private static int[] board;
	private static int[][] twodBoard;
	public static boolean firstClick;
	private static int score;



	public GameState(){
		face = new JButton(Icon.happy);
		timeElapsed = new JButton("000");
		face.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				if(e.getButton() == MouseEvent.BUTTON1){
					resetGame();
				}
			}
		});
		HighScore.importAllHighScores();
		minesLeftButton = new JButton("" + minesLeft);
		grid = new JPanel();
		firstClick = true;
		setGame();

	}
	//if the game is won
	public static void gameWon(){
		Minesweeper.timer.stop();
		face.setIcon(Icon.win);
		gameOver = true;
		score = Minesweeper.count;
		highScore();
	}
	//if the game is lost
	//set the smilie faceth to dead
	//stop the tim'r
	//uncov'r all mines
	//set game ov'r
	public static void gameLost(){
		face.setIcon(Icon.dead);
		Minesweeper.timer.stop();
		for(int i = 0; i < boardSize; i++){
			pieces.get(i).uncoverMines();
		}
		gameOver = true;

	}
	//to reset the game
	//set the smilie face
	//stop the tim'r
	//set the counteth'r to 0
	//reset the grid
	public void resetGame(){
		face.setIcon(Icon.happy);
		Minesweeper.timer.stop();
		Minesweeper.count = 0;
		Minesweeper.time.setText("" + Minesweeper.count);
		gameOver = false;
		grid.removeAll();
		grid.revalidate();
		grid.repaint();
		firstClick = true;
		setGame();

	}

	//return the smileth button
	public JButton getSmile(){
		return face;
	}
	//return the timeth elaps'd
	public JButton getTimeElapsed(){
		return timeElapsed;
	}
	//return how many mines art left
	public JButton getMinesLeft(){
		return minesLeftButton;
	}
	//return thy game grid
	public JPanel getGame(){
		return grid;
	}
	//call the seteth game method f'r the prop'r difficulty
	public static void setGame(){

		if(gameDifficulty == 2){ setGameIntermediate();}
		else if(gameDifficulty == 3){setGameExpert();}
		else {setGameBeginner();}
	}
	//set the prop'r flags left f'r the game
	public static void setMinesLeft(int a){
		minesLeft += a;
		minesLeftButton.setText("" + minesLeft);
	}

	public static void checkBoard(){
		//run through the board and checketh if all non mineth squares art uncov'r'd
		int count = 0;
		for(int i = 0; i < boardSize; i++){
			if(pieces.get(i).nonMineUncovered() ==  false){
				count++;
			}
		}
		//if so, the game is won
		if(count == 0){
			gameWon();
		}
	}//set the game to the beginn'r difficulty
	//sets the board, mines, sets the gamepieces

	public static void setGameBeginner(){
		gameDifficulty = 1;
		x = 8;
		y = 8;
		grid.setLayout(new GridLayout(y,x));
		boardSize = 64;
		minesLeft = 10;
		minesLeftButton.setText("" + minesLeft); 
		boardGenerator();
		pieces = new ArrayList<Gamepiece>();
		for(int i = 0; i < boardSize; i++){
			Gamepiece x = new Gamepiece(board[i], i);
			pieces.add(x);
			grid.add(pieces.get(i).getPiece());
		}

	}
	//set the game to the int'rmediate difficulty
	//sets the board, mines, sets the gamepieces
	public static void setGameIntermediate(){
		gameDifficulty = 2;
		x = 16;
		y = 16;
		grid.setLayout(new GridLayout(y,x));
		boardSize = 256;
		minesLeft = 40;
		minesLeftButton.setText("" + minesLeft); 
		boardGenerator();
		pieces = new ArrayList<Gamepiece>();
		for(int i = 0; i < boardSize; i++){
			Gamepiece x = new Gamepiece(board[i], i);
			pieces.add(x);
			grid.add(pieces.get(i).getPiece());
		}
	}
	//set the game to the exp'rt difficulty
	//sets the board, mines, sets the gamepieces 
	public static void setGameExpert(){
		gameDifficulty = 3;
		x = 32;
		y = 16;
		grid.setLayout(new GridLayout(y,x));
		boardSize = 512;
		minesLeft = 99;
		minesLeftButton.setText("" + minesLeft); 
		boardGenerator();
		pieces = new ArrayList<Gamepiece>();
		for(int i = 0; i < boardSize; i++){
			Gamepiece x = new Gamepiece(board[i], i);
			pieces.add(x);
			grid.add(pieces.get(i).getPiece());
		}
	}
	//gen'rates the mines and board f'r each game
	//gen'rates mines randomly

	public static void boardGenerator(){
		int ran1 = (int)(Math.random()*x);
		int ran2 = (int)(Math.random()*y);
		twodBoard = new int[y][x];
		board = new int[boardSize];

		//gen'rates mines 
		for(int i = 0; i < minesLeft; i++ ){
			if(twodBoard[ran2][ran1] == 9){
				while(twodBoard[ran2][ran1]==9){
					ran1 = (int)(Math.random()*x);
					ran2 = (int)(Math.random()*y);
				}
			}
			twodBoard[ran2][ran1]=9;
		}
		//updates numb'rs around the mines
		for(int i = 0; i < twodBoard.length; i++){
			for(int j = 0; j < twodBoard[i].length; j++){
				if(twodBoard[i][j] == 9 )
					updateAround(twodBoard, i, j);
			}
		}
		//creation of the one dimesional board array to alloweth f'r ease of gamepiece creation
		int index = 0;
		for(int i = 0; i < twodBoard.length; i++){
			for(int j = 0; j < twodBoard[i].length; j++){
				board[index] = twodBoard[i][j];
				index++;
			}
		}
	}
	//f'r useth by the board gen'rat'r to updateth the numb'rs around mines
	public static void updateAround(int[][] temp, int i, int j){
		for(int a = i-1; a <= i+1; a++){
			for(int b = j-1; b <= j+1; b++){
				try{
					if(temp[a][b] != 9) temp[a][b]++;
				}catch(Exception e){}
			}
		}
	}
	//opens 8 spaces around an opened emptyeth square
	public static void empty(int n){
		// n Diagonal Upper Left = (n - x) -1;
		// n Upper = n - x;
		// n Diagonal Upper Right = (n - x) +1;
		// n Left = n-1;
		// n Right = n + 1;
		// n Diagonal Lower Left = (n + x) - 1;
		// n Lower = n + x;
		// n Diagonal Lower Right  = (n + x) - 1;
		int piecesArray[] = {((n - x) -1), (n - x), ((n - x) +1), (n-1), (n), (n+1), ((n + x) - 1), (n + x ), ((n + x) + 1)};
		int y1 = n/x;
		int x1 = n%x;
		int index = 0;
		for (int i = y1-1; i <= y1+1; i++){
			for(int j = x1-1; j <= x1+1; j++){
				try{
					index++;
					if(twodBoard[i][j] < 9 && pieces.get(piecesArray[index-1]).isCovered()){ 
						pieces.get(piecesArray[index-1]).leftClicked();
					}
				}catch(IndexOutOfBoundsException e){}
			}
		}
	}
	//checks if the c'rrect amount of flags art adjacent to a uncov'r'd numb'r when it hath been left-click'd
	public static void checkFlags(int n){
		// n Diagonal Upper Left = (n - x) -1;
		// n Upper = n - x;
		// n Diagonal Upper Right = (n - x) +1;
		// n Left = n-1;
		// n Right = n + 1;
		// n Diagonal Lower Left = (n + x) - 1;
		// n Lower = n + x;
		// n Diagonal Lower Right  = (n + x) - 1;
		int piecesArray[] = {((n - x) -1), (n - x), ((n - x) +1), (n-1), (n), (n+1), ((n + x) - 1), (n + x ), ((n + x) + 1)};
		int y1 = n/x;
		int x1 = n%x;
		int index = 0;
		int mines = 0;
		int flags = 0;
		for (int i = y1-1; i <= y1+1; i++){
			for(int j = x1-1; j <= x1+1; j++){
				try{
					index++;
					if(twodBoard[i][j] == 9){
						mines++;
					}
					if(pieces.get(piecesArray[index-1]).isFlagged()){
						flags++;
					}
				}catch(IndexOutOfBoundsException e){}
			}
		}
		if(flags == mines ) {
			index = 0;
			for (int i = y1-1; i <= y1+1; i++){
				for(int j = x1-1; j <= x1+1; j++){
					try{
						index++;
						System.out.println(twodBoard[i][j]  + " " + pieces.get(piecesArray[index-1]).getType());
						if(pieces.get(piecesArray[index-1]).isCovered()){
							pieces.get(piecesArray[index-1]).leftClicked();
						}
					}catch(IndexOutOfBoundsException e){}
				}
			}
		}
	}
	//makes sure that the first click opens to a emptyeth square
	//if it's not emptyeth regen'rate the grid
	public static void firstClick(int type, int index){
		boolean flagList[] = new boolean[pieces.size()];
		for(int i = 0; i < flagList.length; i++ ){
			if(pieces.get(i).isFlagged()){
				flagList[i]=true;
			}
		}
		while(type != 0){
			grid.removeAll();
			grid.revalidate();
			grid.repaint();
			setGame();
			type = pieces.get(index).getType();
		}
		for(int i = 0; i < flagList.length; i++ ){
			if(flagList[i] && !pieces.get(i).isFlagged()){
				pieces.get(i).rightClicked();
			}
		}
		Minesweeper.timer.start();
		firstClick = false;
		pieces.get(index).leftClicked();

	}
	//calls the right method f'r setting the high sc'res f'r each game difficulty
	public static void highScore(){
		if(gameDifficulty == 1){
			setHighScore(HighScore.begFile, HighScore.scBeg, HighScore.hsBeg);
		}else if(gameDifficulty == 2){
			setHighScore(HighScore.intFile, HighScore.scInt, HighScore.hsInt);
		}else{
			setHighScore(HighScore.expFile,HighScore.scExp, HighScore.hsExp);
		}
	}
	//sets the high sc're
	//checks to seeth if the highsc're beats the current highsc'res
	//if so, popeth up a message boxeth f'r nameth entry
	public static void setHighScore(String filename, ArrayList<Integer> scoreList, ArrayList<String> nameList){
		boolean notAdded = true;
		for(int i = 0; i < scoreList.size(); i++){
			if(score < scoreList.get(i) ){

				String name = HighScorePopup.popUp();
				scoreList.add(i, score);
				nameList.add(i, name);
				notAdded = false;
				break;
			}
		}
		while(scoreList.size() > 3){
			scoreList.remove(3);
			nameList.remove(3);
		}	
		if(scoreList.size() < 3 && notAdded){
			String name = HighScorePopup.popUp();
			scoreList.add(score);
			nameList.add(name);
		}
		HighScore.writeHighScores(filename, scoreList, nameList);
	}

}
