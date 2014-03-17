package com.thejavatar.mastercoder03.converter;

import com.thejavatar.mastercoder03.converter.convertingFromDecimal.FromDecimalConverter;
import com.thejavatar.mastercoder03.converter.convertingToDecimal.ToDecimalConverter;
import com.thejavatar.mastercoder03.converter.ifSupport.IfActionReturningResult;

import java.math.BigDecimal;

import static com.thejavatar.mastercoder03.converter.ifSupport.IfReturningValueBuilder.returnValue;

/**
 * Created by Lukasz Janicki on 15.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
public class Converter {

    private final BigDecimal inputSystem;
    private final BigDecimal outputSystem;
    private final String numberToConvert;

    public Converter(BigDecimal inputSystem, BigDecimal outputSystem, String numberToConvert) {
        this.inputSystem = inputSystem;
        this.outputSystem = outputSystem;
        this.numberToConvert = numberToConvert;
    }

    public String convert() {
        validateInputData();
        return convertIfSystemsAreDifferentOtherwiseReturnGivenNumber();
    }

    private void validateInputData() {
        InputDataValidator inputDataValidator = new InputDataValidator();
        inputDataValidator.validateInputData(inputSystem.intValue(), outputSystem.intValue(), numberToConvert);
    }

    private String convertIfSystemsAreDifferentOtherwiseReturnGivenNumber() {
        return returnValue(conversionResult())
                .when(!inputSystem.equals(outputSystem))
                .otherwiseReturn(numberToConvert);
    }

    private IfActionReturningResult<String> conversionResult() {
        return new IfActionReturningResult<String>() {
            @Override
            public String returnCalculationResult() {
                BigDecimal decimalRepresentation = convertToDecimal(inputSystem, numberToConvert);
                return convertFromDecimal(outputSystem, decimalRepresentation);
            }
        };
    }

    private BigDecimal convertToDecimal(final BigDecimal inputSystem, String numberToConvert) {
        ToDecimalConverter toDecimalConverter = new ToDecimalConverter(inputSystem, numberToConvert);
        return toDecimalConverter.convert();
    }

    private String convertFromDecimal(BigDecimal outputSystem, BigDecimal decimalValue) {
        FromDecimalConverter fromDecimalConverter = new FromDecimalConverter(outputSystem, decimalValue);
        return fromDecimalConverter.convert();
    }
}
