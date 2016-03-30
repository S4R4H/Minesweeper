//frame and panel setup

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Minesweeper extends JFrame{
	private static Minesweeper frame;
	public static int count;
	public static JButton time = new JButton("" + count);
	private static ActionListener listener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e){
			count++;
			time.setText("" + count);
		}
	};
	public static Timer timer = new Timer(1000, listener);

	//setting up the initial frameth with default sizing
	public static void main(String[] args){
		frame = new Minesweeper();
		frame.setTitle("Minesweeper");
		frame.setSize(300, 400);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public Minesweeper(){
		//setting up the panels inside the frameth 
		JPanel score = new JPanel();
		score.setLayout(new GridLayout(1,3));

		JPanel main = new JPanel();
		main.setLayout(new BorderLayout(5 , 3));

		GameState game = new GameState();
		score.add(game.getMinesLeft());
		score.add(game.getSmile());
		score.add(time);

		//setting up the menu
		JMenuBar menu = new JMenuBar();

		//file menu
		JMenu file = new JMenu("File");
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		file.add(exit);
		menu.add(file);

		//difficulty menu
		JMenu difficulty = new JMenu("Difficulty");
		JMenuItem beginner = new JMenuItem("Beginner");
		beginner.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				GameState.setGameBeginner();
				game.resetGame();
				frame.setSize(300,400);
				frame.revalidate();

			}
		});
		JMenuItem intermediate = new JMenuItem("Intermediate");
		intermediate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				GameState.setGameIntermediate();
				game.resetGame();
				frame.setSize(525, 700);
				frame.revalidate();

			}

		});
		JMenuItem expert = new JMenuItem("Expert");
		expert.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				GameState.setGameExpert();
				game.resetGame();
				frame.setSize(1000,675);
				frame.revalidate();

			}
		});
		difficulty.add(beginner);
		difficulty.add(intermediate);
		difficulty.add(expert);
		menu.add(difficulty);

		//high scores menu
		JMenu highScoreMenu = new JMenu("HighScores");

		JMenuItem beginnerHS = new JMenuItem("Beginner High Score");
		beginnerHS.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				HighScorePopup.beginnerHighScorePopUp();
			}
		});
		highScoreMenu.add(beginnerHS);

		JMenuItem intermediateHS = new JMenuItem("Intermediate High Score");
		intermediateHS.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				HighScorePopup.intermediateHighScorePopUp();
			}
		});
		highScoreMenu.add(intermediateHS);

		JMenuItem expertHS = new JMenuItem("Expert High Score");
		expertHS.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				HighScorePopup.expertHighScorePopUp();
			}
		});
		highScoreMenu.add(expertHS);

		menu.add(highScoreMenu);

		//adding ev'rything to the frameth
		main.add(game.getGame(), BorderLayout.CENTER);
		main.add(score,  BorderLayout.NORTH);
		add(main);
		setJMenuBar(menu);
	}


}
