package com.anverm.javalab.game.battleshipgame.target;

import com.anverm.javalab.game.battleshipgame.player.BattleShipGamePlayer;
import com.anverm.javalab.game.deploy.Deployable;
import com.anverm.javalab.game.deploy.affect.Affectable;
import com.anverm.javalab.game.platform.PlatForm;
import com.anverm.javalab.game.target.Target;

public class BattleShipGameTarget implements Target{

	private BattleShipGamePlayer battleShipGamePlayer;
	private int targetX;
	private int targetY;
	
	public BattleShipGameTarget(BattleShipGamePlayer battleShipGamePlayer, int targetX, int targetY) {
		this.battleShipGamePlayer = battleShipGamePlayer;
		this.targetX = targetX;
		this.targetY = targetY;
	}

	@Override
	public boolean affect() {
		PlatForm platForm = battleShipGamePlayer.getPlatForm();
		Deployable deployabeObject = platForm.getDeployable(targetX, targetY);
		if (deployabeObject != null && deployabeObject instanceof Affectable) {
			Affectable affectable = (Affectable) deployabeObject;
			return affectable.affect(targetX, targetY);
		} else {
			return false;
		}
	}

}
