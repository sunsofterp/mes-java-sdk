package com.mes.sdk.rbs;

import com.mes.sdk.core.ApiInterface;
import com.mes.sdk.core.Http;
import com.mes.sdk.exception.MesRuntimeException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Rbs implements ApiInterface<RbsRequest> {

    private final Http http;
    private final RbsSettings settings;

    private final static Logger LOG = Logger.getLogger(Rbs.class.getName());

    /**
     * The main object used to communicate with the Payment Gateway.
     *
     * @param settings An instance of {@link RbsSettings}.
     */
    public Rbs(RbsSettings settings) {
        http = new Http(settings);
        this.settings = settings;
    }

    @Override
    public RbsResponse run(RbsRequest requestObject) {
        http.setRequestString(parseRequest(requestObject));
        if (settings.isVerbose())
            LOG.log(Level.INFO, "Sending request: " + http.getRequestString());
        http.run();

        RbsResponse resp = parseResponse();
//        if (settings.isVerbose())
//        	System.out.println("Response: (HTTP "+http.getHttpCode()+" - "+http.getDuration()+"ms - Request Failed) " + resp);
        return resp;
    }

    @Override
    public String parseRequest(RbsRequest req) {
        switch (req.getRequestType()) {
            case CREATE:
                settings.setUrlPostfix("rbsCreate");
                break;
            case DELETE:
                settings.setUrlPostfix("rbsDelete");
                break;
            case INQUIRY:
                settings.setUrlPostfix("rbsInquiry");
                break;
            case UPDATE:
                settings.setUrlPostfix("rbsUpdate");
                break;
        }

        String requestString = "userId=".concat(settings.getUserName());
        requestString = requestString.concat("&userPass=").concat(settings.getUserPass());
        requestString = requestString.concat("&profileId=").concat(settings.getProfileId());

        for (Map.Entry<String, String> pair : req.requestTable.entrySet()) {
            try {
                requestString = requestString.concat("&").concat(pair.getKey()).concat("=").concat(URLEncoder.encode(pair.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new MesRuntimeException("Unable to URL Encode the following value: " + pair.getKey() + " / " + pair.getValue());
            }
        }
        return requestString;
    }

    public RbsResponse parseResponse() {
        HashMap<String, String> hM = new HashMap<String, String>();
        if (http.getHttpCode() == 200 && http.getRawResponse().length() > 0) {
            String[] xa = http.getRawResponse().split("&");
            if (xa.length > 0) {
                for (int i = 0; i < xa.length; i++) {
                    String[] xb = xa[i].split("=");
                    if (xb.length > 1)
                        hM.put(xb[0], xb[1]);
                    else
                        hM.put(xb[0], "");
                }
            }
        }

        RbsResponse resp = new RbsResponse(
                hM,
                http.getHttpCode(),
                http.getHttpText(),
                http.getRawResponse(),
                http.getDuration()
        );
        return resp;
    }
}