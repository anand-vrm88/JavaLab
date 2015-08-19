package com.anverm.javalab.game.deploy;


public interface Deployable{
	public boolean isDead();
	public int getStartX();
	public int getStartY();
	public int getEndX();
	public int getEndY();
	public int getStrength();
	public void setStrength(int strength);
}
