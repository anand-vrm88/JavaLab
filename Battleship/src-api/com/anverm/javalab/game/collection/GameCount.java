package com.anverm.javalab.game.collection;

public interface GameCount {
	public int getHumansCount();
	public int getPlayersCount();
	public int getBotsCount();
	public int getDeployablesCount();
	public void setHumansCount(int humansCount);
	public void setBotsCount(int botsCount);
	public void setDeployablesCount(int deployablesCount);
}
