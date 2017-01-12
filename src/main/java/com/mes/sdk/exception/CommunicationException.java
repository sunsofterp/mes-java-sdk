package com.mes.sdk.exception;

/**
 * Re-thrown from communication issues, such as SocketTimeoutException.
 *
 * @author brice
 */
public class CommunicationException extends MesRuntimeException {

    private static final long serialVersionUID = 1L;

    public CommunicationException(String message) {
        super(message);
    }

}
