package com.mes.sdk.gateway;

import com.mes.sdk.core.ApiInterface;
import com.mes.sdk.core.Http;
import com.mes.sdk.exception.MesRuntimeException;
import com.mes.sdk.gateway.Level3.LineItemData;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Gateway implements ApiInterface<GatewayRequest> {

    private final Http http;
    private final GatewaySettings settings;

    /**
     * The main object used to communicate with the Payment Gateway.
     *
     * @param settings An instance of {@link GatewaySettings}.
     */
    public Gateway(GatewaySettings settings) {
        http = new Http(settings);
        this.settings = settings;
    }

    public GatewayResponse run(GatewayRequest requestObject) {
        if (settings.validatesRequests())
            requestObject.validateRequest();
        http.setRequestString(parseRequest(requestObject));
        http.run();
        return parseResponse();
    }

    public String parseRequest(GatewayRequest req) {
        StringBuilder sb = new StringBuilder();

        sb.append("profile_id=").append(settings.getProfileId());
        sb.append("&profile_key=").append(settings.getProfileKey());

        String typeCode = null;
        switch (((GatewayRequest) req).getType()) {
            case SALE:
                typeCode = "D";
                break;
            case PREAUTH:
                typeCode = "P";
                break;
            case REAUTH:
                typeCode = "J";
                break;
            case OFFLINE:
                typeCode = "O";
                break;
            case SETTLE:
                typeCode = "S";
                break;
            case VOID:
                typeCode = "V";
                break;
            case CREDIT:
                typeCode = "C";
                break;
            case REFUND:
                typeCode = "U";
                break;
            case VERIFY:
                typeCode = "A";
                break;
            case TOKENIZE:
                typeCode = "T";
                break;
            case DETOKENIZE:
                typeCode = "X";
                break;
            case BATCHCLOSE:
                typeCode = "Z";
                break;
        }

        if (typeCode != null)
            sb.append("&transaction_type=").append(typeCode);

        for (Map.Entry<String, String> pair : req.requestTable.entrySet()) {
            try {
                sb.append("&").append(pair.getKey()).append("=").append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new MesRuntimeException("Unable to URL Encode the following value: " + pair.getValue());
            }
        }

        // Add each level 3 line items to the request.
        for (int i = 0; i < req.lineItems.size(); ++i) {
            LineItemData lineItem = req.lineItems.get(i);
            try {
                sb.append("&").append(lineItem.getFieldName()).append("=");
                sb.append(URLEncoder.encode(lineItem.toString(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new MesRuntimeException("Unable to URL Encode the following line item value: " + lineItem.toString());
            }
        }
        if (req.lineItems.size() > 0) {
            sb.append("&").append(LineItemData.lineItemCountFieldName).append("=").append(req.lineItems.size());
        }
        return sb.toString();
    }

    public GatewayResponse parseResponse() {
        // Create new GatewayResponse,
        boolean isApproved = false;

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
            if (hM.get("error_code").equals("000"))
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
