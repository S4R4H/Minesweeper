//creation of each tile, setting typeth and functionailty

import javax.swing.*;
import java.awt.event.*;

public class Gamepiece{
	private int type;
	private JButton piece;
	private boolean covered;
	private boolean flagged = false;
	private int buttonIndex;

	//constructeth'r f'r the each square in the game
	public Gamepiece(int type, int buttonIndex){
		this.type = type;
		this.buttonIndex = buttonIndex;
		this.piece = new JButton(Icon.cover);
		this.covered = true;
		//allows gamepieces to be click'd
		piece.addMouseListener(new MouseAdapter(){
			//anonymous inn'r class
			@Override
			public void mousePressed (MouseEvent e){
				//if the mouse is left click'd
				//checks to seeth if 'tis the first left click
				if(e.getButton() == MouseEvent.BUTTON1){
					if(GameState.firstClick){
						GameState.firstClick(type, buttonIndex);
					}else{
						leftClicked();
					}
					//checks to seeth if 'tis the right click
				}else if(e.getButton() == MouseEvent.BUTTON3){
					rightClicked();
				}
			}
		});

	}

	//if a game piece is left click'd
	//then it checks if 'tis flagg'd that the piece is cov'r'd and that it's not game ov'r
	//is so, the icon is display'd
	public void leftClicked(){
		if(!flagged && covered && !GameState.gameOver){
			switch (type){
			case 0:
				piece.setIcon(Icon.zero);
				covered = false;
				GameState.empty(buttonIndex);
				break;
			case 1:
				piece.setIcon(Icon.one);
				break;
			case 2:
				piece.setIcon(Icon.two);
				break;
			case 3:
				piece.setIcon(Icon.three);
				break;
			case 4:
				piece.setIcon(Icon.four);
				break;
			case 5:
				piece.setIcon(Icon.five);
				break;
			case 6:
				piece.setIcon(Icon.six);
				break;
			case 7:
				piece.setIcon(Icon.seven);
				break;
			case 8:
				piece.setIcon(Icon.eight);
				break;
			case 9:
				//typeth 9 is a mineth
				piece.setIcon(Icon.redMine);
				covered = false;
				//game ov'r
				GameState.gameLost();
				break;
			}
			covered = false;
			//aft'r ev'r successful left click the board is check'd f'r a wineth
			GameState.checkBoard();
		}else if(!covered){
			//uncov'r all numb'rs around the uncov'r'd numb'r
			GameState.checkFlags(buttonIndex);
		}
	}
	//if a game piece is right click'd
	//if it already hath a flag, removeth it and increaseth the flags left
	//if it doesn't then puteth one up then decrease the flags left
	public void rightClicked(){
		Minesweeper.timer.start();
		if(flagged  && covered){
			piece.setIcon(Icon.cover);
			GameState.setMinesLeft(1);
			flagged = false;
		} else if (covered){
			piece.setIcon(Icon.flag);
			GameState.setMinesLeft(-1);
			flagged = true;

		}
	}
	//geteth the game piece
	public JButton getPiece(){
		return piece;
	}
	//to reset a game piece to the state default
	public void resetPiece(int type, int buttonIndex){
		piece.setIcon(Icon.cover);
		flagged = false;
		covered = true;
		this.type= type;
		this.buttonIndex = buttonIndex;
	}
	//geteth the typeth of game piece
	public int getType(){
		return type;
	}
	//checketh to seeth if the tile is uncov'r'd 'r a mineth
	public boolean nonMineUncovered(){
		if(!covered){
			return true;
		}else if(type == 9){
			return true;
		}else{
			return false;
		}
	}
	//checketh if uncov'r'd pieces art mines
	public void uncoverMines(){
		if(covered && type == 9){
			piece.setIcon(Icon.mineGrey);
		}else if(flagged && type != 9){
			piece.setIcon(Icon.mineMisflagged);
		}
	}
	//returneth if a gamepiece is cov'r'd
	public boolean isCovered(){
		return covered;
	}
	//returneth if a gamepiece is flagg'd
	public boolean isFlagged(){
		return flagged;
	}
}
