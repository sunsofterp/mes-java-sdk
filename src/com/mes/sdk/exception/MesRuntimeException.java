package com.mes.sdk.exception;

/**
 * A generic parent exception that is thrown while creating and sending requests. 
 * @author brice
 *
 */
public class MesRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MesRuntimeException(String message) {
		super(message);
	}
	
	public MesRuntimeException(Throwable th) {
		super(th);
	}
	
	public MesRuntimeException(String message, Throwable th) {
		super(message, th);
	}
}
