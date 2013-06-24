package com.mes.sdk.gateway.Level3;

/**
 * A Data structure to hold Level 3 line item data.
 * @author brice
 *
 */
public abstract class LineItemData {
  
  public static final String lineItemSeperator = "<|>";
  public static final String lineItemCountFieldName = "line_item_count";
  
  public enum DebitCreditInd {D, C};
  
  /**
   * Returns the card-specific name of the level 3 line item detail field.
   * @return
   */
  public abstract String getFieldName();
  
  @Override
  public abstract String toString();

}
