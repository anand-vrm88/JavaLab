package com.anverm.javalab.game.platform.buildingblock;

import com.anverm.javalab.game.deploy.Deployable;
import com.anverm.javalab.game.platform.buildingblock.status.PlatFormBuildingBlockStatus;

public interface PlatFormBuildingBlock {
	public void setStatus(PlatFormBuildingBlockStatus platFormBuildingBlockStatus);
	public PlatFormBuildingBlockStatus getStatus();
	public Deployable getDeployabe();
	public void setDeployable(Deployable deployableObject);
}
