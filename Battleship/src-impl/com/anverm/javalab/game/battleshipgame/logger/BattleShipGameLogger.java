package com.anverm.javalab.game.battleshipgame.logger;

import java.util.HashMap;
import java.util.Map;

import com.anverm.javalab.game.logger.GameLogger;
import com.anverm.javalab.game.logger.status.GameLoggerStatus;

public class BattleShipGameLogger implements GameLogger{

	private GameLoggerStatus gameLoggerStatus;
	private int platFormSize;
	private String nextPlayer;
	private String winner;
	private String message;
	private Map<String, Character[][]> platFormImages = new HashMap<String, Character[][]>();
	
	public BattleShipGameLogger() {
	}
	
	public BattleShipGameLogger(GameLoggerStatus gameLoggerStatus) {
		super();
		this.gameLoggerStatus = gameLoggerStatus;
	}
	
	public void logPlatFormSize(int platFormSize){
		this.platFormSize = platFormSize;
	}
	
	public void logPlayerPlatFormImage(String playerName){
		Character[][] platFormImage = new Character[platFormSize][platFormSize];
		for(int i = 0; i<platFormSize; i++){
			for(int j = 0; j<platFormSize; j++){
				platFormImage[i][j] = 'V';
			}
		}
		platFormImages.put(playerName, platFormImage);
	}

	public void logAffectableAsAffected(String playerName, int posX, int posY){
		Character[][] platFormImage = platFormImages.get(playerName);
		platFormImage[posX][posY] = 'A';
	}
	
	public void logAffectableAsMissed(String playerName, int posX, int posY){
		Character[][] platFormImage = platFormImages.get(playerName);
		platFormImage[posX][posY] = 'M';
	}
	
	public void logDeployableAsDeployed(String playerName, int startX, int startY, int endX, int endY){
		int start = -1;
		int end = -1;
		Character[][] platFormImage = platFormImages.get(playerName);
		
		if(platFormImage == null){
			return;
		}
		
		if(startX == endX){
			start = startY<endY?startY:endY;
			end = startY<endY?endY:startY;
			for(int i = start; i<=end; i++){
				platFormImage[startX][i] = 'D';
			}
		} else if(startY == endY){
			start = startX<endX?startX:endX;
			end = startX>endX?startX:endX;
			for(int i = start; i<=end; i++){
				platFormImage[i][startY] = 'D';
			}
		}
	}
	
	@Override
	public GameLoggerStatus getStatus() {
		if(gameLoggerStatus == null){
			return GameLoggerStatus.FAILURE;
		}else{
			return gameLoggerStatus;
		}
	}

	@Override
	public Map<String, Character[][]> getplatFormImages() {
		return platFormImages;
	}

	@Override
	public void setStatus(com.anverm.javalab.game.logger.status.GameLoggerStatus gameLoggerStatus) {
		this.gameLoggerStatus = gameLoggerStatus;
	}

	@Override
	public void logWinner(String winner) {
		this.winner = winner;
	}

	@Override
	public String getWinner() {
		return winner;
	}

	@Override
	public void logMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setNextPlayer(String nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	@Override
	public String getNextPlayer() {
		return nextPlayer;
	}

}
