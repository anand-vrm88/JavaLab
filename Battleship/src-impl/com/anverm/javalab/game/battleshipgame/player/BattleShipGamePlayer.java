package com.anverm.javalab.game.battleshipgame.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.anverm.javalab.game.battleshipgame.player.duty.BattleDuty;
import com.anverm.javalab.game.deploy.Deployable;
import com.anverm.javalab.game.exception.GamePlayException;
import com.anverm.javalab.game.platform.PlatForm;
import com.anverm.javalab.game.player.GamePlayer;
import com.anverm.javalab.game.player.duty.Duty;
import com.anverm.javalab.game.player.duty.exception.NoSuchDutyException;
import com.anverm.javalab.game.player.duty.strategy.Strategy;
import com.anverm.javalab.game.player.duty.strategy.exception.NoSuchStrategyException;
import com.anverm.javalab.game.target.Target;

//BattleShip GamePlayer will also be Target object to hit.  
//But this is a bit confusing because actual targets are player's BattleShips
//TODO: Target should be restructured in future.
public class BattleShipGamePlayer implements GamePlayer{
	private static final Logger logger = Logger.getLogger("BattleShipPlayer.class"); 
	private String playerName;
	private PlatForm platForm;
	private int playerScore = 0;
	private Map<String, Duty> dutyMap;
	
	public BattleShipGamePlayer(String playerName){
		this(playerName, null, null);
	}

	public BattleShipGamePlayer(String playerName, Duty duty){
		this(playerName, null, duty);
	}
	
	public BattleShipGamePlayer(String playerName, PlatForm platForm, Duty duty){
		this.playerName = playerName;
		this.platForm =  platForm;
		dutyMap = new HashMap<String, Duty>();
		
		//TODO: In future, other duties will also be supported. 
		if(duty != null && duty instanceof BattleDuty){
			dutyMap.put(duty.getName(), duty);
		}
	}
	
	@Override
	public void setPlatForm(PlatForm platForm) {
		this.platForm = platForm;
	}
	
	@Override
	public PlatForm getPlatForm() {
		return platForm;
	}

	@Override
	public int getScore() {
		return playerScore;
	}

	@Override
	public boolean isLost() {
		if(platForm == null){
			logger.severe("Player "+playerName+" has no platForm assigned. Please assign a platform.");
			return true;
		}
		PlatForm platForm = this.getPlatForm();
		Set<Deployable> deployableObjects = platForm.getAllDeployables();
		for(Deployable deployableObject : deployableObjects){
			if(!deployableObject.isDead()){
				return false;
			}
		}
		return true;
	}

	@Override
	public String getName() {
		return playerName;
	}

	@Override
	public boolean doDuty(String dutyType, String strategyType) throws NoSuchDutyException, NoSuchStrategyException, GamePlayException{
		throw new GamePlayException(new UnsupportedOperationException("Currently no self duty supported"));
/*		Duty duty = dutyMap.get(dutyType);
		if(duty == null){
			throw new NoSuchDutyException("Duty \""+dutyType+"\" is not available for "+this.getName());
		}
		if(duty.executeStrategy(strategyType)){
			playerScore++;
		}*/		
	}

	@Override
	public boolean doDuty(String dutyType, String strategyType, Target target) throws NoSuchDutyException, NoSuchStrategyException {
		Duty duty = dutyMap.get(dutyType);
		if(duty == null){
			throw new NoSuchDutyException("Duty \""+dutyType+"\" is not available for "+this.getName());
		}
		
		if(duty.executeStrategy(strategyType, target)){
			playerScore++;
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void addDuty(Duty duty) {
		if(dutyMap.containsKey(duty.getName())){
			return;
		}
		//TODO: In future, other duties will also be supported. 
		if (duty != null && duty instanceof BattleDuty) {
			dutyMap.put(duty.getName(), duty);
		}
	}

	@Override
	public void removeDuty(String dutyType) {
		dutyMap.remove(dutyType);
	}
	
	@Override
	public void addStrategy(String dutyType, Strategy strategy) throws NoSuchDutyException {
		Duty duty = dutyMap.get(dutyType);
		if (duty == null) {
			throw new NoSuchDutyException("Duty \"" + dutyType + "\" is not available for " + this.getName());
		}
		duty.addStrategy(strategy);
	}

	@Override
	public void removeStrategy(String dutyType, String strategyType) throws NoSuchDutyException {
		Duty duty = dutyMap.get(dutyType);
		if (duty == null) {
			throw new NoSuchDutyException("Duty \"" + dutyType + "\" is not available for " + this.getName());
		}
		duty.removeStrategy(strategyType);
	}

}
