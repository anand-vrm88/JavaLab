package com.anverm.javalab.game.battleshipgame.player.duty;

import java.util.HashMap;
import java.util.Map;

import com.anverm.javalab.game.battleshipgame.player.duty.strategy.HitBattleShipStrategy;
import com.anverm.javalab.game.player.duty.Duty;
import com.anverm.javalab.game.player.duty.strategy.Strategy;
import com.anverm.javalab.game.player.duty.strategy.exception.NoSuchStrategyException;
import com.anverm.javalab.game.target.Target;

public class BattleDuty implements Duty{
	private Map<String, Strategy> strategyMap;
	public static final String dutyName = "BattleDuty";
	public static final String HitBatteShipStrategy = "HitBatteShipStrategy";
	
	public BattleDuty() {
		this(null);
	}

	public BattleDuty(Strategy strategy) {
		strategyMap = new HashMap<String, Strategy>();
		//TODO:Currently only HitBattleShipStrategy supported.
		if(strategy != null && strategy instanceof HitBattleShipStrategy){
			strategyMap.put(strategy.getName(), strategy);
		}
	}
	
	@Override
	public String getName() {
		return dutyName;
	}
	
	@Override
	public Strategy getStrategy(String strategyType) {
		return strategyMap.get(strategyType);
	}

	@Override
	public void addStrategy(Strategy strategy) {
		if(strategyMap.containsKey(strategy.getName())){
			return;
		}
		//TODO:Currently only HitBattleShipStrategy supported.
		if(strategy != null && strategy instanceof HitBattleShipStrategy){
			strategyMap.put(strategy.getName(), strategy);
		}
	}

	@Override
	public Strategy removeStrategy(String strategyType) {
		if(strategyType == null){
			return null;
		}
		return strategyMap.remove(strategyType);
	}

	@Override
	public boolean executeStrategy(String strategyType, Target target) throws NoSuchStrategyException {
		Strategy strategy = strategyMap.get(strategyType);
		if(strategy == null){
			throw new NoSuchStrategyException("Strategy \""+strategyType+"\" is not available with "+this.getClass().getName());
		}
		return strategy.affectTarget(target);
	}

	@Override
	public boolean executeStrategy(String strategyType) throws NoSuchStrategyException {
		// TODO:In future, Task which is irrelevant of target will be implemented.
		return false;
	}

}
