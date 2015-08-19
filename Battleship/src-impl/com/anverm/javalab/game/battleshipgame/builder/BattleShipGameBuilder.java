package com.anverm.javalab.game.battleshipgame.builder;


import com.anverm.javalab.game.battleshipgame.deploy.BattleShip;
import com.anverm.javalab.game.battleshipgame.platform.GridPlatForm;
import com.anverm.javalab.game.battleshipgame.player.BattleShipGamePlayer;
import com.anverm.javalab.game.battleshipgame.player.duty.BattleDuty;
import com.anverm.javalab.game.battleshipgame.player.duty.strategy.HitBattleShipStrategy;
import com.anverm.javalab.game.builder.GameBuilder;
import com.anverm.javalab.game.deploy.Deployable;
import com.anverm.javalab.game.exception.GameInitializationException;
import com.anverm.javalab.game.platform.PlatForm;
import com.anverm.javalab.game.player.GamePlayer;
import com.anverm.javalab.game.player.duty.Duty;
import com.anverm.javalab.game.player.duty.strategy.Strategy;

public class BattleShipGameBuilder implements GameBuilder{

	//TODO: Keeping GameInitializationException as throwable of following method for future implementation.
	@Override
	public GamePlayer buildPlayer(String playerName) throws GameInitializationException {
		//TODO: Game Player building Implementation will be made user interactive in future.
		Strategy strategy = new HitBattleShipStrategy();
		Duty duty = new BattleDuty();
		duty.addStrategy(strategy);
		GamePlayer gamePlayer = new BattleShipGamePlayer(playerName);
		gamePlayer.addDuty(duty);
		return gamePlayer;
	}

	@Override
	public PlatForm buildPlatForm(int platFormSize) throws GameInitializationException {
		//TODO: Only GridPlatForm supported for now. In future, battleShip Game might support 
		//other kind of platForms.
		return new GridPlatForm(platFormSize);
	}

	@Override
	public Deployable buildDeployable(int startX, int startY, int endX, int endY) throws GameInitializationException {
		return new BattleShip(startX, startY, endX, endY);
	}
}
