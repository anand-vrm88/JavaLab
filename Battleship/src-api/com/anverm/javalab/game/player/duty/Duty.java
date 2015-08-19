package com.anverm.javalab.game.player.duty;

import com.anverm.javalab.game.player.duty.strategy.Strategy;
import com.anverm.javalab.game.player.duty.strategy.exception.NoSuchStrategyException;
import com.anverm.javalab.game.target.Target;

/**
 * Execute Strategy either by itself or on Target.
 * @author anand
 *
 */
public interface Duty {
	public String getName();
	public Strategy getStrategy(String strategyType);
	public void addStrategy(Strategy strategy);
	public Strategy removeStrategy(String strategyType);
	public boolean executeStrategy(String strategyType, Target target) throws NoSuchStrategyException;
	public boolean executeStrategy(String strategyType) throws NoSuchStrategyException;
}
