package com.mes.sdk.rbs;

import com.mes.sdk.core.RequestObject;

/**
 * The RbsRequest Object is used to store request fields, and is passed to the Recurring.run method.
 *
 * @author BRice
 */
public class RbsRequest extends RequestObject {

    /**
     * The type of transaction being sent to the gateway.<br />
     * <ul>
     * <li>CREATE: Creates a new recurring record.</li>
     * <li>UPDATE: Updates an existing record.</li>
     * <li>INQUIRY: Inquires on the status of an existing record.</li>
     * <li>DELETE: Removes an existing record.</li>
     * </ul>
     */
    public static enum RequestType {
        CREATE, UPDATE, INQUIRY, DELETE
    }

    ;

    /**
     * The type of frequency of payments.<br />
     * <ul>
     * <li>MONTHLY: Every month, the same day as the start date.</li>
     * <li>QUARTERLY: Every three months on the same day as the start date.</li>
     * <li>ANNUALLY: Every year on the same day as the start date.</li>
     * </ul>
     */
    public static enum PaymentFrequency {
        MONTHLY, QUARTERLY, ANNUALLY
    }

    /**
     * The payment method to bill: Credit Card, or ACH.
     */
    public static enum PaymentType {
        CC, ACHP
    }

    public static enum AchAccountType {CHECKING, SAVINGS}

    public static enum AchAuthType {WEB, PPD, CCD}

    private final RequestType requestType;

    public RbsRequest(RequestType r) {
        super();
        this.requestType = r;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    /**
     * Sets the type of payment to bill for this record.
     *
     * @param type
     * @return
     */
    public RbsRequest setPaymentType(PaymentType type) {
        setParameter("payment_type", type.toString());
        return this;
    }

    /**
     * Sets the start date of first recurring transaction.<br />
     * Ex. 11 24 12 (For November 24th, 2012)
     *
     * @param mm
     * @param dd
     * @param yy
     * @return
     */
    public RbsRequest setStartDate(String mm, String dd, String yy) {
        setParameter("recur_start_date", mm + "/" + dd + "/" + yy);
        return this;
    }

    /**
     * Sets the date of next recurring transaction.<br />
     * Ex. 11 24 12 (For November 24th, 2012)
     *
     * @param mm
     * @param dd
     * @param yy
     * @return
     */
    public RbsRequest setNextDate(String mm, String dd, String yy) {
        setParameter("nextDate", mm + "/" + dd + "/" + yy);
        return this;
    }

    /**
     * Sets the unique customer ID which identifies this record.
     *
     * @param customerId
     * @return
     */
    public RbsRequest setCustomerId(String customerId) {
        setParameter("customer_id", customerId);
        return this;
    }

    /**
     * Sets the amount to bill every time the Frequency passes.
     *
     * @param amount
     * @return
     */
    public RbsRequest setAmount(String amount) {
        setParameter("recur_amount", amount);
        return this;
    }

    /**
     * Sets the amount of payments to make. A setting of 0 (zero) will bill indefinitely.
     *
     * @param count
     * @return
     */
    public RbsRequest setPaymentCount(String count) {
        setParameter("recur_num_pmt", count);
        return this;
    }

    /**
     * Sets how often to bill the {@link PaymentType}.
     *
     * @param freq
     * @return
     */
    public RbsRequest setFrequency(PaymentFrequency freq) {
        setParameter("recur_frequency", freq.toString());
        return this;
    }

    /**
     * Sets the credit card and expiry date used for billing.<br />
     * <b>Only applicable for CC Payment Type</b>
     *
     * @param cardNum
     * @param expDate
     * @return
     */
    public RbsRequest setCardData(String cardNum, String expDate) {
        setParameter("card_number", cardNum);
        setParameter("card_exp_date", expDate);
        return this;
    }

    /**
     * Sets a previously tokenized card number and expiry date used for billing<br />
     * <b>Only applicable for CC Payment Type</b>
     *
     * @param token
     * @param expDate
     * @return
     */
    public RbsRequest setTokenData(String token, String expDate) {
        setParameter("card_id", token);
        setParameter("card_exp_date", expDate);
        return this;
    }

    /**
     * Sets the relevant customer data used for billing.<br />
     * <b>Only applicable for CC Payment Type</b>
     *
     * @param billingAddress
     * @param billingZip
     * @return
     */
    public RbsRequest setCardCustomerData(String billingAddress, String billingZip) {
        setParameter("cardholder_street_address", billingAddress);
        setParameter("cardholder_zip", billingZip);
        return this;
    }

    /**
     * Sets the ACH data used for billing.<br />
     * <b>Only applicable for ACH Payment Type</b>
     *
     * @param accountNum  Checking or Savings account number
     * @param transitNum  Transit Routing Number
     * @param accountType Type of bank account
     * @param authType
     * @return
     */
    public RbsRequest setAchData(String accountNum, String transitNum, AchAccountType accountType, AchAuthType authType) {
        setParameter("account_num", accountNum);
        setParameter("transit_num", transitNum);
        switch (accountType) {
            case CHECKING:
                setParameter("account_type", "C");
                break;
            case SAVINGS:
                setParameter("account_type", "S");
                break;
        }

        switch (authType) {
            case WEB:
                setParameter("auth_type", "WEB");
                break;
            case CCD:
                setParameter("auth_type", "CCD");
                break;
            case PPD:
                setParameter("auth_type", "PPD");
                break;
        }
        return this;
    }

    /**
     * Sets the relevant customer data used for billing.<br />
     * <b>Only applicable for ACH Payment Type</b>
     *
     * @param customerName
     * @param customerId
     * @param ipAddress
     * @return
     */
    public RbsRequest setAchCustomerData(String customerName, String customerId, String ipAddress) {
        setParameter("cust_name", customerName);
        setParameter("cust_id", customerId);
        setParameter("ip_address", ipAddress);
        return this;
    }

    @Override
    public void validateRequest() {
    }

}