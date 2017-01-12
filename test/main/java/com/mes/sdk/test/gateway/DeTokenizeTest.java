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

class DeTokenizeTestCase extends MesTest {
	
	private Gateway gateway;
	private GatewaySettings settings;
	private final static Logger LOG = Logger.getLogger(DeTokenizeTestCase.class.getName());
	
	@Override
	public void run() {
		settings = new GatewaySettings();
		settings.credentials("9410000xxxxx0000000x", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
			.hostUrl(GatewaySettings.URL_CERT)
			.method(Settings.Method.POST)
			.timeout(10000)
			.verbose(true);
		
		gateway = new Gateway(settings);
		
		try {
			GatewayRequest tRequest = new GatewayRequest(TransactionType.TOKENIZE)
				.cardData(
					new CcData()
						.setCcNum("4012888812348882")
				)
				.setParameter("client_reference_number", "Java SDK Test");
			GatewayResponse tResponse = gateway.run(tRequest);
			System.out.println(tResponse);
			
			GatewayRequest dRequest = new GatewayRequest(TransactionType.DETOKENIZE)
				.cardData(
					new CcData()
						.setToken(tResponse.getResponseValue("transaction_id"))
				)
				.setParameter("client_reference_number", "Java SDK Test");
			GatewayResponse dResponse = gateway.run(dRequest);
			LOG.log(Level.INFO, dResponse.toString());
		} catch (MesRuntimeException e) {
			e.printStackTrace();
		}
		
	}
	
}

public class DeTokenizeTest {
	public static void main(String[] args) {
		new DeTokenizeTestCase().run();
	}	
}