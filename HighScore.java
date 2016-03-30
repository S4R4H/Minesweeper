//highsc're arrays and fileth management

import java.util.*;
import java.io.*;

public class HighScore {
	public static String begFile = "beghighscores.txt";
	public static String intFile = "inthighscores.txt";
	public static String expFile = "exphighscores.txt";
	private static Scanner hs;
	private static PrintWriter out;
	public static ArrayList<String> hsBeg = new ArrayList<String>();
	public static ArrayList<String> hsInt = new ArrayList<String>();
	public static ArrayList<String> hsExp = new ArrayList<String>();
	public static ArrayList<Integer> scBeg= new ArrayList<Integer>();
	public static ArrayList<Integer> scInt= new ArrayList<Integer>();
	public static ArrayList<Integer> scExp= new ArrayList<Integer>();


	//imp'rts all difficulty of highsc'res from files
	public static void importAllHighScores(){
		importHighScores(begFile, scBeg, hsBeg);
		importHighScores(intFile, scInt, hsInt);
		importHighScores(expFile, scExp, hsExp);
	}

	//imp'rt the high sc'res to the game
	//creates a files, checks to seeth if it exisits, if so
	//clears the highsc're arrays and addeth the highsc'res from the fileth
	public static void importHighScores(String filename, ArrayList<Integer> scoreArr, ArrayList<String> nameArr){
		File file = new File(filename);
		if(file.exists()){
			try{
				scoreArr.clear();
				nameArr.clear();
				hs = new Scanner(file);
				while(hs.hasNext()){
					scoreArr.add(hs.nextInt());
					nameArr.add(hs.nextLine());
				}
			}catch(FileNotFoundException e){ 
				System.out.println("No such file"); 
			}finally{
				if(hs != null)
					hs.close();
			}
		}

	}
	//writes the highsc'res to fileth
	//prints each array to their respective files
	public static void writeHighScores(String filename, ArrayList<Integer> scoreArr, ArrayList<String> nameArr){
		File file = new File(filename);
		try{
			out = new PrintWriter(file);
			for(int i = 0; i < scoreArr.size(); i++){
				out.println(scoreArr.get(i) + " " + nameArr.get(i));
			}

		}catch(FileNotFoundException e){
			System.out.println(e);
		}finally{
			if(out != null){
				out.close();
			}
		}
	}

}
