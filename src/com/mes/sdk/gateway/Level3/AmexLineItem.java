package com.mes.sdk.gateway.Level3;

/**
 * A data structure to hold American Express related level 3 line item data.<br />
 * After each applicable field is set you may call <code>toString</code> to build the outbound line item String. <br />
 * <b>If all fields are not set, you may recieve the error "Invalid Level III Line Item Detail".</b>
 * @author brice
 *
 */
public class AmexLineItem extends LineItemData {
  
  public static final String fieldName = "amex_line_item";
  
  private String itemDescription, unitCost = "";
  private String lineItemString;
  private int quantity = 1;
  
  /**
   * Set the description for this line item. <br />
   * 40 byte max description.
   * @param descriptor
   * @return
   */
  public AmexLineItem itemDescription(String descriptor) {
    this.itemDescription = descriptor;
    return this;
  }
  
  /**
   * Set the quantity of this line item.
   * @param quantity
   * @return
   */
  public AmexLineItem quantity(int quantity) {
    this.quantity = quantity;
    return this;
  }
  
  /**
   * @param cost
   * @return
   */
  public AmexLineItem unitCost(String cost) {
    this.unitCost = cost;
    return this;
  }
  
  /**
   * You may optionally build the line item yourself, and set it via this method.
   * @param preBuiltLineItem
   * @return
   */
  public AmexLineItem setBuiltString(String preBuiltLineItem) {
    this.lineItemString = preBuiltLineItem;
    return this;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if(itemDescription.length() > 40)
      itemDescription = itemDescription.substring(0, 41);
    sb.append(itemDescription).append(lineItemSeperator);
    sb.append(quantity).append(lineItemSeperator);
    sb.append(unitCost.toString());
    lineItemString = sb.toString();
    return lineItemString;
  }

  @Override
  public String getFieldName() {
    return fieldName;
  }

}
