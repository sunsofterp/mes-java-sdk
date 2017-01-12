package com.mes.sdk.core;

import java.util.HashMap;

public class ResponseObject {

	protected final HashMap<String, String> response;
	protected final int httpCode;
	protected final String httpText;
	protected final String rawResponse;
	protected final float duration;
	
	public ResponseObject(HashMap<String, String> parsedResponse, int httpCode, String httpText, String rawResponse, float duration) {
		this.response = parsedResponse;
		this.httpCode = httpCode;
		this.httpText = httpText;
		this.rawResponse = rawResponse;
		this.duration = duration;
	}
	
	/**
	 * Get the whole HashMap of response key/values.
	 * @return HashMap
	 */
	public HashMap<String, String> getResponseValues() {
		return response;
	}

	/**
	 * Get the HTTP code returned by the request (200, 404, etc).
	 * @return Http Code Integer
	 */
	public int getHttpCode() {
		return httpCode;
	}
	
	/**
	 * Get the Textual response of the HTTP code (OK, FORBIDDEN, etc).
	 * @return Http Code Text
	 */
	public String getHttpText() {
		return httpText;
	}
	
	/**
	 * Get the raw text from the HTTP request.
	 * @return Raw Response String
	 */
	public String getRawResponse() {
		return rawResponse;
	}

	/**
	 * Get the round trip time the request took to process.
	 * @return Request Duration in ms
	 */
	public float getDuration() {
		return duration;
	}
	
	/**
	 * Search for a response key, returning it's value.<br />
	 * If it is not present - (dash) is returned. 
	 * @param Key
	 * @return Response Value
	 */
	public String getResponseValue(String key) {
		if(response.containsKey(key))
			return response.get(key);
		else
			return "-";
	}
	
	@Override
	public String toString() {
		return "[HTTP:"+httpCode+"] [duration:"+duration+"ms] ";
	}
	
}
