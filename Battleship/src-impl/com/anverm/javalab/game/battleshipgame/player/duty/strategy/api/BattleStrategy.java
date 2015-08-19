package com.anverm.javalab.game.battleshipgame.player.duty.strategy.api;

import com.anverm.javalab.game.battleshipgame.platform.GridPlatForm;
import com.anverm.javalab.game.player.duty.strategy.Strategy;

public interface BattleStrategy extends Strategy{
	public boolean attack(GridPlatForm enemyBattleGrid, int posX, int posY);
}
