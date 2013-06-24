package com.mes.sdk.test.gateway;

import com.mes.sdk.core.Settings;
import com.mes.sdk.exception.MesRuntimeException;
import com.mes.sdk.gateway.CcData;
import com.mes.sdk.gateway.Gateway;
import com.mes.sdk.gateway.GatewayRequest;
import com.mes.sdk.gateway.GatewayRequest.TransactionType;
import com.mes.sdk.gateway.GatewayResponse;
import com.mes.sdk.gateway.GatewaySettings;
import com.mes.sdk.gateway.Level3.LineItemData.DebitCreditInd;
import com.mes.sdk.gateway.Level3.VisaLineItem;
import com.mes.sdk.test.TestInterface;

class SaleLevel3VITestCase implements TestInterface {
	
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
						.setExpDate("12/16")
						.setCvv("123")
				)
				.amount("2300.00")
        .setParameter("invoice_number", "123456")
        .setParameter("client_reference_number", "Java SDK Test")
        
        // Add two line items
				.addLineItem(
				  new VisaLineItem()
				    .itemCommodityCode("5096")
				    .itemDescriptor("Carbon Dioxide")
				    .productCode("equipment")
				    .quantity(1)
				    .unitOfMeasure("EA")
				    .unitCost("1200.00")
				    .vatTaxAmount("0.00")
				    .vatTaxRate("0.0")
				    .discountPerLine("0.00")
				    .lineItemTotal("1200.00")
				    .debitOrCreditIndicator(DebitCreditInd.D)
				)
        .addLineItem(
          new VisaLineItem()
            .itemCommodityCode("5097")
            .itemDescriptor("Carbon Monoxide")
            .productCode("equipment")
            .quantity(1)
            .unitOfMeasure("EA")
            .unitCost("1100.00")
            .vatTaxAmount("0.00")
            .vatTaxRate("0.0")
            .discountPerLine("0.00")
            .lineItemTotal("1100.00")
            .debitOrCreditIndicator(DebitCreditInd.D)
        )
        
        // Add other Visa Level 3 fields
        .shipFromZip("55555")
        .shipToZip("55555")
        .merchantTaxId("123")
        .customerTaxId("321")
        .summaryCommodityCode("1234")
        .discountAmount("0.00")
        .shippingAmount("0.00")
        .dutyAmount("0.00")
        .destCountryCode("840")
        .vatInvoice("123456")
        .orderDate("130624")
        .vatAmount("0.00");
				
			GatewayResponse sResponse = gateway.run(sRequest);
			System.out.println(sResponse);
		} catch (MesRuntimeException e) {
			e.printStackTrace();
		}
		
	}
	
}

public class SaleLevel3VisaTest {
	public static void main(String[] args) {
		new SaleLevel3VITestCase().run();
	}	
}