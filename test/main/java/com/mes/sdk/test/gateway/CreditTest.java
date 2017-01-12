package com.mes.sdk.test.gateway;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mes.sdk.core.Settings;
import com.mes.sdk.exception.MesRuntimeException;
import com.mes.sdk.gateway.CcData;
import com.mes.sdk.gateway.Gateway;
import com.mes.sdk.gateway.GatewayRequest;
import com.mes.sdk.gateway.GatewayRequest.TransactionType;
import com.mes.sdk.gateway.GatewayResponse;
import com.mes.sdk.gateway.GatewaySettings;
import com.mes.sdk.test.MesTest;

class CreditTestCase extends MesTest {
	
	private Gateway gateway;
	private GatewaySettings settings;
	
	private final static Logger LOG = Logger.getLogger(CreditTestCase.class.getName());
	
	@Override
	public void run() {
		settings = new GatewaySettings().hostUrl("");
		settings.credentials("9410000xxxxx0000000x", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
			.hostUrl(GatewaySettings.URL_CERT)
			.method(Settings.Method.POST)
			.timeout(10000)
			.verbose(true)
			.validateRequests(true);
		
		gateway = new Gateway(settings);
		
		try {
			GatewayRequest cRequest = new GatewayRequest(TransactionType.CREDIT)
			.cardData(
				new CcData()
					.setCcNum("4012888812348882")
			)
			.amount("0.3")
			.setParameter("client_reference_number", "Java SDK Test");
			
			GatewayResponse cResponse = gateway.run(cRequest);
			LOG.log(Level.INFO, cResponse.toString());
		} catch (MesRuntimeException e) {
			e.printStackTrace();
		}
		
	}
	
}

public class CreditTest {
	public static void main(String[] args) {
		new CreditTestCase().run();
	}	
}