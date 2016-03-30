//high sc're popup setup

import javax.swing.*;

public class HighScorePopup {

	//hast an entry boxeth popup f'r nameth entry f'r highsc'res 
	public static String popUp(){
		String name = JOptionPane.showInputDialog(null, "You achieved a new high score! \nPlease enter your name:", "", JOptionPane.DEFAULT_OPTION);	
		return name;
	}

	//pops up a mesage boxeth showing the beginn'r's high sc're
	public static void beginnerHighScorePopUp(){
		HighScore.importAllHighScores();
		String message = "Beginner High Scores: \n";
		if(HighScore.hsBeg.size() > 0){
			for(int i = 0; i < HighScore.hsBeg.size(); i++){
				message += HighScore.scBeg.get(i) + " " + HighScore.hsBeg.get(i) + "\n";
			}
		}else{
			message += "No high scores available";
		}
		JOptionPane.showMessageDialog(null, message);
	}
	//pops up a mesage boxeth showing the int'rmediate's high sc're
	public static void intermediateHighScorePopUp(){
		HighScore.importAllHighScores();
		String message = "Intermediate High Scores \n";
		if(HighScore.hsInt.size() > 0){
			for(int i = 0; i < HighScore.hsInt.size(); i++){
				message += HighScore.scInt.get(i) + " " + HighScore.hsInt.get(i) + "\n";
			}
		}else{
			message += "No high scores available";
		}
		JOptionPane.showMessageDialog(null, message);
	}
	//pops up a mesage boxeth showing the extreme's high sc're
	public static void expertHighScorePopUp(){
		HighScore.importAllHighScores();
		String message = "Expert High Scores \n";
		if(HighScore.hsExp.size() > 0){
			for(int i = 0; i < HighScore.hsExp.size(); i++){
				message += HighScore.scExp.get(i) + " " + HighScore.hsExp.get(i) + "\n";
			}
		}else{
			message += "No high scores available, mwhahaha";
		}
		JOptionPane.showMessageDialog(null, message);
	}

}
