package com.anverm.javalab.game.platform;

import java.util.Set;

import com.anverm.javalab.game.deploy.Deployable;

//TODO: Need to decide valid contracts here.
public interface PlatForm {
	//PlatForm on which Game will be played.
	public boolean deploy(Deployable deployableObject);
	public boolean affectDeployableOnBuildingBlock(int posX, int posY);
	public Deployable getDeployable(int posX, int posY);
	public Set<Deployable> getAllDeployables();
}
