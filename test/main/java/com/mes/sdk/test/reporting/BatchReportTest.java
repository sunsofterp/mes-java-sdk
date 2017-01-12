package com.mes.sdk.test.reporting;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mes.sdk.exception.MesRuntimeException;
import com.mes.sdk.reporting.Reporting;
import com.mes.sdk.reporting.ReportingRequest;
import com.mes.sdk.reporting.ReportingRequest.ReportMode;
import com.mes.sdk.reporting.ReportingRequest.ReportType;
import com.mes.sdk.reporting.ReportingRequest.ResponseFormat;
import com.mes.sdk.reporting.ReportingResponse;
import com.mes.sdk.reporting.ReportingSettings;
import com.mes.sdk.test.MesTest;

class BatchReportTestCase extends MesTest {
	
	private final static Logger LOG = Logger.getLogger(BatchReportTestCase.class.getName());
	
	@Override
	public void run() {
		
        ReportingSettings settings = new ReportingSettings();
        settings.credentials("user", "pass")
            .verbose(true)
            .hostUrl(ReportingSettings.URL_LIVE);
        Reporting reporting = new Reporting(settings);

        ReportingRequest request = new ReportingRequest(ReportType.BATCH, ReportMode.DETAIL);
        request.setNode("9410000xxxxx")
            .setStartDate("11", "01", "2012")
            .setEndDate("11", "01", "2012")
            .setResponseFormat(ResponseFormat.XML1);

		try {
			ReportingResponse response = reporting.run(request);
			LOG.log(Level.INFO, response.toString());
			if(reporting.wasSuccessful()) {
				// Store or parse response.getRawResponse()
			}
		} catch (MesRuntimeException e) {
			e.printStackTrace();
		}
	}
}

public class BatchReportTest {
	public static void main(String[] args) {
		new BatchReportTestCase().run();
	}	
}