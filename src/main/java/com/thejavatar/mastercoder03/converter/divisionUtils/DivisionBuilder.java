package com.thejavatar.mastercoder03.converter.divisionUtils;

import java.math.BigDecimal;

/**
 * Created by Lukasz Janicki on 15.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
public class DivisionBuilder {

    private BigDecimal dividend;

    private DivisionBuilder(BigDecimal dividend) {
        this.dividend = dividend;
    }

    public static DivisionBuilder divide(BigDecimal dividend) {
        return new DivisionBuilder(dividend);
    }

    public DivisionResult by(BigDecimal divisor) {
        BigDecimal[] result = dividend.divideAndRemainder(divisor);
        return new DivisionResult(result[0], result[1]);
    }
}
