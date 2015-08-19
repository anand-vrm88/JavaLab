package com.anverm.javalab.game.joystick;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;

import com.anverm.javalab.game.Game;
import com.anverm.javalab.game.battleshipgame.collection.BattleShipGameCount;
import com.anverm.javalab.game.battleshipgame.logger.BattleShipGameLogger;
import com.anverm.javalab.game.collection.GameCount;
import com.anverm.javalab.game.exception.GameInitializationException;
import com.anverm.javalab.game.exception.GamePlayException;
import com.anverm.javalab.game.logger.GameLogger;
import com.anverm.javalab.game.logger.status.GameLoggerStatus;

public class BattleShipGameServletJoyStick implements GameJoyStick{

	private Game game;
	private HttpServletRequest inputSource;
	
	
	
	public BattleShipGameServletJoyStick(Game game) {
		this.game = game;
	}

	@Override
	public GameCount generateGameInitInput() throws GameInitializationException {
		try {
			int playersCount = Integer.parseInt(inputSource.getParameter("playersCount"));
			int botsCount = Integer.parseInt(inputSource.getParameter("botsCount"));
			if(botsCount >= playersCount){
				throw new GameInitializationException("Bots count should be less than players count");
			}
			int platFormSize = Integer.parseInt(inputSource.getParameter("platFormSize"));
			int battleShipsCount = Integer.parseInt(inputSource.getParameter("battleShipsCount"));
			if(battleShipsCount > (platFormSize*platFormSize)){
				throw new GameInitializationException("BattleShips count cannot be larger than platForm capacity");
			}
			game.initGame(playersCount, platFormSize);
			return new BattleShipGameCount(playersCount, botsCount, battleShipsCount);
		} catch (NumberFormatException e) {
			throw new GameInitializationException("Input is corrupted",e);
		}
	}

	@Override
	public String generatePlayerInitInput() throws GameInitializationException {
		System.out.print("[Battle]$ Enter player name:");
		String playerName = inputSource.getParameter("playerName");
		if(playerName == null && "".equals(playerName)){
			throw new GameInitializationException("Player Name cannot be blank");
		}
		game.initPlayer(playerName);
		return playerName;
	}
	
	@Override
	public void generatePlatformInitInput(String playerName) throws GameInitializationException {
		game.initPlatForm(playerName);
	}

	/**
	 * Currently only BattleShip is supported to deploy on platForm. 
	 */
	@Override
	public void generateDeployableInitInput(String playerName) throws GameInitializationException {
		boolean validShip = true;
		int startX = 0;
		int startY = 0;
		int endX = 0;
		int endY = 0;
		//System.out.print("[Battle]$ Enter coordinates[x1 y1 x2 y2] of battleship:");
		try {
			System.out.println("x is = "+inputSource.getParameter("startX"));
			System.out.println("y is = "+inputSource.getParameter("startY"));
			System.out.println("x is = "+inputSource.getParameter("endX"));
			System.out.println("y is = "+inputSource.getParameter("endY"));
			startX = Integer.parseInt(inputSource.getParameter("startX").trim());
			startY = Integer.parseInt(inputSource.getParameter("startY").trim());
			endX = Integer.parseInt(inputSource.getParameter("endX").trim());
			endY = Integer.parseInt(inputSource.getParameter("endY").trim());
			validShip = game.initDeployable(playerName, startX, startY, endX, endY);
		} catch (NumberFormatException e) {
			throw new GameInitializationException("Coordinates are corrupted. Please re-try", e);
/*			gameLogger = new BattleShipGameLogger(GameLoggerStatus.FAILURE);
			gameLogger.logMessage("Coordinates are corrupted");
			return gameLogger;*/
		}

		// Return output based on validShip flag.
		if (!validShip) {
			throw new GameInitializationException("Coordinates are invalid. Please re-try");
/*			gameLogger = new BattleShipGameLogger(GameLoggerStatus.FAILURE);
			gameLogger.logMessage("Coordinates are invalid");
			return gameLogger;*/
		}
	}
	
	@Override
	public GameLogger generateGamePlayInput(String playerName) throws GamePlayException {
		String line = null;
		boolean isValidInput = true;
		GameLogger gameLogger = null;
		String enemyName = null;
		int targetX = -1;
		int targetY = -1;
		//do {
/*			if(!isValidInput){
				System.out.println("[Battle]$ You have provided invalid input");
			}
*/			
			System.out.print("[Battle@"+playerName+"]$ Enter enemy name:");
			enemyName = inputSource.getParameter("enemyName");

			System.out.print("[Battle@"+playerName+"]$ Enter enemy battleship coordinates[x y] and hit:");
			try {
				targetX = Integer.parseInt(inputSource.getParameter("targetX").trim());
				targetY = Integer.parseInt(inputSource.getParameter("targetY").trim());
				gameLogger = game.playGame(playerName, enemyName, targetX, targetY);
				if(gameLogger.getStatus().equals(GameLoggerStatus.SUCCESS) || gameLogger.getStatus().equals(GameLoggerStatus.END)){
					isValidInput = true;
				}else{
					isValidInput = false;
				}
			} catch (NumberFormatException e) {
				System.out.println("[Battle]$ You have entered invalid coordinates of enemy["+enemyName+"] battleship.");
				gameLogger = new BattleShipGameLogger(GameLoggerStatus.FAILURE);
				gameLogger.logMessage("Coordinates are corrupted");
				return gameLogger;
			}
		//} while (!isValidInput); //early check for Coordinates.
		return gameLogger;
	}

	@Override
	public boolean setInputSouce(Object inputSource) {
		if(inputSource instanceof HttpServletRequest){
			this.inputSource = (HttpServletRequest) inputSource;
			return true;
		}else{
			return false;
		}
		
	}

}
