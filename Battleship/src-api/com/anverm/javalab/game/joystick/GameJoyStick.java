package com.anverm.javalab.game.joystick;

import com.anverm.javalab.game.collection.GameCount;
import com.anverm.javalab.game.exception.GameInitializationException;
import com.anverm.javalab.game.exception.GamePlayException;
import com.anverm.javalab.game.logger.GameLogger;

public interface GameJoyStick {
	public GameCount generateGameInitInput() throws GameInitializationException;
	public String generatePlayerInitInput() throws GameInitializationException;
	public void generatePlatformInitInput(String playerName) throws GameInitializationException;
	public void generateDeployableInitInput(String playerName) throws GameInitializationException;
	public GameLogger generateGamePlayInput(String playerName) throws GamePlayException;
	public boolean setInputSouce(Object inputSource);
}
