package com.mes.sdk.exception;

import com.mes.sdk.core.RequestObject;

/**
 * A Validation Exception is thrown when an issue is encountered validating a {@link RequestObject}.
 * @author brice
 *
 */
public class ValidationException extends MesRuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ValidationException(String message) {
		super(message);
	}
	
	public ValidationException(Throwable th) {
		super(th);
	}
	
	public ValidationException(String message, Throwable th) {
		super(message, th);
	}
	
}
