package com.anverm.javalab.game.battleshipgame;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.anverm.javalab.game.Game;
import com.anverm.javalab.game.battleshipgame.builder.BattleShipGameBuilder;
import com.anverm.javalab.game.battleshipgame.logger.BattleShipGameLogger;
import com.anverm.javalab.game.battleshipgame.player.BattleShipGamePlayer;
import com.anverm.javalab.game.battleshipgame.player.duty.BattleDuty;
import com.anverm.javalab.game.battleshipgame.player.duty.strategy.HitBattleShipStrategy;
import com.anverm.javalab.game.battleshipgame.target.BattleShipGameTarget;
import com.anverm.javalab.game.deploy.Deployable;
import com.anverm.javalab.game.exception.GameInitializationException;
import com.anverm.javalab.game.exception.GamePlayException;
import com.anverm.javalab.game.exception.GameShutdownException;
import com.anverm.javalab.game.logger.GameLogger;
import com.anverm.javalab.game.logger.status.GameLoggerStatus;
import com.anverm.javalab.game.platform.PlatForm;
import com.anverm.javalab.game.player.GamePlayer;
import com.anverm.javalab.game.player.duty.exception.NoSuchDutyException;
import com.anverm.javalab.game.player.duty.strategy.exception.NoSuchStrategyException;
import com.anverm.javalab.game.target.Target;
import com.anverm.javalab.game.utility.GameUtility;

public class BattleShipGame implements Game{
	private static final Logger logger = Logger.getLogger("BattleShipGame.class");
	private int playersInActionCount;
	private int platFormSize;
	private Map<String, BattleShipGamePlayer> battleShipPlayerMap = new HashMap<String, BattleShipGamePlayer>();
	private List<String> playersList = new LinkedList<String>();
	private BattleShipGameBuilder builder = new BattleShipGameBuilder();
	private GameLogger gameLogger = new BattleShipGameLogger();
	
	public void initGame(int playersCount, int platFormSize) throws GameInitializationException {
		this.playersInActionCount = playersCount;
		this.platFormSize = platFormSize;
		gameLogger.logPlatFormSize(platFormSize);
	}

	@Override
	public void initPlayer(String playerName)  throws GameInitializationException{
			GamePlayer gamePlayer = builder.buildPlayer(playerName);
			
			//This is battleShip game
			if(gamePlayer != null && gamePlayer instanceof BattleShipGamePlayer){
				battleShipPlayerMap.put(playerName, (BattleShipGamePlayer)gamePlayer);
				playersList.add(playerName);
			}
	}
	
	@Override
	public void initPlatForm(String playerName) throws GameInitializationException {
		GamePlayer gamePlayer = battleShipPlayerMap.get(playerName);
		PlatForm platForm = builder.buildPlatForm(platFormSize);
		gamePlayer.setPlatForm(platForm);
		gameLogger.logPlayerPlatFormImage(playerName);
	}


	@Override
	public boolean initDeployable(String playerName, int startX, int startY, int endX, int endY) throws GameInitializationException {
		GamePlayer gamePlayer = battleShipPlayerMap.get(playerName);
		PlatForm platForm = gamePlayer.getPlatForm();
		Deployable deployableObject = builder.buildDeployable(startX, startY, endX, endY);
		if(platForm.deploy(deployableObject)){
			gameLogger.logDeployableAsDeployed(playerName, startX, startY, endX, endY);
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public GameLogger playGame(String playerName, String enemyName, int targetX, int targetY) throws GamePlayException {
		BattleShipGamePlayer battleShipGamePlayer = null;
		BattleShipGamePlayer enemybattleShipGamePlayer = null;
		try {
			if (playersInActionCount > 1){
				
				battleShipGamePlayer = battleShipPlayerMap.get(playerName);
				if(battleShipGamePlayer == null){
					return prepareGameOutput(GameLoggerStatus.FAILURE, getNextInActionPlayer(playerName), null);
				}
				
				enemybattleShipGamePlayer = battleShipPlayerMap.get(enemyName);
				if(enemybattleShipGamePlayer == null){
					return prepareGameOutput(GameLoggerStatus.FAILURE, getNextInActionPlayer(playerName), null);
				}

				//Prepare target to be affected.
				Target target = new BattleShipGameTarget(enemybattleShipGamePlayer, targetX, targetY);
				
				if(battleShipGamePlayer.doDuty(BattleDuty.dutyName, HitBattleShipStrategy.strategyName, target)){
					gameLogger.logAffectableAsAffected(enemyName, targetX, targetY);
				}else{
					gameLogger.logAffectableAsMissed(enemyName, targetX, targetY);
				}
				
				if(enemybattleShipGamePlayer.isLost()){
					playersInActionCount--;
				}
			} 
			
			if(playersInActionCount == 1){
				battleShipGamePlayer = findWinner();
				return prepareGameOutput(GameLoggerStatus.END, getNextInActionPlayer(playerName), battleShipGamePlayer.getName());
			}
		} catch (NoSuchDutyException e) {
			logger.log(Level.SEVERE, "Duty could not be found", e);
			throw new GamePlayException(e);
		} catch (NoSuchStrategyException e) {
			logger.log(Level.SEVERE, "Strategy could not be found", e);
			throw new GamePlayException(e);
		}
		return prepareGameOutput(GameLoggerStatus.SUCCESS, getNextInActionPlayer(playerName), null);
	}
	
	private BattleShipGamePlayer findWinner() {
		if (playersInActionCount != 1) {
			return null;
		}
		Iterator<Entry<String, BattleShipGamePlayer>> playersIterator = battleShipPlayerMap.entrySet().iterator();
		BattleShipGamePlayer battleShipGamePlayer = playersIterator.next().getValue();
		while (battleShipGamePlayer.isLost()) {
			battleShipGamePlayer = playersIterator.next().getValue();
		}
		return battleShipGamePlayer;
	}

	@Override
	public void shutdown() throws GameShutdownException {
		try {
			GameUtility.destroyConsoleReader();
		} catch (IOException e) {
			logger.log(Level.WARNING, "IOException occurred while Game shutdown", e);
		}
		
	}
	
	private GameLogger prepareGameOutput(GameLoggerStatus gameLoggerStatus, String nextPlayer, String winner){
		gameLogger.setStatus(gameLoggerStatus);
		gameLogger.setNextPlayer(nextPlayer);
		gameLogger.logWinner(winner);
		return gameLogger;
	}

	private String getNextInActionPlayer(String playerName){
		int size = playersList.size();
		int index = -1;
		while(index < size){
			index = playersList.indexOf(playerName);
			if(index == (size-1)){
				index = -1;
			}
			
			//Next Player.
			playerName = playersList.get(index+1);
			GamePlayer gamePlayer = battleShipPlayerMap.get(playerName);
			if(!gamePlayer.isLost()){
				return playerName;
			}
		}
		return null;
	}
	
	@Override
	public Map<String, Character[][]> getGameImage(){
		return gameLogger.getplatFormImages();
	}

	@Override
	public int getPlatFormSize() {
		return platFormSize;
	}

	@Override
	public List<String> getPlayersInAction() {
		// TODO Auto-generated method stub.
		Set<String> players = battleShipPlayerMap.keySet();
		List<String> playersInAction = new LinkedList<String>();
		Iterator<String> it = players.iterator();
		while(it.hasNext()){
			String playerName = it.next();
			BattleShipGamePlayer battleShipGamePlayer = battleShipPlayerMap.get(playerName);
			if(!battleShipGamePlayer.isLost()){
				playersInAction.add(playerName);
			}
		}
		return playersInAction;
	}
}
