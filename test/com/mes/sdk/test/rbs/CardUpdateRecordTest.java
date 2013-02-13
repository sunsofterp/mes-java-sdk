package com.mes.sdk.test.rbs;

import com.mes.sdk.exception.MesRuntimeException;
import com.mes.sdk.rbs.Rbs;
import com.mes.sdk.rbs.RbsRequest;
import com.mes.sdk.rbs.RbsRequest.RequestType;
import com.mes.sdk.rbs.RbsResponse;
import com.mes.sdk.rbs.RbsSettings;
import com.mes.sdk.test.TestInterface;

class UpdateRecordTestCase implements TestInterface {
	
	private Rbs rbs;
	private RbsSettings settings;
	
	@Override
	public void run() {
		settings = new RbsSettings()
			.credentials("testuser", "testpass", "9410000xxxxx0000000x")
			.hostUrl(RbsSettings.URL_LIVE)
			.verbose(true);
		
		rbs = new Rbs(settings);
		
		try {
			RbsRequest cRequest = new RbsRequest(RequestType.UPDATE);
			cRequest.setCustomerId("customer123")
				.setCardData("4111111111111111", "1218")
				.setCardCustomerData("123 N. Main", "55555")
				.setNextDate("02", "13", "13")
				.setAmount("1.00")
				.setPaymentCount("0");
			
			RbsResponse cResponse = rbs.run(cRequest);
			System.out.println(cResponse);
			if(cResponse.requestSuccessful()) {
				// Store Results
			}
		} catch (MesRuntimeException e) {
			e.printStackTrace();
		}
	}
}

public class CardUpdateRecordTest {
	public static void main(String[] args) {
		new UpdateRecordTestCase().run();
	}	
}