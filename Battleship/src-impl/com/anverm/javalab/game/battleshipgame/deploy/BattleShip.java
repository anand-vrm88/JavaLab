package com.anverm.javalab.game.battleshipgame.deploy;

import com.anverm.javalab.game.battleshipgame.deploy.buildingblock.BattleShipBuildingBlock;
import com.anverm.javalab.game.deploy.Deployable;
import com.anverm.javalab.game.deploy.affect.Affectable;
import com.anverm.javalab.game.deploy.buildingblock.status.DeployableBuildingBlockStatus;

/**
 * BattleShip is immovable and it is always placed as left to right or top to bottom 
 * i.e. there is no choice of placing battleShip with its front and back in specified
 * direction.
 * @author anand
 *
 */
public class BattleShip implements Deployable, Affectable{
	private int startX, startY;
	private int endX, endY;
	private int shipStrength = 0;
	private BattleShipBuildingBlock[] battleShipBuildingBlock;
	
	
	public BattleShip(int startX, int startY, int endX, int endY){
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		
		int start = 0;
		int end = 0;

		if(startX == endX){
			start = startY<endY?startY:endY;
			end = startY<endY?endY:startY;
		} else if(startY == endY){
			start = startX<endX?startX:endX;
			end = startX>endX?startX:endX;
		}
		
		int shipLength = end-start+1;
		battleShipBuildingBlock = new BattleShipBuildingBlock[shipLength];
		for(int i = 0; i<shipLength; i++){
			//Each building block to know its status and its coordinate on platform.
			if(startX == endX){
				battleShipBuildingBlock[i] = new BattleShipBuildingBlock(DeployableBuildingBlockStatus.INTECT, startX, start + i);
			} else if(startY == endY){
				battleShipBuildingBlock[i] = new BattleShipBuildingBlock(DeployableBuildingBlockStatus.INTECT, start + i, startY);
			}
		}
		
		//Strength criteria for battleShip is its length.
		shipStrength = shipLength;
	}
	
	@Override
	public boolean isDead(){
		
		//TODO: This method should contain this check only. 
		//It will be faster and easy to read the code.
		if(shipStrength == 0){
			return true;
		}
		
		int start = 0;
		int end = 0;
		int length = 0;
		if(startX == endX){
			start = startY<endY?startY:endY;
			end = startY<endY?endY:startY;
			length = end-start+1;
			for(int i = 0; i<length; i++){
				if(battleShipBuildingBlock[i].getStatus() == DeployableBuildingBlockStatus.INTECT)
					return false;
			}
		} else if(startY == endY){
			start = startX<endX?startX:endX;
			end = startX>endX?startX:endX;
			length = end-start+1;
			for(int i = 0; i<length; i++){
				if(battleShipBuildingBlock[i].getStatus() == DeployableBuildingBlockStatus.INTECT)
					return false;
			}			
		}
		return true;
	}
	
	/**
	 * Affects building blocks of this BattleShip.
	 */
	@Override
	public boolean affect(int targetX, int targetY) {
		int start = -1;
		int end = -1;
		
		if (startX == endX) {
			start = startY<endY?startY:endY;
			end = startY<endY?endY:startY;
			if ((startX == targetX) && targetY >= start && targetY <= end) {
				if(battleShipBuildingBlock[targetY - start].getStatus() != DeployableBuildingBlockStatus.AFFECTED){
					battleShipBuildingBlock[targetY - start].setStatus(DeployableBuildingBlockStatus.AFFECTED);
					decreaseStrength();
					return true;
				}
			}
		} else if (startY == endY) {
			start = startX<endX?startX:endX;
			end = startX>endX?startX:endX;
			if ((startY == targetY) && targetX >= start && targetX <= end) {
				if(battleShipBuildingBlock[targetX - start].getStatus() != DeployableBuildingBlockStatus.AFFECTED){
					battleShipBuildingBlock[targetX - start].setStatus(DeployableBuildingBlockStatus.AFFECTED);
					decreaseStrength();
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public int getStartX() {
		return startX;
	}

	@Override
	public int getStartY() {
		return startY;
	}

	@Override
	public int getEndX() {
		return endX;
	}

	@Override
	public int getEndY() {
		return endY;
	}

	/**
	 * TODO: This method for now does nothing but in future, we might
	 * have feature of putting battleShips back to life.
	 */
	@Override
	public void setStrength(int strength) {
	}
	
	@Override
	public int getStrength() {
		return shipStrength;
	}

	//TODO: Moving decreasing strength code in method block
	//because in future strength criteria of battleShip can
	//more complex.
	private void decreaseStrength() {
		shipStrength--;
	}

	/**
	 * Two BattleShip Objects are same if they land on same coordinate irrespective of their state.
	 * For override of this method necessary by contract.
	 */
	@Override
	public boolean equals(Object anObject){
		if(anObject == null || !(anObject instanceof Deployable)){
			return false;
		}
		
		Deployable deployableObject = (Deployable)anObject;
		
		if(startX == deployableObject.getStartX() && startY == deployableObject.getStartY() && endX == deployableObject.getEndX() && endY == deployableObject.getEndY()){
			return true;
		}

		return false;
	}
	
	/**
	 * This should be close to producing unique hash code for different BattleShips.
	 * For override of this method necessary by contract.
	 */
	@Override
	public int hashCode(){
		return startX*3 + startY*5 + endX*7 + endY*11;
	}

}
