package com.mes.sdk.exception;

/**
 * Thrown when an unsupported request field is manually added.
 *
 * @author brice
 */
public class InvalidFieldException extends MesRuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidFieldException(String message) {
        super(message);
    }

    public InvalidFieldException(Throwable th) {
        super(th);
    }

    public InvalidFieldException(String message, Throwable th) {
        super(message, th);
    }
}
