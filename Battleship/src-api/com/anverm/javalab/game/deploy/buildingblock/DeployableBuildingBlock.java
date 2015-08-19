package com.anverm.javalab.game.deploy.buildingblock;

import com.anverm.javalab.game.deploy.buildingblock.status.DeployableBuildingBlockStatus;

public interface DeployableBuildingBlock {
	public DeployableBuildingBlockStatus getStatus();
	public void setStatus(DeployableBuildingBlockStatus deployableBuildingBlockStatus);
	public int getPosX();
	public void setPosX(int posX);
	public int getPosY();
	public void setPosY(int posY);
}
