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

class RefundTestCase extends MesTest {
	
	private Gateway gateway;
	private GatewaySettings settings;
	private final static Logger LOG = Logger.getLogger(RefundTestCase.class.getName());
	
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
				.amount("0.03")
				.setParameter("invoice_number", "123456")
				.setParameter("client_reference_number", "Java SDK Test");
			
			GatewayResponse sResponse = gateway.run(sRequest);
			LOG.log(Level.INFO, sResponse.toString());
			
			if(sResponse.isApproved()) {
				GatewayRequest rRequest = new GatewayRequest(TransactionType.REFUND);
				rRequest.setParameter("transaction_id", sResponse.getResponseValue("transaction_id"))
					.amount("0.03");
				GatewayResponse rResponse = gateway.run(rRequest);
				LOG.log(Level.INFO, rResponse.toString());
			}
			
		} catch (MesRuntimeException e) {
			e.printStackTrace();
		}
		
	}
	
}

public class RefundTest {
	public static void main(String[] args) {
		new RefundTestCase().run();
	}	
}