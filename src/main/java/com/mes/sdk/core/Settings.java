package com.mes.sdk.core;


public abstract class Settings<T> {
	
	public static enum Method { POST, GET }
	public static final String URL_TEST_404 = "http://httpstat.us/404";
	public static final String URL_TEST_403 = "http://httpstat.us/403";
	public static final String URL_TEST_500 = "http://httpstat.us/500";
	public static final String URL_TEST_503 = "http://httpstat.us/503";
	
	private String hostUrl;
	private Method method;
	private int timeout;
	private boolean verbose;
	private boolean validateRequests;
	
	/**
	 * Creates default settings of method type POST, verbose false, a timeout of 20s, and validation of request objects true.
	 */
	public Settings() {
		method = Method.POST;
		timeout = 20000;
		validateRequests = true;
	}
	
	/**
	 * The endpoint URL for this HTTP request.
	 * @param hostUrl
	 * @return this object for chaining.
	 */
	@SuppressWarnings("unchecked")
	public T hostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
		return (T)this;
	}
	
	/**
	 * The {@link Method} this HTTP request will use.
	 * @param method
	 * @return this object for chaining.
	 */
	@SuppressWarnings("unchecked")
	public T method(Method method) {
		this.method = method;
		return (T)this;
	}
	
	/**
	 * Set the timeout of the HTTP request.
	 * @param timeout
	 * @return this object for chaining.
	 */
	@SuppressWarnings("unchecked")
	public T timeout(int timeout) {
		this.timeout = timeout;
		return (T)this;
	}
	
	/**
	 * Standard output for testing.
	 * @param verbose
	 * @return this object for chaining.
	 */
	@SuppressWarnings("unchecked")
	public T verbose(boolean verbose) {
		this.verbose = verbose;
		return (T)this;
	}
	
	/**
	 * When run is called, {@link RequestObject}s are validated by default. If validateRequests is set to false, RequestObjects can be manually validated at any time by calling RequestObject.validateRequest().<br />
	 * Validation checks for required fields & proper formatting.
	 * @param validate
	 * @return this object for chaining.
	 */
	@SuppressWarnings("unchecked")
	public T validateRequests(boolean validate) {
		this.validateRequests = validate;
		return (T)this;
	}

	public String getHostUrl() {
		return hostUrl;
	}

	public Method getMethod() {
		return method;
	}

	public int getTimeout() {
		return timeout;
	}

	public boolean isVerbose() {
		return verbose;
	}
	
	public boolean validatesRequests() {
		return this.validateRequests;
	}
	
}
