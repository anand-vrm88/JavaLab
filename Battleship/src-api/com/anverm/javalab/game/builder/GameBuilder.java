package com.anverm.javalab.game.builder;

import com.anverm.javalab.game.deploy.Deployable;
import com.anverm.javalab.game.exception.GameInitializationException;
import com.anverm.javalab.game.platform.PlatForm;
import com.anverm.javalab.game.player.GamePlayer;

//It builds objects in game like player, platform etc.
public interface GameBuilder {
	public GamePlayer buildPlayer(String playerName) throws GameInitializationException;
	public PlatForm buildPlatForm(int platFormSize) throws GameInitializationException;
	public Deployable buildDeployable(int startX, int startY, int endX, int endY) throws GameInitializationException;
}
