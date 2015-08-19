package com.anverm.javalab.game.battleshipgame.platform;

import java.util.HashSet;
import java.util.Set;

import com.anverm.javalab.game.battleshipgame.platform.buildingblock.GridPlatFormBuildingBlock;
import com.anverm.javalab.game.deploy.Deployable;
import com.anverm.javalab.game.platform.PlatForm;
import com.anverm.javalab.game.platform.buildingblock.status.PlatFormBuildingBlockStatus;

public class GridPlatForm implements PlatForm{
	private Set<Deployable> deployableObjects;
	private int deployableObjectsCount;
	private int platFormSize;
	private GridPlatFormBuildingBlock[][] gridPlatFormBuildingBlocks;
	
	public GridPlatForm(int platFormSize) {
		super();
		this.platFormSize = platFormSize;
		gridPlatFormBuildingBlocks = new GridPlatFormBuildingBlock[platFormSize][platFormSize];
		for(int i = 0; i < platFormSize; i++){
			for(int j = 0; j < platFormSize; j++){
				gridPlatFormBuildingBlocks[i][j] = new GridPlatFormBuildingBlock(PlatFormBuildingBlockStatus.UNUSED, null);
			}
		}
		deployableObjects = new HashSet<Deployable>();
	}
	
	@Override
	public boolean deploy(Deployable deployableObject){
		int startX = deployableObject.getStartX();
		int startY = deployableObject.getStartY();
		int endX = deployableObject.getEndX();
		int endY = deployableObject.getEndY();
		//Late check whether object to deploy can be accommodated in platForm
		//without conflict with other object deployed and in object is deployed
		//in allowed orientation.
		if(!isDeployableValid(startX, startY, endX, endY)){
			return false;
		}
		putDeployableOnPlatFormBuildingBlocks(deployableObject);
		deployableObjects.add(deployableObject);
		deployableObjectsCount++;
		System.out.println("[Battle]$ Battleship deployed at coordinates["+startX+" "+startY+" "+endX+" "+endY+"]");
		return true;
	}
	
	//Late check whether deployable object is worth accommodate on platform.
	private boolean isDeployableValid(int startX, int startY, int endX, int endY){
		//1. Is ship out of gridBoard.
		//2. Is ship cross placed.
		//3. Is ship in collision.
		
		return !isShipOutOfGridBoard(startX, startY, endX, endY) && !isShipCrossPlaced(startX, startY, endX, endY) && !isShipInCollision(startX, startY, endX, endY);
	}
	
	private boolean isShipOutOfGridBoard(int startX, int startY, int endX, int endY){
		boolean beyondLowerBound = startX < 0 || startY < 0 || endX < 0 || endY < 0;
		boolean beyondUpperBound = startX >= platFormSize || startY >= platFormSize || endX >= platFormSize || endY >= platFormSize;
		return beyondLowerBound || beyondUpperBound;
	}
	
	private boolean isShipCrossPlaced(int startX, int startY, int endX, int endY){
		return (startX != endX)&&(startY != endY);
	}
	
	private boolean isShipInCollision(int startX, int startY, int endX, int endY){
		int start = 0;
		int end = 0;
		if(startX == endX){
			start = startY<endY?startY:endY;
			end = startY<endY?endY:startY;
			for(int j = start; j<=end; j++){
				if(gridPlatFormBuildingBlocks[startX][j].getStatus() == PlatFormBuildingBlockStatus.UCCUPIED)
					return true;
			}
			return false;
		} else if(startY == endY){
			start = startX<endX?startX:endX;
			end = startX>endX?startX:endX;
			for(int i = start; i<=end; i++){
				if(gridPlatFormBuildingBlocks[i][startY].getStatus() == PlatFormBuildingBlockStatus.UCCUPIED)
					return true;
			}			
			return false;
		}
		return true;
	}

	private boolean putDeployableOnPlatFormBuildingBlocks(Deployable deployableObject){
		int startX = deployableObject.getStartX();
		int startY = deployableObject.getStartY();
		int endX = deployableObject.getEndX();
		int endY = deployableObject.getEndY();

		int start = 0;
		int end = 0;

		if(startX == endX){
			start = startY<endY?startY:endY;
			end = startY<endY?endY:startY;
			for(int j = start; j<=end; j++){
				gridPlatFormBuildingBlocks[startX][j].setStatus(PlatFormBuildingBlockStatus.UCCUPIED);
				gridPlatFormBuildingBlocks[startX][j].setDeployable(deployableObject);
			}
		} else if(startY == endY){
			start = startX<endX?startX:endX;
			end = startX>endX?startX:endX;
			for(int i = start; i<=end; i++){
				gridPlatFormBuildingBlocks[i][startY].setStatus(PlatFormBuildingBlockStatus.UCCUPIED);
				gridPlatFormBuildingBlocks[i][startY].setDeployable(deployableObject);
			}			
		}
		return false;
	}

	public void markGridAsHit(int posX, int posY){
		gridPlatFormBuildingBlocks[posX][posY].setStatus(PlatFormBuildingBlockStatus.HIT);
	}
	
	public GridPlatFormBuildingBlock[][] getGrid(){
		return gridPlatFormBuildingBlocks;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < platFormSize; i++){
			for(int j = 0; j < platFormSize; j++){
				sb.append(gridPlatFormBuildingBlocks[i][j].toString()+"\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	@Override
	public boolean affectDeployableOnBuildingBlock(int posX, int posY){
		GridPlatFormBuildingBlock gridPlatFormBuildingBlock = gridPlatFormBuildingBlocks[posX][posY];
		if(gridPlatFormBuildingBlock.getStatus() == PlatFormBuildingBlockStatus.UNUSED){
			gridPlatFormBuildingBlock.setStatus(PlatFormBuildingBlockStatus.HIT);
			return false;
		}else if(gridPlatFormBuildingBlock.getStatus() == PlatFormBuildingBlockStatus.HIT){
			return false;
		}else if(gridPlatFormBuildingBlock.getStatus() == PlatFormBuildingBlockStatus.UCCUPIED){
			Deployable deployableObject = gridPlatFormBuildingBlock.getDeployabe();
			//battleShip.decreaseShipStrength();
			gridPlatFormBuildingBlock.setStatus(PlatFormBuildingBlockStatus.HIT);
			if(deployableObject.isDead()){
				deployableObjectsCount--;
			}
			return true;
		}
		return false;
	}
	
	public boolean areAllBattleShipsDestroyed(){
		return deployableObjectsCount == 0;
	}

	@Override
	public Deployable getDeployable(int posX, int posY) {
		return gridPlatFormBuildingBlocks[posX][posY].getDeployabe();
	}

	@Override
	public Set<Deployable> getAllDeployables() {
		return deployableObjects;
	}
}
