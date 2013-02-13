package com.mes.sdk.test.gateway;

import com.mes.sdk.core.Settings;
import com.mes.sdk.exception.MesRuntimeException;
import com.mes.sdk.gateway.CcData;
import com.mes.sdk.gateway.GatewayRequest;
import com.mes.sdk.gateway.GatewayRequest.TransactionType;
import com.mes.sdk.gateway.GatewayResponse;
import com.mes.sdk.gateway.GatewaySettings;
import com.mes.sdk.gateway.Gateway;
import com.mes.sdk.test.TestInterface;

class CreditTestCase implements TestInterface {
	
	private Gateway gateway;
	private GatewaySettings settings;
	
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
			System.out.println(cResponse);
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