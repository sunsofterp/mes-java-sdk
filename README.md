Introduction
============

Merchant e-Solutions APIs implemented in a Java SDK.

###Requirements
This library requires Java 1.5+

###Basic Usage
```java
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
	LOG.log(Level.INFO, sResponse.toString());
} catch (MesRuntimeException e) {
	e.printStackTrace();
}
```