package com.mes.sdk.reporting;

import com.mes.sdk.core.Settings;

/**
 * A object that holds generic configuration for the Reporting API.<br />
 * The {@link Reporting} class requires this object in it's constructor.
 *
 * @author brice
 */
public class ReportingSettings extends Settings<ReportingSettings> {

    /**
     * Uses the test web reporting system.
     */
    public static final String URL_TEST = "https://test.merchante-solutions.com/jsp/reports/report_api.jsp";

    /**
     * Uses the live web reporting system.
     */
    public static final String URL_LIVE = "https://www.merchante-solutions.com/jsp/reports/report_api.jsp";

    private String userName;
    private String userPass;

    /**
     * Creates default gateway settings of method type POST, verbose false, a timeout of 20s, and validation of request objects true.<br />
     * The default URL is set to live.
     */
    public ReportingSettings() {
        super();
        hostUrl(URL_LIVE);
    }

    /**
     * Sets the profile ID and profile Key which are used to authenticate with the Payment Gateway
     *
     * @param id
     * @param key
     */
    public final ReportingSettings credentials(String id, String key) {
        this.userName = id;
        this.userPass = key;
        return this;
    }

    /**
     * Returns the user name.
     *
     * @return
     */
    public final String getUserName() {
        return userName;
    }

    /**
     * Returns the user pass
     *
     * @return
     */
    public final String getUserPass() {
        return userPass;
    }

}
