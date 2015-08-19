package com.anverm.javalab.main;

import java.util.logging.Level;
import java.util.logging.Logger;


import com.anverm.javalab.game.Game;
import com.anverm.javalab.game.battleshipgame.BattleShipGame;
import com.anverm.javalab.game.exception.GameInitializationException;
import com.anverm.javalab.game.exception.GamePlayException;
import com.anverm.javalab.game.exception.GameShutdownException;
import com.anverm.javalab.game.utility.GameConsole;

public class StartGame {

	private static final Logger logger = Logger.getLogger("StartGame");

	public static void main(String[] args) {
		try {
			Game battleShipGame = new BattleShipGame();
			GameConsole.start(battleShipGame);
		} catch (GameInitializationException e) {
			logger.log(Level.SEVERE, "Game could not be initialized", e);
		} catch (GamePlayException e) {
			logger.log(Level.SEVERE, "Exception occurred while playing Game", e);
		} catch (GameShutdownException e) {
			logger.log(Level.SEVERE, "Exception occurred while Game shutdown", e);
		}
	}
}
