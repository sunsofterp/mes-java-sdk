package com.mes.sdk.gateway;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.mes.sdk.core.ApiInterface;
import com.mes.sdk.core.Http;
import com.mes.sdk.exception.MesRuntimeException;

public class Gateway implements ApiInterface<GatewayRequest> {
	
	private final Http http;
	private final GatewaySettings settings;

	/**
	 * The main object used to communicate with the Payment Gateway.
	 * @param settings An instance of {@link GatewaySettings}.
	 */
	public Gateway(GatewaySettings settings) {
		http = new Http(settings);
		this.settings = settings;
	}
	
	public GatewayResponse run(GatewayRequest requestObject) {
		if(settings.validatesRequests())
			requestObject.validateRequest();
		http.setRequestString(parseRequest(requestObject));
		http.run();
		return parseResponse();
	}

	public String parseRequest(GatewayRequest req) {
		String requestString = "profile_id=".concat(settings.getProfileId());
		requestString = requestString.concat("&profile_key=").concat(settings.getProfileKey());
		
		String typeCode = null;
		switch( ((GatewayRequest) req).getType() ) {
		case SALE: typeCode = "D"; break;
		case PREAUTH: typeCode = "P"; break;
		case REAUTH: typeCode = "J"; break;
		case OFFLINE: typeCode = "O"; break;
		case SETTLE: typeCode = "S"; break;
		case VOID: typeCode = "V"; break;
		case CREDIT: typeCode = "C"; break;
		case REFUND: typeCode = "U"; break;
		case VERIFY: typeCode = "A"; break;
		case TOKENIZE: typeCode = "T"; break;
		case DETOKENIZE: typeCode = "X"; break;
		case BATCHCLOSE: typeCode = "Z"; break;
		}
		
		if(typeCode != null)
			requestString = requestString.concat("&transaction_type=").concat(typeCode);
		
		for(Map.Entry<String, String> pair : req.requestTable.entrySet()) {
			try {
				requestString = requestString.concat("&").concat(pair.getKey()).concat("=").concat(URLEncoder.encode(pair.getValue(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new MesRuntimeException("Unable to URL Encode the following value: "+pair.getValue());
			}
		}
		return requestString;
	}
	
	public GatewayResponse parseResponse() {
		// Create new GatewayResponse, 
		boolean isApproved = false;
		
		HashMap<String, String> hM = new HashMap<String, String>();
		if(http.getHttpCode() == 200 && http.getRawResponse().length() > 0) {
			String[] xa = http.getRawResponse().split("&");
			if(xa.length > 0) {
				for(int i=0; i<xa.length; i++) {
					String[] xb = xa[i].split("=");
					if(xb.length > 1)
						hM.put(xb[0], xb[1]);
					else
						hM.put(xb[0], "");
				}
				
			}
			if(hM.get("error_code").equals("000"))
				isApproved = true;
		}
		
		GatewayResponse gResp = new GatewayResponse(
			hM,
			http.getHttpCode(),
			http.getHttpText(),
			http.getRawResponse(),
			http.getDuration(),
			isApproved
		);
		return gResp;
	}
	
}
