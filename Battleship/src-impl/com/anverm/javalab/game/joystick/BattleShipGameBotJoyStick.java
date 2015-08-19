package com.anverm.javalab.game.joystick;

import java.util.List;
import java.util.Random;

import com.anverm.javalab.game.Game;
import com.anverm.javalab.game.collection.GameCount;
import com.anverm.javalab.game.exception.GameInitializationException;
import com.anverm.javalab.game.exception.GamePlayException;
import com.anverm.javalab.game.logger.GameLogger;

/**
 * This Bot is technically dumb as it would be doing each task randomly.
 * @author anand
 *
 */
public class BattleShipGameBotJoyStick implements GameJoyStick{
	private Game game;
	private Random inputSource;
	
	public BattleShipGameBotJoyStick(Game game) {
		this.game = game;
		inputSource = new Random();
	}

	@Override
	public GameCount generateGameInitInput() throws GameInitializationException {
/*		String line = null;
		int platFormSize = 0;
		int battleShipCount = 0;
		int playersCount = 0;
		int botsCount = 0;
		BufferedReader br = GameUtility.getConsoleReader();
		boolean isValidInput = true;
		do {
			if(!isValidInput){
				System.out.println("[Battle]$ You have entered invalid players count.");
			}
			System.out.print("[Battle]$ Enter players count:");
			line = br.readLine();
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
			line = br.readLine();
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
			if(!isValidInput){
				System.out.println("[Battle]$ You have entered invalid platform size.");
			}
			System.out.print("[Battle]$ Enter platform size:");
			line = br.readLine();
			try {
				platFormSize = Integer.parseInt(line);
				isValidInput = true;
			} catch (NumberFormatException e) {
				isValidInput = false;
			}
		} while (!isValidInput);

		do {
			if(!isValidInput){
				System.out.println("[Battle]$ You have entered invalid ship count.");
			}
			System.out.print("[Battle]$ Enter ship count:");
			line = br.readLine();
			try {
				battleShipCount = Integer.parseInt(line);
				isValidInput = true;
			} catch (NumberFormatException e) {
				isValidInput = false;
			}
		} while (!isValidInput);
		
		game.initGame(playersCount, platFormSize);
		return new BattleShipGameCount(playersCount, botsCount, battleShipCount);*/
		return null;
	}

	@Override
	public String generatePlayerInitInput() throws GameInitializationException {
		String playerName = "bot";
		//BufferedReader br = GameUtility.getConsoleReader();
		System.out.print("[Battle]$ Enter player name:");
		processing();
		System.out.println(playerName);
		//String playerName = br.readLine();
		
		game.initPlayer(playerName);
		return playerName;
	}

	private void processing() {
		try {
			Thread.sleep(1 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		int platFormSize = game.getPlatFormSize();
		int startX = 0;
		int startY = 0;
		int endX = 0;
		int endY = 0;
		//BufferedReader br = GameUtility.getConsoleReader();
		do {
			if (!validShip) {
				System.out.println("[Battle]$ You have enetered invalid coordinates for battleship.");
			}
/*			System.out.print("[Battle]$ Enter coordinates[x1 y1 x2 y2] of battleship:");
			line = br.readLine();
			String[] coordinateArray = line.split(" ");

			//Early check for invalid input format.
			if (coordinateArray.length != 4) {
				validShip = false;
				continue;
			}*/
			
			System.out.print("[Battle]$ Enter coordinates[x1 y1 x2 y2] of battleship:");
			
			//Firstly, choose any valid building block coordinate on platForm.
			startX = inputSource.nextInt(platFormSize);
			startY = inputSource.nextInt(platFormSize);
			
			//Now, choose battleShip orientation.
			boolean virtically = inputSource.nextBoolean();
			
			if(virtically){
				endY = startY;
				endX = inputSource.nextInt(platFormSize);
			}else{
				endX = startX;
				endY = inputSource.nextInt(platFormSize);
			}

			processing();
			
			System.out.println(startX+" "+startY+" "+endX+" "+endY);
			
/*			try {
				startX = Integer.parseInt(coordinateArray[0].trim());
				startY = Integer.parseInt(coordinateArray[1].trim());
				endX = Integer.parseInt(coordinateArray[2].trim());
				endY = Integer.parseInt(coordinateArray[3].trim());*/
				validShip = game.initDeployable(playerName, startX, startY, endX, endY);
/*			} catch (NumberFormatException e) {
				validShip = false;
				continue;
			}*/
		} while (!validShip); //Early check for invalid input format.
	}
	
	@Override
	public GameLogger generateGamePlayInput(String playerName) throws GamePlayException {
		//BufferedReader br = GameUtility.getConsoleReader();
		int platFormSize = game.getPlatFormSize();
		boolean isValidInput = true;
		GameLogger gameLogger = null;
		String enemyName = null;
		int targetX = -1;
		int targetY = -1;
		//do {
			if(!isValidInput){
				System.out.println("[Battle]$ You have provided invalid input");
			}

			List<String> playersInAction = game.getPlayersInAction();
			playersInAction.remove(playerName);
			int playersInActionSize = playersInAction.size();
			
			//Choose enemy randomly. Though this is dumb bot, it may choose itself 
			//as its enemy.
			int playerIndex = inputSource.nextInt(playersInActionSize);
			enemyName = playersInAction.get(playerIndex);
			
			System.out.print("[Battle@"+playerName+"]$ Enter enemy name:");
			processing();
			System.out.println(enemyName);
			/*enemyName = br.readLine();*/
			
			//Find how many players are still in action.

			targetX = inputSource.nextInt(platFormSize);
			targetY = inputSource.nextInt(platFormSize);
			
			
			System.out.print("[Battle@"+playerName+"]$ Enter enemy battleship coordinates[x y] and hit:");
			processing();
			System.out.println(targetX+" "+targetY);
			
/*			line = br.readLine();
			String[] coordinateArray = line.split(" ");
			if (coordinateArray.length != 2) {
				isValidInput = false;
				continue;
			}

			try {
				targetX = Integer.parseInt(coordinateArray[0].trim());
				targetY = Integer.parseInt(coordinateArray[1].trim());
*/				gameLogger = game.playGame(playerName, enemyName, targetX, targetY);
				if(gameLogger.getStatus().equals("SUCCESS") || gameLogger.getStatus().equals("END")){
					isValidInput = true;
				}else{
					isValidInput = false;
				}
/*			} catch (NumberFormatException e) {
				System.out.println("[Battle]$ You have entered invalid coordinates of enemy["+enemyName+"] battleship.");
				isValidInput = false;
				continue;
			}*/
		//} while (!isValidInput); //early check for Coordinates.
		return gameLogger;
	}

	@Override
	public boolean setInputSouce(Object inputSource) {
		if(inputSource instanceof Random){
			this.inputSource = (Random) inputSource;
			return true;
		}else{
			return false;
		}
	}

}
