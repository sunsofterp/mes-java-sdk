package com.mes.sdk.gateway;

import com.mes.sdk.core.RequestObject;
import com.mes.sdk.core.Util;
import com.mes.sdk.exception.InvalidFieldException;
import com.mes.sdk.exception.MesRuntimeException;
import com.mes.sdk.exception.ValidationException;
import com.mes.sdk.gateway.Level3.LineItemData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The GatewayRequest Object is used to store request fields, and is passed to the Gateway.run method.
 *
 * @author brice
 */
public class GatewayRequest extends RequestObject {

    /**
     * The type of transaction being sent to the gateway.<br />
     * <ul>
     * <li>Sale: An authorization followed by an automatic capture.</li>
     * <li>Preauth: An authorization only.</li>
     * <li>Settle: Captures a preauthorization for funding.</li>
     * <li>Reauth: A clone transaction (only valid within 28 days from the original authorization date).</li>
     * <li>Offline: A forced-entry transaction using an approval code.</li>
     * <li>Void: Cancels a transaction authorized same-day and attempts to reverse it to the issuer.</li>
     * <li>Credit: An unmatched credit, requiring a full card number. This function is disabled at MeS by default.</li>
     * <li>Refund: Using an authorization's transaction id, a void is performed (if same-day) or credit is given (if the transaction was settled).</li>
     * <li>Verify: Validates the card account is open. Not supported by all issuers.</li>
     * <li>Tokenize: The transaction id returned by this request may be sent in the card_id field, replacing any card number field.</li>
     * <li>Detokenize: Removes the referenced token from the database.</li>
     * <li>Batchclose: Attempts to settle the current batch. Must a supply batch_number.</li>
     * </ul>
     *
     * @author brice
     */
    public enum TransactionType {
        SALE, PREAUTH, SETTLE, REAUTH, OFFLINE, VOID, CREDIT, REFUND, VERIFY, TOKENIZE, DETOKENIZE, BATCHCLOSE
    }

    private static final String[] requestFields = {
            "profile_id", "profile_key", "card_number", "card_exp_date", "card_swipe", "card_id", "card_number_encrypted", "card_swipe_encrypted", "card_swipe_ksn",
            "transaction_amount", "transaction_id", "transaction_type", "moto_ecommerce_ind", "reference_number", "client_reference_number",
            "auth_code", "batch_number", "retry_id", "retry_request", "cvv2", "invoice_number", "cardholder_street_address", "cardholder_zip",
            "tax_amount", "ship_to_zip", "merchant_name", "dm_contact_info", "xid", "cavv", "ucaf_collection_ind", "ucaf_auth_data",
            "currency_code", "recurring_pmt_num", "recurring_pmt_count", "debit_pin_block", "debit_ksn", "contactless",
            "cardholder_first_name", "cardholder_last_name", "cardholder_email", "cardholder_phone",
            "ship_to_first_name", "ship_to_last_name", "ship_to_phone", "ship_to_address", "ship_from_zip", "dest_country_code",
            "ip_address", "cust_host_name", "customer_browser_type", "customer_ani", "customer_ani_ii", "prod_sku", "shipping_method",
            "country_code", "subscription", "digital_goods", "account_name", "account_email", "account_creation_date", "account_last_change",
            "line_item_count", "merchant_tax_id", "customer_tax_id", "summary_commodity_code", "discount_amount", "shipping_amount", "duty_amount",
            "vat_invoice_number", "order_date", "vat_amount", "alt_tax_amount", "alt_tax_amount_indicator", "visa_line_item", "mc_line_item", "amex_line_item",
            "requester_name", "cardholder_reference_number", "resp_encoding", "store_card",
            "rctl_extended_avs", "rctl_account_balance", "rctl_partial_auth", "rctl_card_number_truncated", "rctl_resp_hash", // Response control fields
            "industry_code", "statement_date_begin", "statement_date_end", "name_of_place", "rate_daily", // Travel / Lodging
    };

    protected ArrayList<LineItemData> lineItems;

    private final TransactionType type;

    public GatewayRequest(TransactionType t) {
        super();
        lineItems = new ArrayList<LineItemData>();
        this.type = t;
    }

