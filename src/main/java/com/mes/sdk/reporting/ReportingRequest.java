package com.mes.sdk.reporting;

import com.mes.sdk.core.RequestObject;

/**
 * The ReportingRequest Object is used to store request fields, and is passed to the Reporting.run method.
 *
 * @author brice
 */
public class ReportingRequest extends RequestObject {

    public static enum ReportType {BATCH, SETTLEMENT, DEPOSIT, RECONSCILE, CHARGEBACKADJUSTMENTS, CHARGEBACKPRENOT, RETRIEVAL, INTERCHANGE, CUSTOM, FXBATCH, ITLCHARGEBACK, ITLRETRIEVAL, FXINTERCHANGE, INTLDETAILS, AUTHLOG, GATEWAYREQUESTLOG, ACHSETTLEMENT, ACHRETURN, TRIDENTBATCH}

    public static enum ReportMode {SUMMARY, DETAIL}

    public static enum ResponseFormat {CSV, XML1, XML2}

    private final ReportType reportType;
    private final ReportMode reportMode;

    public ReportingRequest(ReportType type, ReportMode mode) {
        super();
        this.reportType = type;
        this.reportMode = mode;
    }

    public ReportType getType() {
        return reportType;
    }

    public ReportMode getMode() {
        return reportMode;
    }

    /**
     * Sets the starting date of the report.
     *
     * @param mm
     * @param dd
     * @param yyyy
     * @return
     */
    public ReportingRequest setStartDate(String mm, String dd, String yyyy) {
        setParameter("reportDateBegin", mm + "%2F" + dd + "%2F" + yyyy);
        return this;
    }

    /**
     * Sets the ending date of the report.
     *
     * @param mm
     * @param dd
     * @param yyyy
     * @return
     */
    public ReportingRequest setEndDate(String mm, String dd, String yyyy) {
        setParameter("reportDateEnd", mm + "%2F" + dd + "%2F" + yyyy);
        return this;
    }

    /**
     * Sets the MID or association to use.
     *
     * @param nodeId
     * @return
     */
    public ReportingRequest setNode(String nodeId) {
        setParameter("nodeId", nodeId);
        return this;
    }

    /**
     * Requests the Trident Transaction ID to be in the response data.
     *
     * @param inc
     * @return
     */
    public ReportingRequest includeTranId(boolean inc) {
        setParameter("includeTridentTranId", inc);
        return this;
    }

    /**
     * Requests the Purchase ID (Invoice Number) to be in the response data.
     *
     * @param inc
     * @return
     */
    public ReportingRequest includePurchId(boolean inc) {
        setParameter("includePurchaseId", inc);
        return this;
    }

    /**
     * Requests the Client Reference Number to be in the response data.
     *
     * @param inc
     * @return
     */
    public ReportingRequest includeClientRefNum(boolean inc) {
        setParameter("includeClientRefNum", inc);
        return this;
    }

    /**
     * Sets the profile ID, only for use with the Payment Gateway Request Log report.
     *
     * @param profileId
     * @return
     */
    public ReportingRequest setProfileId(String profileId) {
        setParameter("profileId", profileId);
        return this;
    }

    /**
     * Sets the custom query ID, only for use with the Custom Query Report.
     *
     * @param profileId
     * @return
     */
    public ReportingRequest setQueryId(String profileId) {
        setParameter("profileId", profileId);
        return this;
    }

    /**
     * Sets the response data type.
     *
     * @param format
     * @return
     */
    public ReportingRequest setResponseFormat(ResponseFormat format) {
        switch (format) {
            case XML1:
                setParameter("xmlEncoding", "0");
                break;
            case XML2:
                setParameter("xmlEncoding", "1");
                break;
            case CSV:
            default:
                removeParameter("xmlEncoding");
                break;
        }
        return this;
    }

    @Override
    public void validateRequest() {

    }
}
