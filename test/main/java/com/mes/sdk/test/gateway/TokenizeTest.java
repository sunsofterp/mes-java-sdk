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

class TokenizeTestCase extends MesTest {
	
	private Gateway gateway;
	private GatewaySettings settings;
	private final static Logger LOG = Logger.getLogger(TokenizeTestCase.class.getName());
	
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
			LOG.log(Level.INFO, tResponse.toString());
			
			GatewayRequest sRequest = new GatewayRequest(TransactionType.PREAUTH)
				.cardData(
					new CcData()
						.setToken(tResponse.getTransactionId())
						.setExpDate("1216")
						.setCvv("123")
				)
				.amount("0.3")
				.setParameter("invoice_number", "123456")
				.setParameter("client_reference_number", "Java SDK Test");
			GatewayResponse sResponse = gateway.run(sRequest);
			LOG.log(Level.INFO, sResponse.toString());
		} catch (MesRuntimeException e) {
			e.printStackTrace();
		}
		
	}
	
}

public class TokenizeTest {
	public static void main(String[] args) {
		new TokenizeTestCase().run();
	}	
}