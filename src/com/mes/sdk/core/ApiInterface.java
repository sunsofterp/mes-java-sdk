package com.mes.sdk.core;

public interface ApiInterface<T extends RequestObject> {
	
	public String parseRequest(T requestObject);
	
	public ResponseObject run(T requestObject);
	
	public ResponseObject parseResponse();
}
