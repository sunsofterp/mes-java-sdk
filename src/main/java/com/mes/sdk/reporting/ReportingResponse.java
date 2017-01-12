package com.mes.sdk.reporting;

import com.mes.sdk.core.ResponseObject;
import com.mes.sdk.exception.MesRuntimeException;

import java.util.HashMap;

/**
 * The ReportingResponse adds to {@link ResponseObject} with the data return from the Reporting API.
 *
 * @author brice
 */
public class ReportingResponse extends ResponseObject {

    public ReportingResponse(int httpCode, String httpText, String rawResponse, float duration) {
        super(null, httpCode, httpText, rawResponse, duration);
    }

    @Override
    public HashMap<String, String> getResponseValues() {
        throw new MesRuntimeException("Response values not supported via Reporting API.");
    }

    @Override
    public String getResponseValue(String key) {
        throw new MesRuntimeException("Response values not supported via Reporting API.");
    }

}