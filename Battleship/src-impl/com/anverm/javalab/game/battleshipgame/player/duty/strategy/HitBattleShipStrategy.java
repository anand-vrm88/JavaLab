package com.anverm.javalab.game.battleshipgame.player.duty.strategy;

import com.anverm.javalab.game.player.duty.strategy.Strategy;
import com.anverm.javalab.game.target.Target;

public class HitBattleShipStrategy implements Strategy{
	public static final String strategyName = "HitBattleShipStrategy";

	@Override
	public String getName() {
		return strategyName;
	}
	
	@Override
	public boolean takeEffect() {
		// TODO In future, self action will be implemented.
		return false;
	}

	@Override
	public boolean affectTarget(Target target) {
		//TODO: Extra task can be added based on responsibilities in HitBattleShipStrategy.
		return target.affect();
	}

}
