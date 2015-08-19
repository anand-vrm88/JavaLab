package com.anverm.javalab.game.battleshipgame.collection;

import com.anverm.javalab.game.collection.GameCount;

public class BattleShipGameCount implements GameCount{
	private int playersCount;
	private int humansCount;
	private int botsCount;
	private int battleShipCount;
	
	public BattleShipGameCount(int playersCount, int botsCount, int battleShipCount) {
		super();
		this.playersCount = playersCount;
		this.humansCount = playersCount - botsCount;
		this.botsCount = botsCount;
		this.battleShipCount = battleShipCount;
	}

	@Override
	public int getPlayersCount() {
		return playersCount;
	}

	@Override
	public int getHumansCount(){
		return humansCount;
	}
	
	@Override
	public int getBotsCount() {
		return botsCount;
	}

	@Override
	public int getDeployablesCount() {
		return battleShipCount;
	}

	@Override
	public void setHumansCount(int humansCount) {
		this.humansCount = humansCount;
	}

	@Override
	public void setBotsCount(int botsCount) {
		this.botsCount = botsCount;
	}

	@Override
	public void setDeployablesCount(int deployablesCount) {
		this.battleShipCount = deployablesCount;
	}
}
