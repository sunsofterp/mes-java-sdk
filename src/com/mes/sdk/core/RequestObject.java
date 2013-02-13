package com.mes.sdk.core;

import java.util.HashMap;

import com.mes.sdk.exception.ValidationException;

public abstract class RequestObject {

	public HashMap<String, String> requestTable;
	
	protected RequestObject() {
		requestTable = new HashMap<String, String>();
	}
	
	/**
	 * Sets a specific request field and value to be sent
	 * @param fieldName
	 * @param value
	 * @return this for chaining
	 */
	public RequestObject setParameter(String fieldName, String value) {
		requestTable.put(fieldName, value);  // Also replaces existing value
		return this;
	}
	
	public void removeParameter(String fieldName) {
		requestTable.remove(fieldName);
	}
	
	public RequestObject setParameter(String fieldName, boolean value) {
		return setParameter(fieldName, Boolean.toString(value));
	}
	
	public RequestObject setParameter(String fieldName, int value) {
		return setParameter(fieldName, Integer.toString(value));
	}
	
	public RequestObject setParameter(String fieldName, float value) {
		return setParameter(fieldName, Float.toString(value));
	}
	
	/**
	 * <p>Used for validating a request object contains all the proper fields before it's processed.
	 * May throw the runtime exception {@link ValidationException}.</p>
	 */
	public abstract void validateRequest();
	
}
