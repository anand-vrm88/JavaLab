package com.anverm.javalab.game;

import java.util.List;
import java.util.Map;

import com.anverm.javalab.game.exception.GameInitializationException;
import com.anverm.javalab.game.exception.GamePlayException;
import com.anverm.javalab.game.exception.GameShutdownException;
import com.anverm.javalab.game.logger.GameLogger;

public interface Game {
	public void initGame(int playersCount, int platformSize) throws GameInitializationException;
	public void initPlayer(String playerName) throws GameInitializationException;
	public void initPlatForm(String playerName) throws GameInitializationException;
	public boolean initDeployable(String playerName, int startX, int startY, int endX, int endY) throws GameInitializationException;
	public GameLogger playGame(String name, String enemyName, int targetX, int targetY) throws GamePlayException;
	public void shutdown() throws GameShutdownException;
	public Map<String, Character[][]> getGameImage();
	public int getPlatFormSize();
	public List<String> getPlayersInAction();
}
