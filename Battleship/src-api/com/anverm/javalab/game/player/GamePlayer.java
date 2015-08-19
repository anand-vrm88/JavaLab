package com.anverm.javalab.game.player;


import com.anverm.javalab.game.exception.GamePlayException;
import com.anverm.javalab.game.platform.PlatForm;
import com.anverm.javalab.game.player.duty.Duty;
import com.anverm.javalab.game.player.duty.exception.NoSuchDutyException;
import com.anverm.javalab.game.player.duty.strategy.Strategy;
import com.anverm.javalab.game.player.duty.strategy.exception.NoSuchStrategyException;
import com.anverm.javalab.game.target.Target;

/**
 * GamePlayer does its Duty by itself or on Target.
 * @author anand
 */
public interface GamePlayer {
	public int getScore();
	public boolean isLost();
	public void setPlatForm(PlatForm platForm);
	public PlatForm getPlatForm();
	public String getName();
	public boolean doDuty(String dutyType, String strategyType) throws NoSuchDutyException, NoSuchStrategyException, GamePlayException;
	public boolean doDuty(String dutyType, String strategyType, Target target) throws NoSuchDutyException, NoSuchStrategyException;
	public void addDuty(Duty duty);
	public void removeDuty(String dutyType);
	public void addStrategy(String dutyType, Strategy strategy) throws NoSuchDutyException;
	public void removeStrategy(String dutyType, String strategyType) throws NoSuchDutyException;
}
