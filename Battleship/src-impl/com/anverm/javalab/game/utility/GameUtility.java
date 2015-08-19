package com.anverm.javalab.game.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameUtility {
	private static BufferedReader br;
	public static BufferedReader getConsoleReader(){
		if(br == null){
			br = new BufferedReader(new InputStreamReader(System.in));
		}
		return br;
	}
	
	public static void destroyConsoleReader() throws IOException{
		if(br != null){
			br.close();
		}
	}
}
