package com.mes.sdk.rbs;

import java.util.HashMap;

import com.mes.sdk.core.ResponseObject;

/**
 * Adds to the {@link ResponseObject} methods specific to the Recurring Billing System. 
 * @author BRice
 *
 */
public class RbsResponse extends ResponseObject {
	
	public RbsResponse(HashMap<String, String> response, int httpCode, String httpText, String rawResponse, float duration) {
		super(response, httpCode, httpText, rawResponse, duration);
	}
	
	/**
	 * Get the response error code.
	 * @return 
	 */
	public String getErrorCode() {
		return super.getResponseValue("rspCode");
	}
	
	/**
	 * Get the human readable textual response. <b>Not intended to be parsed.</b>
	 * @return Text result
	 */
	public String getResponseText() {
		return super.getResponseValue("rspMessage");
	}
	
	public boolean requestSuccessful() {
		String respCode = super.getResponseValue("rspCode");
		if(respCode == null || !respCode.equals("000")) return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "[Success:"+requestSuccessful()+"] " + super.toString();
	}
	
}
