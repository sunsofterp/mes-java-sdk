package com.mes.sdk.test.gateway;

import com.mes.sdk.core.Settings;
import com.mes.sdk.exception.MesRuntimeException;
import com.mes.sdk.gateway.CcData;
import com.mes.sdk.gateway.Gateway;
import com.mes.sdk.gateway.GatewayRequest;
import com.mes.sdk.gateway.GatewayRequest.TransactionType;
import com.mes.sdk.gateway.GatewayResponse;
import com.mes.sdk.gateway.GatewaySettings;
import com.mes.sdk.gateway.Level3.AmexLineItem;
import com.mes.sdk.gateway.Level3.LineItemData.DebitCreditInd;
import com.mes.sdk.gateway.Level3.VisaLineItem;
import com.mes.sdk.test.TestInterface;

class SaleLevel3AXTestCase implements TestInterface {

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
            .setCcNum("349999999999991")
            .setExpDate("12/16")
            .setCvv("1234")
          )
      .amount("2300.00")
      .setParameter("invoice_number", "123456")
      .setParameter("client_reference_number", "Java SDK Test")

      // Add two line items
      .addLineItem(
          new AmexLineItem()
            .itemDescription("Carbon Dioxide")
            .quantity(1)
            .unitCost("0.00")
          )
      .addLineItem(
          new AmexLineItem()
            .itemDescription("Carbonite Chamber")
            .quantity(1)
            .unitCost("0.00")
          )

      // Add other Visa Level 3 fields
      .requesterName("D. Vader")
      .cardholderReferenceNumber("123456")
      .shipToZip("55555")
      .vatAmount("0.00");

      GatewayResponse sResponse = gateway.run(sRequest);
      System.out.println(sResponse);
    } catch (MesRuntimeException e) {
      e.printStackTrace();
    }
  }
}

public class SaleLevel3AmexTest {
  public static void main(String[] args) {
    new SaleLevel3AXTestCase().run();
  }
}