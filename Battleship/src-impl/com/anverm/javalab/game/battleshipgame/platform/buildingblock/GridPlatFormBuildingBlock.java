package com.anverm.javalab.game.battleshipgame.platform.buildingblock;

import com.anverm.javalab.game.deploy.Deployable;
import com.anverm.javalab.game.platform.buildingblock.PlatFormBuildingBlock;
import com.anverm.javalab.game.platform.buildingblock.status.PlatFormBuildingBlockStatus;



public class GridPlatFormBuildingBlock implements PlatFormBuildingBlock{
/*	public static enum Status{
		UNUSED, UCCUPIED, HIT
	}*/
	
	private PlatFormBuildingBlockStatus platFormBuildingBlockStatus;
	private Deployable deployableObject;

	public GridPlatFormBuildingBlock(PlatFormBuildingBlockStatus platFormBuildingBlockStatus, Deployable deployableObject) {
		super();
		this.platFormBuildingBlockStatus = platFormBuildingBlockStatus;
		this.deployableObject = deployableObject;
	}

	@Override
	public void setStatus(PlatFormBuildingBlockStatus platFormBuildingBlockStatus) {
		this.platFormBuildingBlockStatus = platFormBuildingBlockStatus;
	}
	
	@Override
	public PlatFormBuildingBlockStatus getStatus() {
		return platFormBuildingBlockStatus;
	}

	@Override
	public Deployable getDeployabe() {
		return deployableObject;
	}

	//TODO: This method creates confusion. In future, a different way 
	//to be thought to achieve reference to affected ship.
	@Override
	public void setDeployable(Deployable deployableObject) {
		this.deployableObject = deployableObject;
	}
	
	public String toString(){
		return platFormBuildingBlockStatus.toString();
	}
}
