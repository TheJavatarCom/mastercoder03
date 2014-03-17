package com.thejavatar.mastercoder03.converter.divisionUtils;

import java.math.BigDecimal;

/**
 * Created by Lukasz Janicki on 15.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
public class DivisionResult {

    private final BigDecimal quotient;
    private final BigDecimal remainder;

    public DivisionResult(BigDecimal quotient, BigDecimal remainder) {
        this.quotient = quotient;
        this.remainder = remainder;
    }

    public BigDecimal getQuotient() {
        return quotient;
    }

    public BigDecimal getRemainder() {
        return remainder;
    }
}
