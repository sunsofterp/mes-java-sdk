package com.mes.sdk.test.rbs;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mes.sdk.exception.MesRuntimeException;
import com.mes.sdk.rbs.Rbs;
import com.mes.sdk.rbs.RbsRequest;
import com.mes.sdk.rbs.RbsRequest.AchAccountType;
import com.mes.sdk.rbs.RbsRequest.AchAuthType;
import com.mes.sdk.rbs.RbsRequest.PaymentFrequency;
import com.mes.sdk.rbs.RbsRequest.PaymentType;
import com.mes.sdk.rbs.RbsRequest.RequestType;
import com.mes.sdk.rbs.RbsResponse;
import com.mes.sdk.rbs.RbsSettings;
import com.mes.sdk.test.MesTest;

class AchCreateRecordTestCase extends MesTest {
	
	private Rbs rbs;
	private RbsSettings settings;
	private final static Logger LOG = Logger.getLogger(AchCreateRecordTestCase.class.getName());
	
	@Override
	public void run() {
		settings = new RbsSettings()
			.credentials("testuser", "testpass", "9410000xxxxx0000000x")
			.hostUrl(RbsSettings.URL_LIVE)
			.verbose(true);
		
		rbs = new Rbs(settings);
		
		try {
			RbsRequest cRequest = new RbsRequest(RequestType.CREATE);
			cRequest.setCustomerId("customer123")
				.setPaymentType(PaymentType.ACHP)
				.setFrequency(PaymentFrequency.MONTHLY)
				.setAchData("123456", "123456789", AchAccountType.CHECKING, AchAuthType.WEB)
				.setAchCustomerData("Test Customer", "123456", "10.1.1.1")
				.setStartDate("02", "13", "13")
				.setAmount("1.00")
				.setPaymentCount("0");
			
			RbsResponse cResponse = rbs.run(cRequest);
			LOG.log(Level.INFO, cResponse.toString());
			if(cResponse.requestSuccessful()) {
				// Store Results
			}
		} catch (MesRuntimeException e) {
			e.printStackTrace();
		}
	}
}

public class AchCreateRecordTest {
	public static void main(String[] args) {
		new AchCreateRecordTestCase().run();
	}	
}