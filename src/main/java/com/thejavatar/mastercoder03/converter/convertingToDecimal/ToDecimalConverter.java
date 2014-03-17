package com.thejavatar.mastercoder03.converter.convertingToDecimal;

import com.thejavatar.mastercoder03.converter.BigDecimalPowSupport;
import com.thejavatar.mastercoder03.converter.CharacterToDecimalMapping;
import com.thejavatar.mastercoder03.converter.ifSupport.IfActionReturningResult;

import java.math.BigDecimal;

import static com.thejavatar.mastercoder03.Application.DECIMAL_SEPARATOR;
import static com.thejavatar.mastercoder03.converter.ifSupport.IfReturningValueBuilder.returnValue;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

/**
 * Created by Lukasz Janicki on 16.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
public class ToDecimalConverter {

    private final CharacterToDecimalMapping digitToDecimalMapping = CharacterToDecimalMapping.getInstance();
    private final BigDecimal inputSystem;
    private final String numberToConvert;

    public ToDecimalConverter(BigDecimal inputSystem, String numberToConvert) {
        this.inputSystem = inputSystem;
        this.numberToConvert = numberToConvert;
    }

    public BigDecimal convert() {
        return returnValue(resultForBothIntegerAndFractionalPart())
                .when(numberHasFractionalPart())
                .otherwiseReturn(resultForIntegerPart());
    }

    private boolean numberHasFractionalPart() {
        return numberToConvert.contains(String.valueOf(DECIMAL_SEPARATOR));
    }

    private IfActionReturningResult<BigDecimal> resultForIntegerPart() {
        return new IfActionReturningResult<BigDecimal>() {
            @Override
            public BigDecimal returnCalculationResult() {
                return calculateSumFromIntegerPart(numberToConvert);
            }
        };
    }

    private IfActionReturningResult<BigDecimal> resultForBothIntegerAndFractionalPart() {
        return new IfActionReturningResult<BigDecimal>() {
            @Override
            public BigDecimal returnCalculationResult() {
                String[] splittedByDecimalPoint = numberToConvert.split(String.valueOf(DECIMAL_SEPARATOR));
                BigDecimal resultValue = ZERO;
                resultValue = resultValue.add(calculateSumFromIntegerPart(splittedByDecimalPoint[0]));
                resultValue = resultValue.add(calculateSumFromFractionPart(splittedByDecimalPoint[1]));
                return resultValue;
            }
        };
    }

    private BigDecimal calculateSumFromIntegerPart(String number) {
        BigDecimal digitPosition = BigDecimal.valueOf(number.length() - 1);
        BigDecimal resultValue = ZERO;
        for (char digit : number.toCharArray()) {
            resultValue = resultValue.add(computeAugend(digit, digitPosition, inputSystem));
            digitPosition = digitPosition.subtract(ONE);
        }
        return resultValue;
    }

    private BigDecimal calculateSumFromFractionPart(final String fractionPart) {
        BigDecimal digitPosition = ONE;
        BigDecimal resultValue = ZERO;
        for (char digit : fractionPart.toCharArray()) {
            resultValue = resultValue.add(computeAugend(digit, digitPosition.negate(), inputSystem));
            digitPosition = digitPosition.add(ONE);
        }
        return resultValue;
    }

    private BigDecimal computeAugend(char digit, BigDecimal digitPosition, BigDecimal inputSystem) {
        BigDecimal decimalValueOfDigit = digitToDecimalMapping.getDecimalValue(digit);
        return decimalValueOfDigit.multiply(BigDecimalPowSupport.pow(inputSystem, digitPosition, inputSystem));
    }
}
