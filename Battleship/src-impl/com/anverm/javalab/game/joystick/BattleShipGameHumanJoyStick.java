package com.anverm.javalab.game.joystick;

import java.io.BufferedReader;
import java.io.IOException;

import com.anverm.javalab.game.Game;
import com.anverm.javalab.game.battleshipgame.collection.BattleShipGameCount;
import com.anverm.javalab.game.collection.GameCount;
import com.anverm.javalab.game.exception.GameInitializationException;
import com.anverm.javalab.game.exception.GamePlayException;
import com.anverm.javalab.game.logger.GameLogger;

public class BattleShipGameHumanJoyStick implements GameJoyStick{
	
	private Game game;
	private BufferedReader inputSource;
	
	public BattleShipGameHumanJoyStick(Game game) {
		this.game = game;
	}

	@Override
	public GameCount generateGameInitInput() throws GameInitializationException {
		String line = null;
		int platFormSize = 0;
		int battleShipCount = 0;
		int playersCount = 0;
		int botsCount = 0;
		boolean isValidInput = true;
		try {
			do {
				if (!isValidInput) {
					System.out.println("[Battle]$ You have entered invalid players count.");
				}
				System.out.print("[Battle]$ Enter players count:");
				line = inputSource.readLine();
				try {
					playersCount = Integer.parseInt(line);
					isValidInput = true;
				} catch (NumberFormatException e) {
					isValidInput = false;
				}
			} while (!isValidInput);

			do {
				if (!isValidInput) {
					System.out.println("[Battle]$ You have entered invalid bots count.");
				}
				System.out.print("[Battle]$ Enter bots count:");
				line = inputSource.readLine();
				try {
					botsCount = Integer.parseInt(line);
					if (botsCount >= playersCount) {
						isValidInput = false;
						continue;
					}
					isValidInput = true;
				} catch (NumberFormatException e) {
					isValidInput = false;
				}
			} while (!isValidInput);

			do {
				if (!isValidInput) {
					System.out.println("[Battle]$ You have entered invalid platform size.");
				}
				System.out.print("[Battle]$ Enter platform size:");
				line = inputSource.readLine();
				try {
					platFormSize = Integer.parseInt(line);
					isValidInput = true;
				} catch (NumberFormatException e) {
					isValidInput = false;
				}
			} while (!isValidInput);

			do {
				if (!isValidInput) {
					System.out.println("[Battle]$ You have entered invalid ship count.");
				}
				System.out.print("[Battle]$ Enter ship count:");
				line = inputSource.readLine();
				try {
					battleShipCount = Integer.parseInt(line);
					isValidInput = true;
				} catch (NumberFormatException e) {
					isValidInput = false;
				}
			} while (!isValidInput);

			game.initGame(playersCount, platFormSize);
		} catch (IOException e) {
			throw new GameInitializationException("Internal Server error whiling initializing game", e);
		}
		return new BattleShipGameCount(playersCount, botsCount, battleShipCount);
	}

	@Override
	public String generatePlayerInitInput() throws GameInitializationException {
		String playerName = null;
		try {
			System.out.print("[Battle]$ Enter player name:");
			playerName = inputSource.readLine();
			game.initPlayer(playerName);
		} catch (IOException e) {
			throw new GameInitializationException("Internal Server error while initializing player: " + playerName, e);
		}
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
		String line = null;
		try {
			do {
				if (!validShip) {
					System.out.println("[Battle]$ You have enetered invalid coordinates for battleship.");
				}
				System.out.print("[Battle]$ Enter coordinates[x1 y1 x2 y2] of battleship:");
				line = inputSource.readLine();
				String[] coordinateArray = line.split(" ");

				// Early check for invalid input format.
				if (coordinateArray.length != 4) {
					validShip = false;
					continue;
				}

				try {
					startX = Integer.parseInt(coordinateArray[0].trim());
					startY = Integer.parseInt(coordinateArray[1].trim());
					endX = Integer.parseInt(coordinateArray[2].trim());
					endY = Integer.parseInt(coordinateArray[3].trim());
					validShip = game.initDeployable(playerName, startX, startY, endX, endY);
				} catch (NumberFormatException e) {
					validShip = false;
					continue;
				}
			} while (!validShip); // Early check for invalid input format.
		} catch (IOException e) {
			throw new GameInitializationException("Internal Server error while deploying battleship for player: " + playerName, e);
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
		try {
			do {
				if (!isValidInput) {
					System.out.println("[Battle]$ You have provided invalid input");
				}

				System.out.print("[Battle@" + playerName + "]$ Enter enemy name:");
				enemyName = inputSource.readLine();

				System.out.print("[Battle@" + playerName + "]$ Enter enemy battleship coordinates[x y] and hit:");
				line = inputSource.readLine();
				String[] coordinateArray = line.split(" ");
				if (coordinateArray.length != 2) {
					isValidInput = false;
					continue;
				}

				try {
					targetX = Integer.parseInt(coordinateArray[0].trim());
					targetY = Integer.parseInt(coordinateArray[1].trim());
					gameLogger = game.playGame(playerName, enemyName, targetX, targetY);
					if (gameLogger.getStatus().equals("SUCCESS") || gameLogger.getStatus().equals("END")) {
						isValidInput = true;
					} else {
						isValidInput = false;
					}
				} catch (NumberFormatException e) {
					System.out.println("[Battle]$ You have entered invalid coordinates of enemy[" + enemyName + "] battleship.");
					isValidInput = false;
					continue;
				}
			} while (!isValidInput); // early check for Coordinates.
		} catch (IOException e) {
			throw new GamePlayException("Internal Server error while playing game for player: " + playerName, e);
		}
		return gameLogger;
	}

	@Override
	public boolean setInputSouce(Object inputSource) {
		if (inputSource instanceof BufferedReader) {
			this.inputSource = (BufferedReader) inputSource;
			return true;
		} else {
			return false;
		}
	}

}