    /**
     * Adds a level 3 line item to the request.
     *
     * @param lineItem
     * @return
     */
    public GatewayRequest addLineItem(LineItemData lineItem) {
        lineItems.add(lineItem);
        return this;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public GatewayRequest setParameter(String fieldName, String value) throws InvalidFieldException {
        for (int i = 0; i < requestFields.length; i++) {
            if (fieldName.equals(requestFields[i])) {
                super.setParameter(fieldName, value);
                return this;
            }
        }
        throw new InvalidFieldException("The field \"" + fieldName + "\" is not supported.");
    }

    /**
     * Sets the {@link CcData} object for this transaction.
     *
     * @param ccData The object containing the card data.
     */
    public final GatewayRequest cardData(CcData ccData) {
        if (ccData.getToken() != null)
            super.setParameter("card_id", ccData.getToken());
        else {
            // Attempt to sanitize
            String ccNumber = ccData.getCcNum().replaceAll("[^A-Za-z0-9]", "");
            super.setParameter("card_number", ccNumber);
        }
        if (ccData.getExpDate() != null)
            super.setParameter("card_exp_date", ccData.getExpDate());
        if (ccData.getCvv() != null)
            super.setParameter("cvv2", ccData.getCvv());
        return this;
    }

    /**
     * Sets the card swipe value.
     *
     * @param cardSwipe Raw track data from a swiped card.
     */
    public GatewayRequest cardSwipe(String cardSwipe) {
        super.setParameter("card_swipe", cardSwipe);
        return this;
    }

    /**
     * Sets the request amount.
     *
     * @param amount The requested amount.
     */
    public GatewayRequest amount(String amount) {
        super.setParameter("transaction_amount", amount);
        return this;
    }

    /**
     * Sets the Device ID (Digital Fingerprint).
     *
     * @param deviceId
     * @return
     */
    public GatewayRequest deviceId(String deviceId) {
        setParameter("device_id", deviceId);
        return this;
    }

    /**
     * Sets the ISO 4217 Currency Code. Can be either numeric (ex. 840), or Alpha (ex. USD).
     *
     * @param isoCode
     * @return
     */
    public GatewayRequest currency(String isoCode) {
        setParameter("currency_code", isoCode);
        return this;
    }

    public GatewayRequest cardholderName(String firstName, String lastName) {
        setParameter("cardholder_first_name", firstName);
        setParameter("cardholder_last_name", lastName);
        return this;
    }

    public GatewayRequest cardholderAddress(String address) {
        setParameter("cardholder_street_address", address);
        return this;
    }

    public GatewayRequest cardholderAddress(String address, String zip) {
        setParameter("cardholder_street_address", address);
        setParameter("cardholder_zip", zip);
        return this;
    }

    public GatewayRequest cardholderEmail(String email) {
        setParameter("cardholder_email", email);
        return this;
    }

    public GatewayRequest cardholderPhone(String phone) {
        setParameter("cardholder_phone", phone);
        return this;
    }

    public GatewayRequest cardholderIPAddress(String ipAddress) {
        setParameter("ip_address", ipAddress);
        return this;
    }

    /**
     * Cardholder ISO 3166 Country Code (Alpha-2)
     *
     * @param code
     * @return
     */
    public GatewayRequest cardholderCountry(String code) {
        setParameter("country_code", code);
        return this;
    }

    public GatewayRequest shippingName(String firstName, String lastName) {
        setParameter("ship_to_first_name", firstName);
        setParameter("ship_to_last_name", lastName);
        return this;
    }

    public GatewayRequest shippingAddress(String address, String zip) {
        setParameter("ship_to_address", address);
        setParameter("ship_to_zip", zip);
        return this;
    }

    /**
     * Shipping destination ISO 3166 Country Code (Alpha-2)
     *
     * @param code
     * @return
     */
    public GatewayRequest shippingCountry(String code) {
        setParameter("dest_country_code", code);
        return this;
    }

    /**
     * A flag to designate the transaction as being part of a subscription.<br />
     * Solely used for fraud analysis.
     *
     * @param subscription
     * @return
     */
    public GatewayRequest isSubscription(boolean subscription) {
        setParameter("subscription", subscription);
        return this;
    }

    /**
     * A flag to designate the transaction as being a digital download or product.<br />
     * Solely used for fraud analysis.
     *
     * @param digital
     * @return
     */
    public GatewayRequest isDigitalGoods(boolean digital) {
        setParameter("digital_goods", digital);
        return this;
    }

    /**
     * Sets the request amount.
     *
     * @param amount The requested amount.
     */
    public GatewayRequest amount(BigDecimal amount) {
        setParameter("transaction_amount", amount.toString());
        return this;
    }

    /**
     * Data specific to a user account. Used for fraud analysis.
     *
     * @param accountName
     * @param accountEmail
     * @param accountCreationDate
     * @param accountModificationDate
     * @return
     */
    public GatewayRequest accountData(String accountName, String accountEmail, String accountCreationDate, String accountModificationDate) {
        setParameter("account_name", accountName);
        setParameter("account_email", accountEmail);
        setParameter("account_creation_date", accountCreationDate);
        setParameter("account_last_change", accountModificationDate);
        return this;
    }

    /**
     * Sets the tax amount for this transaction. The tax amount does not add to the transaction amount, and is for reporting purposes only.<br />
     * <b>To achieve best interchange on Level 2, and Level 3 transactions, the amount must be between 0.1% - 21.0% of the transaction amount (Visa/MC requirement).</b>
     *
     * @param tax
     * @return
     */
    public GatewayRequest taxAmount(String tax) {
        setParameter("tax_amount", tax);
        return this;
    }

    /**
     * Sets the merchant's VAT Tax ID<br />
     * <br />
     * <b>Level 3 Usage:</b> Visa
     *
     * @param id
     * @return
     */
    public GatewayRequest merchantTaxId(String id) {
        setParameter("merchant_tax_id", id);
        return this;
    }

    /**
     * Sets the customer's VAT Tax ID<br />
     * <br />
     * <b>Level 3 Usage:</b> Visa
     *
     * @param id
     * @return
     */
    public GatewayRequest customerTaxId(String id) {
        setParameter("customer_tax_id", id);
        return this;
    }

    /**
     * Sets the Summary Commodity Code<br />
     * <br />
     * <b>Level 3 Usage:</b> Visa
     *
     * @param code
     * @return
     */
    public GatewayRequest summaryCommodityCode(String code) {
        setParameter("summary_commodity_code", code);
        return this;
    }

    /**
     * Sets the Discount amount<br />
     * <br />
     * <b>Level 3 Usage:</b> Visa
     *
     * @param amount
     * @return
     */
    public GatewayRequest discountAmount(String amount) {
        setParameter("discount_amount", amount);
        return this;
    }

    /**
     * Sets the Shipping amount<br />
     * <br />
     * <b>Level 3 Usage:</b> Visa / MasterCard
     *
     * @param amount
     * @return
     */
    public GatewayRequest shippingAmount(String amount) {
        setParameter("shipping_amount", amount);
        return this;
    }

    /**
     * Sets the Duty amount<br />
     * <br />
     * <b>Level 3 Usage:</b> Visa / MasterCard
     *
     * @param amount
     * @return
     */
    public GatewayRequest dutyAmount(String amount) {
        setParameter("duty_amount", amount);
        return this;
    }

    /**
     * Sets the VAT amount<br />
     * <br />
     * <b>Level 3 Usage:</b> Visa / AMEX
     *
     * @param amount
     * @return
     */
    public GatewayRequest vatAmount(String amount) {
        setParameter("vat_amount", amount);
        return this;
    }

    /**
     * Sets the ISO country code of the shipping destination.<br />
     * <br />
     * <b>Level 3 Usage:</b> Visa / MasterCard
     *
     * @param code
     * @return
     */
    public GatewayRequest destCountryCode(String code) {
        setParameter("dest_country_code", code);
        return this;
    }

    /**
     * Sets the VAT Invoice Number.<br />
     * <br />
     * <b>Level 3 Usage:</b> Visa
     *
     * @param invoice
     * @return
     */
    public GatewayRequest vatInvoice(String invoice) {
        setParameter("vat_invoice_number", invoice);
        return this;
    }

    /**
     * Sets the ZIP (5, or 9) code for the shipping destination.<br />
     * <br />
     * <b>Level 3 Usage:</b> Visa / MasterCard / AMEX
     *
     * @param zip
     * @return
     */
    public GatewayRequest shipToZip(String zip) {
        setParameter("ship_to_zip", zip);
        return this;
    }

    /**
     * Sets the ZIP (5, or 9) code for the shipping origin.<br />
     * <br />
     * <b>Level 3 Usage:</b> Visa / MasterCard
     *
     * @param zip
     * @return
     */
    public GatewayRequest shipFromZip(String zip) {
        setParameter("ship_from_zip", zip);
        return this;
    }

    /**
     * Sets the Order Date.<br />
     * Format: YYMMDD<br />
     * <br />
     * <b>Level 3 Usage:</b> Visa
     *
     * @param date
     * @return
     */
    public GatewayRequest orderDate(String date) {
        setParameter("order_date", date);
        return this;
    }

    /**
     * Sets the transaction ID for a refund.
     *
     * @param transactionId ID for the transaction to refund.
     */
    public GatewayRequest transactionId(String transactionId) {
        super.setParameter("transaction_id", transactionId);
        return this;
    }

  /*
   * MasterCard Level 3
   */

    /**
     * Sets the alternate tax amount<br />
     * <br />
     * <b>Level 3 Usage:</b> MasterCard
     *
     * @param amount
     * @return
     */
    public GatewayRequest altTaxAmount(String amount) {
        setParameter("alt_tax_amount", amount);
        return this;
    }

    /**
     * Sets the alternate tax amount indicator<br />
     * <br />
     * <b>Level 3 Usage:</b> MasterCard
     *
     * @param indicator
     * @return
     */
    public GatewayRequest altTaxAmountIndicator(String indicator) {
        setParameter("alt_tax_amount_indicator", indicator);
        return this;
    }

    /**
     * Sets the Amex cardholder reference number, used by the cardholder to identify each transaction.<br />
     * <br />
     * <b>Level 3 Usage:</b> Amex
     *
     * @param ref
     * @return
     */
    public GatewayRequest cardholderReferenceNumber(String ref) {
        setParameter("cardholder_reference_number", ref);
        return this;
    }

    /**
     * Sets the name of the Amex cardholder purchasing the product or service.<br />
     * <br />
     * <b>Level 3 Usage:</b> Amex
     *
     * @param name
     * @return
     */
    public GatewayRequest requesterName(String name) {
        setParameter("requester_name", name);
        return this;
    }

    @Override
    public void validateRequest() {
        switch (type) {
            case SALE:
                validateCardData();
                break;
            case PREAUTH:
                validateCardData();
                break;
            case REAUTH:
                validateRequiredField("transaction_id");
                break;
            case OFFLINE:
                validateRequiredField("auth_code");
                break;
            case SETTLE:
                validateRequiredField("transaction_id");
                validateRequiredField("transaction_amount");
                break;
            case VOID:
                validateRequiredField("transaction_id");
                break;
            case CREDIT:
                validateRequiredField("transaction_amount");
                validateCardData();
                break;
            case REFUND:
                validateRequiredField("transaction_id");
                break;
            case VERIFY:
                validateCardData();
                break;
            case TOKENIZE:
                validateRequiredField("card_number");
                break;
            case DETOKENIZE:
                validateRequiredField("card_id");
                break;
            case BATCHCLOSE:
                break;
        }

        if (requestTable.containsKey("invoice_num")) {
            if (requestTable.get("invoice_num").length() > 17)
                throw new ValidationException("\"invoice_num\" must be <= 17 characters.");
        }
    }

    private void validateRequiredField(String field) {
        if (!requestTable.containsKey(field))
            throw new ValidationException(type + " requires \"" + field + "\"");
    }

    private void validateCardData() {
        if (!requestTable.containsKey("transaction_amount"))
            throw new ValidationException(type + " requires a \"transaction_amount\".");

        if (requestTable.containsKey("card_number")) {
            // attempt to sanitize ccnum

            if (!Util.checkCC(requestTable.get("card_number")))
                throw new MesRuntimeException("Card number is invalid");
            if (!requestTable.containsKey("card_exp_date"))
                throw new ValidationException(type + " using \"card_number\" requires \"card_exp_date\".");
        } else if (requestTable.containsKey("card_id")) {
            if (!requestTable.containsKey("card_exp_date"))
                throw new ValidationException(type + " using \"card_id\" requires \"card_exp_date\".");
        } else if (requestTable.containsKey("card_swipe")) {
            //... validate sentinels?
        } else
            throw new ValidationException("Card data missing.");
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Map.Entry<String, String> entry : this.requestTable.entrySet()) {
            if (!toStringExclusions().contains(entry.getKey())) {
                output.append(String.format("Key: %s, Value: %s%n", entry.getKey(), entry.getValue()));
            }
        }

        return output.toString();
    }

    private List<String> toStringExclusions() {
        List<String> exclusions = new ArrayList<>();
        exclusions.add("card_number");
        exclusions.add("card_exp_date");
        exclusions.add("cvv2");
        exclusions.add("card_swipe");
        return exclusions;
    }
}
