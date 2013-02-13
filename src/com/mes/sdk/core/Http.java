package com.mes.sdk.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import com.mes.sdk.exception.CommunicationException;
import com.mes.sdk.exception.MesRuntimeException;

/**
 * Used for POSTing or GETting data.
 * @author BRice
 */
public class Http {
	
	protected final Settings<?> settings;
	protected String requestString;
	protected int httpCode;
	protected String httpResponse;
	protected String rawResponse;
	protected float duration;
	
	/**
	 * Create a new HTTP object based on the settings set in {@link Settings}.
	 * @param settings
	 */
	public Http(Settings<?> settings) {
		this.settings = settings;
	}
	
	/**
	 * Processes the HTTP request.
	 */
	public void run() {
		HttpURLConnection connection = null;
		String resp = new String();
		
		long start = System.currentTimeMillis();
		try {
			if(settings.isVerbose())
				System.out.print("Connecting to: "+settings.getHostUrl()+" ... ");
			
			if(settings.getMethod() == Settings.Method.GET)
				connection = (HttpURLConnection) new URL(settings.getHostUrl().concat("?").concat(requestString)).openConnection();
			else {
				connection = (HttpURLConnection) new URL(settings.getHostUrl()).openConnection();
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
				
				connection.setRequestProperty("Accept-Charset", "UTF-8");
				connection.setReadTimeout(settings.getTimeout());
				connection.setRequestMethod(settings.getMethod().toString());
				connection.setDoInput(true);
				
				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
				writer.write(requestString);
				writer.flush();
				writer.close();
				
				this.httpCode = connection.getResponseCode();
				this.httpResponse = connection.getResponseMessage();
				
				if(settings.isVerbose())
					System.out.println(httpCode + ":" + httpResponse);
			}
			
			
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(
					new InputStreamReader(connection.getInputStream())
				);
				
				String inputLine = new String();
				while( (inputLine = reader.readLine()) != null) {
					resp += inputLine;
				}
				reader.close();
				if(settings.isVerbose())
					System.out.println(resp);
			}
		} catch (SocketTimeoutException e) {
			throw new CommunicationException("Request timed out after "+settings.getTimeout()+"ms.");
		} catch (IOException e) {
			throw new MesRuntimeException(e);
		} finally {
			if(connection != null)
				connection.disconnect();
			this.duration = System.currentTimeMillis() - start;
			this.rawResponse = resp;
		}
		
	}

	public void setRequestString(String requestString) {
		this.requestString = requestString;
	}
	
	public String getRequestString() {
		return requestString;
	}

	public int getHttpCode() {
		return httpCode;
	}

	public String getHttpText() {
		return httpResponse;
	}

	public String getRawResponse() {
		return rawResponse;
	}

	public float getDuration() {
		return duration;
	}
	
}
