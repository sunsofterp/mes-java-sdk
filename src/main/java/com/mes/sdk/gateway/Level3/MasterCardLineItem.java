package com.mes.sdk.gateway.Level3;

/**
 * A data structure to hold MasterCard related level 3 line item data.<br />
 * After each applicable field is set you may call <code>toString</code> to build the outbound line item String.
 *
 * @author brice
 */
public class MasterCardLineItem extends LineItemData {

    public enum NetGrossInd {N, G}

    ;

    public enum DiscountInd {Y, N}

    ;

    public static final String fieldName = "mc_line_item";

    private String itemDescription, productCode, unitOfMeasure, altTaxIdentifier, taxRateApplied, taxTypeApplied, taxAmount, extendedItemAmount, discountAmount = "";
    private String lineItemString;
    private NetGrossInd netGrossIndicator = NetGrossInd.N;
    private DebitCreditInd debitOrCredit = DebitCreditInd.D;
    private DiscountInd discountIndicator = DiscountInd.N;
    private int quantity = 1;

    /**
     * Set the description for this line item. <br />
     * 35 byte max description.
     *
     * @param descriptor
     * @return
     */
    public MasterCardLineItem itemDescription(String description) {
        this.itemDescription = description;
        return this;
    }

    /**
     * Set the quantity of this line item.
     *
     * @param quantity
     * @return
     */
    public MasterCardLineItem quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * @param code
     * @return
     */
    public MasterCardLineItem productCode(String code) {
        this.productCode = code;
        return this;
    }

    /**
     * Set the method of measuring this line item (EG EA, LBS, etc).
     *
     * @param unit
     * @return
     */
    public MasterCardLineItem unitOfMeasure(String unit) {
        this.unitOfMeasure = unit;
        return this;
    }

    /**
     * Sets the MC alternate tax identifier.
     *
     * @param identifier
     * @return
     */
    public MasterCardLineItem altTaxIdentifier(String identifier) {
        this.altTaxIdentifier = identifier;
        return this;
    }

    /**
     * Sets the tax rate that was applied.
     *
     * @param rate
     * @return
     */
    public MasterCardLineItem taxRateApplied(String rate) {
        this.taxRateApplied = rate;
        return this;
    }

    /**
     * Sets the tax type that was applied.
     *
     * @param type
     * @return
     */
    public MasterCardLineItem taxTypeApplied(String type) {
        this.taxTypeApplied = type;
        return this;
    }

    /**
     * Sets the tax amount.
     *
     * @param amount
     * @return
     */
    public MasterCardLineItem taxAmount(String amount) {
        this.taxAmount = amount;
        return this;
    }

    /**
     * Sets the discount indicator.
     *
     * @param indicator
     * @return
     */
    public MasterCardLineItem discountIndicator(DiscountInd indicator) {
        this.discountIndicator = indicator;
        return this;
    }

    /**
     * Sets the debit / credit indicator.
     *
     * @param indicator
     * @return
     */
    public MasterCardLineItem debitOrCreditIndicator(DebitCreditInd indicator) {
        this.debitOrCredit = indicator;
        return this;
    }

    /**
     * Sets the net / gross indicator.
     *
     * @param indicator
     * @return
     */
    public MasterCardLineItem netGrossIndicator(NetGrossInd indicator) {
        this.netGrossIndicator = indicator;
        return this;
    }

    /**
     * Sets the extended item amount.
     *
     * @param amount
     * @return
     */
    public MasterCardLineItem extendedItemAmount(String amount) {
        this.extendedItemAmount = amount;
        return this;
    }

    /**
     * Sets the discount amount.
     *
     * @param amount
     * @return
     */
    public MasterCardLineItem discountAmount(String amount) {
        this.discountAmount = amount;
        return this;
    }

    /**
     * You may optionally build the line item yourself, and set it via this method.
     *
     * @param preBuiltLineItem
     * @return
     */
    public MasterCardLineItem setBuiltString(String preBuiltLineItem) {
        this.lineItemString = preBuiltLineItem;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (itemDescription.length() > 35)
            itemDescription = itemDescription.substring(0, 36);
        sb.append(itemDescription).append(lineItemSeperator);
        sb.append(productCode).append(lineItemSeperator);
        sb.append(quantity).append(lineItemSeperator);
        sb.append(unitOfMeasure).append(lineItemSeperator);
        sb.append(altTaxIdentifier).append(lineItemSeperator);
        sb.append(taxRateApplied).append(lineItemSeperator);
        sb.append(taxTypeApplied).append(lineItemSeperator);
        sb.append(taxAmount).append(lineItemSeperator);
        sb.append(discountIndicator.toString()).append(lineItemSeperator);
        sb.append(netGrossIndicator.toString()).append(lineItemSeperator);
        sb.append(extendedItemAmount).append(lineItemSeperator);
        sb.append(debitOrCredit.toString()).append(lineItemSeperator);
        sb.append(discountAmount);
        lineItemString = sb.toString();
        return lineItemString;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

}
