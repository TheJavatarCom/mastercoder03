package com.thejavatar.mastercoder03.converter.convertingFromDecimal;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;

/**
 * Created by Lukasz Janicki on 16.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
public class FromDecimalConverter {

    private final BigDecimal outputSystem;
    private final BigDecimal decimalValue;

    public FromDecimalConverter(BigDecimal outputSystem, BigDecimal decimalValue) {
        this.outputSystem = outputSystem;
        this.decimalValue = decimalValue;
    }

    public String convert() {
        String integerPart = new IntegerPartConverter(outputSystem, getIntegerPart()).convert();
        String fractionPart = new FractionPartConverter(outputSystem, getFractionPart()).convert();
        return integerPart + fractionPart;
    }

    private BigDecimal getIntegerPart() {
        return decimalValue.subtract(getFractionPart());
    }

    private BigDecimal getFractionPart() {
        return decimalValue.remainder(ONE);
    }

}
