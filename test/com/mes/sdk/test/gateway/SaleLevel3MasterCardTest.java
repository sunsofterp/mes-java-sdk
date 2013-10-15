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
import com.mes.sdk.gateway.Level3.LineItemData.DebitCreditInd;
import com.mes.sdk.gateway.Level3.MasterCardLineItem;
import com.mes.sdk.gateway.Level3.MasterCardLineItem.DiscountInd;
import com.mes.sdk.gateway.Level3.MasterCardLineItem.NetGrossInd;
import com.mes.sdk.test.MesTest;

class SaleLevel3MCTestCase extends MesTest {
	
	private Gateway gateway;
	private GatewaySettings settings;
	private final static Logger LOG = Logger.getLogger(SaleLevel3MCTestCase.class.getName());
	
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
						.setCcNum("5133272000000004")
						.setExpDate("12/16")
						.setCvv("123")
				)
        .amount("896.92")
        .setParameter("invoice_number", "123456")
        .setParameter("client_reference_number", "Java SDK Test")
				
				// Add two line items
				.addLineItem(
				  new MasterCardLineItem()
				    .itemDescription("Carbon Dioxide")
				    .productCode("equipment")
				    .quantity(1)
				    .unitOfMeasure("EA")
				    .altTaxIdentifier("000000000000000")
				    .taxRateApplied("5.0")
				    .taxTypeApplied("STAT")
				    .taxAmount("17.32")
				    .discountIndicator(DiscountInd.N)
				    .netGrossIndicator(NetGrossInd.N)
				    .extendedItemAmount("346.46")
				    .debitOrCreditIndicator(DebitCreditInd.D)
				    .discountAmount("0.00")
				)
        .addLineItem(
          new MasterCardLineItem()
            .itemDescription("Carbon Monoxide")
            .productCode("equipment")
            .quantity(1)
            .unitOfMeasure("EA")
            .altTaxIdentifier("000000000000000")
            .taxRateApplied("5.0")
            .taxTypeApplied("STAT")
            .taxAmount("27.52")
            .discountIndicator(DiscountInd.N)
            .netGrossIndicator(NetGrossInd.N)
            .extendedItemAmount("550.46")
            .debitOrCreditIndicator(DebitCreditInd.D)
            .discountAmount("0.00")
        )
        
        // Add other MasterCard Level 3 fields
        .shipFromZip("55555")
        .shipToZip("55555")
        .summaryCommodityCode("1234")
        .shippingAmount("28.00")
        .dutyAmount("0.00")
        .destCountryCode("840")
        .orderDate("130624")
        .altTaxAmount("0.00")
        .altTaxAmountIndicator("N");

			GatewayResponse sResponse = gateway.run(sRequest);
			LOG.log(Level.INFO, sResponse.toString());
		} catch (MesRuntimeException e) {
			e.printStackTrace();
		}
		
	}
	
}

public class SaleLevel3MasterCardTest {
	public static void main(String[] args) {
		new SaleLevel3MCTestCase().run();
	}	
}