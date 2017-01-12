package com.mes.sdk.gateway.Level3;

/**
 * A data structure to hold Visa related level 3 line item data.<br />
 * After each applicable field is set you may call <code>toString</code> to build the outbound line item String. <br />
 * <b>If all fields are not set, you may recieve the error "Invalid Level III Line Item Detail".</b>
 *
 * @author brice
 */
public class VisaLineItem extends LineItemData {

    public static final String fieldName = "visa_line_item";

    private String commodityCode, itemDescriptor, productCode, unitOfMeasure, unitCost, vatTaxAmount, vatTaxRate, discountPerLine, lineItemTotal = "";
    private String lineItemString;
    private DebitCreditInd debitOrCredit = DebitCreditInd.D;
    private int quantity = 1;

    /**
     * @param code
     * @return
     */
    public VisaLineItem itemCommodityCode(String code) {
        this.commodityCode = code;
        return this;
    }

    /**
     * Set the description for this line item. <br />
     * 35 byte max description.
     *
     * @param descriptor
     * @return
     */
    public VisaLineItem itemDescriptor(String descriptor) {
        this.itemDescriptor = descriptor;
        return this;
    }

    /**
     * Set the quantity of this line item.
     *
     * @param quantity
     * @return
     */
    public VisaLineItem quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * @param code
     * @return
     */
    public VisaLineItem productCode(String code) {
        this.productCode = code;
        return this;
    }

    /**
     * Set the method of measuring this line item (EG EA, LBS, etc).
     *
     * @param unit
     * @return
     */
    public VisaLineItem unitOfMeasure(String unit) {
        this.unitOfMeasure = unit;
        return this;
    }

    /**
     * Set the monetary amount for the cost per unit.
     *
     * @param cost
     * @return
     */
    public VisaLineItem unitCost(String cost) {
        this.unitCost = cost;
        return this;
    }

    /**
     * Set the monetary amount for the VAT.
     *
     * @param amount
     * @return
     */
    public VisaLineItem vatTaxAmount(String amount) {
        this.vatTaxAmount = amount;
        return this;
    }

    /**
     * Set the VAT tax rate, as a percentage (EG 7.0).
     *
     * @param rate
     * @return
     */
    public VisaLineItem vatTaxRate(String rate) {
        this.vatTaxRate = rate;
        return this;
    }

    /**
     * Set the monetary amount for the line item discount.
     *
     * @param discount
     * @return
     */
    public VisaLineItem discountPerLine(String discount) {
        this.discountPerLine = discount;
        return this;
    }

    /**
     * Set the monetary amount for the entire line item.
     *
     * @param total
     * @return
     */
    public VisaLineItem lineItemTotal(String total) {
        this.lineItemTotal = total;
        return this;
    }

    /**
     * Flag whether this line item is a debit or credit.
     *
     * @param indicator
     * @return
     */
    public VisaLineItem debitOrCreditIndicator(DebitCreditInd indicator) {
        this.debitOrCredit = indicator;
        return this;
    }

    /**
     * You may optionally build the line item yourself, and set it via this method.
     *
     * @param preBuiltLineItem
     * @return
     */
    public VisaLineItem setBuiltString(String preBuiltLineItem) {
        this.lineItemString = preBuiltLineItem;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(commodityCode).append(lineItemSeperator);
        if (itemDescriptor.length() > 35)
            itemDescriptor = itemDescriptor.substring(0, 36);
        sb.append(itemDescriptor).append(lineItemSeperator);
        sb.append(productCode).append(lineItemSeperator);
        sb.append(quantity).append(lineItemSeperator);
        sb.append(unitOfMeasure).append(lineItemSeperator);
        sb.append(unitCost).append(lineItemSeperator);
        sb.append(vatTaxAmount).append(lineItemSeperator);
        sb.append(vatTaxRate).append(lineItemSeperator);
        sb.append(discountPerLine).append(lineItemSeperator);
        sb.append(lineItemTotal).append(lineItemSeperator);
        sb.append(debitOrCredit.toString());
        lineItemString = sb.toString();
        return lineItemString;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

}
