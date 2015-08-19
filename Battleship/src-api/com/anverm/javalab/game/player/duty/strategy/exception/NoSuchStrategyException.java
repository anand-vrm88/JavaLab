package com.anverm.javalab.game.player.duty.strategy.exception;

public class NoSuchStrategyException extends Exception{

	private static final long serialVersionUID = 1L;

	public NoSuchStrategyException() {
		super();
	}

	public NoSuchStrategyException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchStrategyException(String message) {
		super(message);
	}

	public NoSuchStrategyException(Throwable cause) {
		super(cause);
	}
	
}
