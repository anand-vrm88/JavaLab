package com.anverm.javalab.game.player.duty.strategy;

import com.anverm.javalab.game.target.Target;

/**
 * Strategy can be executed by itself or on a Target.
 * @author anand
 *
 */
public interface Strategy {
	public String getName();
	public boolean takeEffect();
	public boolean affectTarget(Target target);
}
