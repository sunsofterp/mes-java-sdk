package com.mes.sdk.gateway;

/**
 * An object which holds credit card data. <br />
 * If CcNum data and token are set, the token is preferred, and card number is ignored.
 *
 * @author brice
 */
public final class CcData {
    private String ccNum;
    private String expDate;
    private String cvv;
    private String token;

    /**
     * Sets the credit card number.
     *
     * @param ccNum
     * @return this CcData Object for chaining.
     */
    public CcData setCcNum(String ccNum) {
        this.ccNum = ccNum;
        return this;
    }

    /**
     * Sets the card expiry date.
     *
     * @param expDate
     * @return this CcData Object for chaining.
     */
    public CcData setExpDate(String expDate) {
        this.expDate = expDate;
        return this;
    }

    /**
     * Sets the card verification code.
     *
     * @param cvv
     * @return this CcData Object for chaining.
     */
    public CcData setCvv(String cvv) {
        this.cvv = cvv;
        return this;
    }

    /**
     * Sets the tokenized card number.<br />
     * If the card number and token are both set, the token is preferred.
     *
     * @param token
     * @return this CcData Object for chaining.
     */
    public CcData setToken(String token) {
        this.token = token;
        return this;
    }

    public String getCcNum() {
        return ccNum;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getCvv() {
        return cvv;
    }

    public String getToken() {
        return token;
    }

}