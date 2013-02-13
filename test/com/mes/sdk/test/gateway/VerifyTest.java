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

class VerifyTestCase implements TestInterface {
	
	private Gateway gateway;
	private GatewaySettings settings;
	
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
			GatewayRequest sRequest = new GatewayRequest(TransactionType.SALE)
				.cardData(
					new CcData()
						.setCcNum("4012888812348882")
						.setExpDate("12/12")
						.setCvv("123")
				)
				.amount("0.3")
				.setParameter("invoice_number", "123456")
				.setParameter("client_reference_number", "Java SDK Test");
			GatewayResponse sResponse = gateway.run(sRequest);
			System.out.println(sResponse);
		} catch (MesRuntimeException e) {
			e.printStackTrace();
		}
		
	}
	
}

public class VerifyTest {
	public static void main(String[] args) {
		new VerifyTestCase().run();
	}	
}