package com.mes.sdk.reporting;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mes.sdk.core.ApiInterface;
import com.mes.sdk.core.Http;
import com.mes.sdk.exception.MesRuntimeException;

public class Reporting implements ApiInterface<ReportingRequest> {
	
	private final Http http;
	private final ReportingSettings settings;
	private boolean success;
	
	private final static Logger LOG = Logger.getLogger(Reporting.class.getName());
	
	/**
	 * The main object used to communicate.
	 * @param settings An instance of {@link ReportingSettings}.
	 */
	public Reporting(ReportingSettings settings) {
		http = new Http(settings);
		this.settings = settings;
	}
	
	public ReportingResponse run(ReportingRequest request) {
        success = false; // Assume false
        http.setRequestString(parseRequest(request));
        if(settings.isVerbose())
          LOG.log(Level.INFO, "Sending request: "+http.getRequestString());
        http.run();

        ReportingResponse resp = parseResponse();
        if (settings.isVerbose()) {
        	if(wasSuccessful())
        		LOG.info("Response: (HTTP "+http.getHttpCode()+" - "+http.getDuration()+"ms - Request Succeeded) " + resp);
        	else {
        		LOG.info("Response: (HTTP "+http.getHttpCode()+" - "+http.getDuration()+"ms - Request Failed) " + resp);
        		LOG.info(resp.getRawResponse());
        	}
        }
        return resp;
	}

	public String parseRequest(ReportingRequest request) {
        StringBuilder requestString = new StringBuilder();
        requestString.append("userId=").append(settings.getUserName());
        requestString.append("&userPass=").append(settings.getUserPass());

        String reportId = null;
        switch (request.getType())
        {
            case BATCH: reportId = "1"; break;
            case SETTLEMENT: reportId = "2"; break;
            case DEPOSIT: reportId = "3"; break;
            case RECONSCILE: reportId = "4"; break;
            case CHARGEBACKADJUSTMENTS: reportId = "5"; break;
            case CHARGEBACKPRENOT: reportId = "6"; break;
            case RETRIEVAL: reportId = "7"; break;
            case INTERCHANGE: reportId = "8"; break;
            case CUSTOM: reportId = "9"; break;
            case FXBATCH: reportId = "10"; break;
            case ITLCHARGEBACK: reportId = "11"; break;
            case ITLRETRIEVAL: reportId = "12"; break;
            case FXINTERCHANGE: reportId = "13"; break;
            case INTLDETAILS: reportId = "14"; break;
            case AUTHLOG: reportId = "15"; break;
            case GATEWAYREQUESTLOG: reportId = "16"; break;
            case ACHSETTLEMENT: reportId = "17"; break;
            case ACHRETURN: reportId = "18"; break;
            case TRIDENTBATCH: reportId = "19"; break;
            default:
                throw new MesRuntimeException("Report type unsupported: "+request.getType());
        }

        requestString.append("&dsReportId=").append(reportId);

        String reportMode = null;
        switch (request.getMode())
        {
            case SUMMARY: reportMode = "0"; break;
            case DETAIL: reportMode = "1"; break;
            default:
                throw new MesRuntimeException("Report mode unsupported: " + request.getType());
        }
        requestString.append("&reportType=").append(reportMode);
        
        for(Map.Entry<String, String> pair : request.requestTable.entrySet()) {
			try {
				requestString = requestString.append("&").append(pair.getKey()).append("=").append(URLEncoder.encode(pair.getValue(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new MesRuntimeException("Unable to URL Encode the following value: "+pair.getValue());
			}
		}
        return requestString.toString();
	}
	
	public ReportingResponse parseResponse() {
		boolean hasError = false;
		String error = "";
		
		// Validate HTTP result
		if(http.getHttpCode() != 200) {
			error =  "Server responded with HTTP code "+http.getHttpCode();
			hasError = true;
		}
		else {
	        // Some conditions still may return http code 200, with an HTML formatted response.  We've no other way to determine the request did not complete: resort to string parsing.
			String s = http.getRawResponse();
			// We've got an HTML response.
			if(s.matches(".*<html>.*")) {
				if(s.matches(".*Insufficient rights.*"))
					error = "Insufficient Rights";
				else if(s.matches(".*Invalid user/password.*"))
					error = "Invalid username or userpass";
				else
					error = "Report Request Failed";
				hasError = true;
			}
		}

		if(!hasError) {
			success = true;
			ReportingResponse resp = new ReportingResponse(
				http.getHttpCode(),
				http.getHttpText(),
				http.getRawResponse(),
				http.getDuration()
			);
			return resp;
		}
		else {
			ReportingResponse resp = new ReportingResponse(
				http.getHttpCode(),
				http.getHttpText(),
				error,
				http.getDuration()
			);
			return resp;
		}
	}
	
    public boolean wasSuccessful() {
        return success;
    }
}