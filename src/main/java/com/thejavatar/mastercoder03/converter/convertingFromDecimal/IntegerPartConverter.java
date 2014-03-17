package com.thejavatar.mastercoder03.converter.convertingFromDecimal;

import com.thejavatar.mastercoder03.converter.divisionUtils.DivisionResult;

import java.math.BigDecimal;

import static com.thejavatar.mastercoder03.converter.CharacterToDecimalMapping.asChar;
import static com.thejavatar.mastercoder03.converter.divisionUtils.DivisionBuilder.divide;

/**
 * Created by Lukasz Janicki on 16.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
class IntegerPartConverter {

    private final BigDecimal outputSystem;
    private final BigDecimal integerPart;
    private final StringBuilder resultString = new StringBuilder();

    IntegerPartConverter(BigDecimal outputSystem, BigDecimal integerPart) {
        this.outputSystem = outputSystem;
        this.integerPart = integerPart;
    }

    public String convert() {
        DivisionResult divisionResult = divide(integerPart).by(outputSystem);
        while(quotientDifferentThanZero(divisionResult)) {
            saveRemainder(divisionResult);
            divisionResult = divide(divisionResult.getQuotient()).by(outputSystem);
        }
        saveRemainder(divisionResult);
        return resultString.reverse().toString();
    }

    private void saveRemainder(DivisionResult divisionResult) {
        resultString.append(asChar(divisionResult.getRemainder()));
    }

    private boolean quotientDifferentThanZero(DivisionResult divisionResult) {
        return divisionResult.getQuotient().compareTo(BigDecimal.ZERO) != 0;
    }
}
