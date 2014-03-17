package com.thejavatar.mastercoder03.converter;

import com.google.common.collect.*;

import java.math.*;

import static com.thejavatar.mastercoder03.converter.ifSupport.IfReturningValueBuilder.returnValue;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

/**
 * Created by Lukasz Janicki on 16.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 *
 * Math.pow tended to loose precision too easily.
 */
public class BigDecimalPowSupport {

    private final RangeMap<Integer,Integer> systemToPrecisionMap = TreeRangeMap.create();
    private static final BigDecimalPowSupport INSTANCE = new BigDecimalPowSupport();    
    
    private BigDecimalPowSupport() {
        systemToPrecisionMap.put(Range.closed(58,62), 10000);
        systemToPrecisionMap.put(Range.closed(52,57), 6000);
        systemToPrecisionMap.put(Range.closed(46,51), 4000);
        systemToPrecisionMap.put(Range.closed(40,45), 2000);
        systemToPrecisionMap.put(Range.closed(34,39), 1000);
        systemToPrecisionMap.put(Range.closed(27,33), 800);
        systemToPrecisionMap.put(Range.closed(21,26), 600);
        systemToPrecisionMap.put(Range.closed(15,20), 400);
        systemToPrecisionMap.put(Range.closed(9,14), 200);
        systemToPrecisionMap.put(Range.closed(0,8), 100);    
    }

    public static BigDecimal pow(BigDecimal number, BigDecimal factor, BigDecimal system) {
        return INSTANCE.calculatePow(number, factor, system.intValue());
    }

    private BigDecimal calculatePow(BigDecimal number, BigDecimal factor, Integer system) {
        BigDecimal result = ONE;
        BigDecimal remainingFactor = factor;
        if (factor.compareTo(ZERO) <= 0) {
            BigDecimal divide = ONE.divide(number, new MathContext(getPrecisionForSystem(system), RoundingMode.DOWN));
            while ((remainingFactor.compareTo(ZERO) < 0)) {
                result = result.multiply(divide);
                remainingFactor = remainingFactor.add(ONE);
            }
        } else {
            while ((remainingFactor.compareTo(ZERO) > 0)) {
                result = result.multiply(number);
                remainingFactor = remainingFactor.subtract(ONE);
            }
        }
        return result;
    }

    private int getPrecisionForSystem(Integer system) {
        Integer precision = systemToPrecisionMap.get(system);
        return returnValue(precision)
                .when(precision != null)
                .otherwiseReturn(10000);
    }

}
