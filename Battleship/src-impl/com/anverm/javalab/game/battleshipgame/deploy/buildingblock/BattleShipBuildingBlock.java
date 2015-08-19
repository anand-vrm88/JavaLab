package com.anverm.javalab.game.battleshipgame.deploy.buildingblock;

import com.anverm.javalab.game.deploy.buildingblock.DeployableBuildingBlock;
import com.anverm.javalab.game.deploy.buildingblock.status.DeployableBuildingBlockStatus;

public class BattleShipBuildingBlock implements DeployableBuildingBlock{
	private DeployableBuildingBlockStatus deployableBuildingBlockStatus;
	private int posX;
	private int posY;

	public BattleShipBuildingBlock() {
		this(null, -1, -1);
	}
	
	public BattleShipBuildingBlock(DeployableBuildingBlockStatus deployableBuildingBlockStatus) {
		this(deployableBuildingBlockStatus, -1, -1);
	}

	public BattleShipBuildingBlock(int posX, int posY) {
		this(null, posX, posY);
	}
	
	public BattleShipBuildingBlock(DeployableBuildingBlockStatus deployableBuildingBlockStatus, int posX, int posY) {
		this.deployableBuildingBlockStatus = deployableBuildingBlockStatus;
		this.posX = posX;
		this.posY = posY;
	}

	@Override
	public DeployableBuildingBlockStatus getStatus() {
		return deployableBuildingBlockStatus;
	}

	@Override
	public void setStatus(DeployableBuildingBlockStatus deployableBuildingBlockStatus) {
		this.deployableBuildingBlockStatus = deployableBuildingBlockStatus;
	}

	@Override
	public int getPosX() {
		return posX;
	}

	@Override
	public void setPosX(int posX) {
		this.posX = posX;
	}

	@Override
	public int getPosY() {
		return posY;
	}

	@Override
	public void setPosY(int posY) {
		this.posY = posY;
	}
}
