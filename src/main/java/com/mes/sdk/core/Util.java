package com.mes.sdk.core;

import com.mes.sdk.exception.ValidationException;

public class Util {

    /**
     * LUHN check.
     * Based on <a href="http://code.google.com/p/gnuc-credit-card-checker/source/browse/trunk/CCCheckerPro/src/com/gnuc/java/ccc/Luhn.java?r=3">GCCC</a>
     *
     * @param ccNumber
     * @return true on a valid card number
     */
    public static boolean checkCC(String ccNumber) {
        int sum = 0;
        boolean alternate = false;
        try {
            for (int i = ccNumber.length() - 1; i >= 0; i--) {
                int n = Integer.parseInt(ccNumber.substring(i, i + 1));
                if (alternate) {
                    n *= 2;
                    if (n > 9)
                        n = (n % 10) + 1;
                }
                sum += n;
                alternate = !alternate;
            }
        } catch (NumberFormatException e) {
            throw new ValidationException(e.getMessage());
        }
        return (sum % 10 == 0);
    }

}
