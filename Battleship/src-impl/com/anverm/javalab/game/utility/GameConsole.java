package com.anverm.javalab.game.utility;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.anverm.javalab.game.Game;
import com.anverm.javalab.game.collection.GameCount;
import com.anverm.javalab.game.exception.GameInitializationException;
import com.anverm.javalab.game.exception.GamePlayException;
import com.anverm.javalab.game.exception.GameShutdownException;
import com.anverm.javalab.game.joystick.BattleShipGameBotJoyStick;
import com.anverm.javalab.game.joystick.BattleShipGameHumanJoyStick;
import com.anverm.javalab.game.logger.GameLogger;


public class GameConsole {

	public static void start(Game game) throws GameInitializationException, GamePlayException, GameShutdownException{
		BattleShipGameHumanJoyStick human = new BattleShipGameHumanJoyStick(game);
		BattleShipGameBotJoyStick bot = new BattleShipGameBotJoyStick(game);
		try {
			GameCount gameCount = human.generateGameInitInput();
			int playersCount = gameCount.getPlayersCount();
			//int botsCount = gameCount.getBotsCount();
			int deployablesCount = gameCount.getDeployablesCount();
			
			//int humanPlayerCount = playersCount - botsCount;
			
			String[] playersName = new String[playersCount];
			
/*			for (int i = 0; i < humanPlayerCount; i++) {
				if (!playersName[i].equalsIgnoreCase("Bot")) {
					playersName[i] = human.generatePlayerInitInput();
					human.generatePlatformInitInput(playersName[i]);
					for (int j = 0; j < deployablesCount; j++) {
						human.generateDeployableInitInput(playersName[i]);
					}
				} else {
					playersName[i] = bot.generatePlayerInitInput();
					bot.generatePlatformInitInput(playersName[i]);
					for (int j = 0; j < deployablesCount; j++) {
						bot.generateDeployableInitInput(playersName[i]);
					}
				}
			}*/
			
			
				playersName[0] = human.generatePlayerInitInput();
				human.generatePlatformInitInput(playersName[0]);
				for (int j = 0; j < deployablesCount; j++) {
					human.generateDeployableInitInput(playersName[0]);
				}
				playersName[1] = bot.generatePlayerInitInput();
				bot.generatePlatformInitInput(playersName[1]);
				for (int j = 0; j < deployablesCount; j++) {
					bot.generateDeployableInitInput(playersName[1]);
				}
			
			printImage(game.getGameImage());
			int j = 0;
/*			while(true){
				if(j == playersCount){
					j=0;
					continue;
				}
				GameOutput gameOutput = human.generateGamePlayInput(playersName[j]);
				printImage(gameOutput.getOutput());
				if(gameOutput.getStatus().equals("END")){
					System.out.println(gameOutput.getWinner()+" won. game ends");
					break;
				}
				j++;
			}*/
			GameLogger gameLogger = null;
			while(true){
				if(j == playersCount){
					j=0;
					continue;
				}
				if(j == 0){
					gameLogger = human.generateGamePlayInput(playersName[j]);
				}else{
					gameLogger = bot.generateGamePlayInput(playersName[j]);
				}
				printImage(gameLogger.getplatFormImages());
				if(gameLogger.getStatus().equals("END")){
					System.out.println(gameLogger.getWinner()+" won. game ends");
					break;
				}
				j++;
			}
			
			
		} catch (GameInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*		game.initialize();
		game.play();
		game.shutdown();*/
	}
	
	private static void printImage(Map<String, Character[][]> gameImage){
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, Character[][]>> it = gameImage.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, Character[][]> entry = it.next();
			String playerName = entry.getKey();
			Character[][] platFormImage = entry.getValue();
			sb.append(playerName);
			sb.append("\n_________________________________\n");
			sb.append(printPlatFormImage(platFormImage));
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	private static String printPlatFormImage(Character[][] platFormImage){
		StringBuilder sb = new StringBuilder();
		int size = platFormImage[0].length;
		for(int i = 0; i< size; i++){
			for(int j = 0; j<size; j++){
				sb.append(platFormImage[i][j]);
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
