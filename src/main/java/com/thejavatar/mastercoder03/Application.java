package com.thejavatar.mastercoder03;

import com.thejavatar.mastercoder03.converter.Converter;

import java.math.BigDecimal;

/**
 * Created by Lukasz Janicki on 16.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
public class Application {

    public static final Integer MAX_SUPPORTED_SYSTEM = 62;
    public static final Integer MIN_SUPPORTED_SYSTEM = 2;
    public static final int PRECISION = 4;
    public static final char DECIMAL_SEPARATOR = ',';

    /* Broken naming convention for the sake of API's contract. */
    public String Convert(final int inputSystem, final int outputSystem, final String numberToConvert) {
        return new Converter(BigDecimal.valueOf(inputSystem), BigDecimal.valueOf(outputSystem), numberToConvert)
                .convert();
    }

}
