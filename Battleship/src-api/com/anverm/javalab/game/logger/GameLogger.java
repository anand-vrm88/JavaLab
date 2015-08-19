package com.anverm.javalab.game.logger;

import java.util.Map;

import com.anverm.javalab.game.logger.status.GameLoggerStatus;


public interface GameLogger {
	public GameLoggerStatus getStatus();
	public void setStatus(GameLoggerStatus gameLoggerStatus);
	public Map<String, Character[][]> getplatFormImages();
	public void logPlatFormSize(int platFormSize);
	public void logPlayerPlatFormImage(String playerName);
	public void logAffectableAsAffected(String playerName, int posX, int posY);
	public void logAffectableAsMissed(String playerName, int posX, int posY);
	public void logDeployableAsDeployed(String playerName, int startX, int startY, int endX, int endY);
	public void logWinner(String winner);
	public String getWinner();
	public void logMessage(String message);
	public String getMessage();
	public void setNextPlayer(String nextPlayer);
	public String getNextPlayer();
}
