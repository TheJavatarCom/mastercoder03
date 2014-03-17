package com.thejavatar.mastercoder03.converter.convertingFromDecimal;

import com.google.common.collect.Lists;
import com.google.common.primitives.Chars;
import com.thejavatar.mastercoder03.converter.forEachSupport.BreakCondition;
import com.thejavatar.mastercoder03.converter.ifSupport.IfAction;
import com.thejavatar.mastercoder03.converter.ifSupport.IfActionReturningResult;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.thejavatar.mastercoder03.Application.DECIMAL_SEPARATOR;
import static com.thejavatar.mastercoder03.Application.PRECISION;
import static com.thejavatar.mastercoder03.converter.BigDecimalPowSupport.pow;
import static com.thejavatar.mastercoder03.converter.CharacterToDecimalMapping.asChar;
import static com.thejavatar.mastercoder03.converter.convertingFromDecimal.forEachActions.ForEachActionFactory.rewriteFollowingElements;
import static com.thejavatar.mastercoder03.converter.convertingFromDecimal.forEachActions.ForEachActionFactory.rewriteToListIfDifferenetThanZero;
import static com.thejavatar.mastercoder03.converter.forEachSupport.ForEachBuilder.forEach;
import static com.thejavatar.mastercoder03.converter.ifSupport.IfBuilder.when;
import static com.thejavatar.mastercoder03.converter.ifSupport.IfReturningValueBuilder.returnValue;
import static java.math.BigDecimal.ZERO;

/**
 * Created by Lukasz Janicki on 16.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
class FractionPartConverter {

    private final BigDecimal outputSystem;
    private final BigDecimal fractionPart;

    public FractionPartConverter(BigDecimal outputSystem, BigDecimal fractionPart) {
        this.outputSystem = outputSystem;
        this.fractionPart = fractionPart;
    }

    public String convert() {
        return returnValue(outputSystemRepresentationOfFractionPart())
                .when(fractionPart.compareTo(ZERO) != 0)
                .otherwiseReturn("");
    }

    private IfActionReturningResult<String> outputSystemRepresentationOfFractionPart() {
        return new IfActionReturningResult<String>() {
            @Override

            public String returnCalculationResult() {
                OutputSystemRepresentation result = new OutputSystemRepresentationCalculator().getRepresentation();
                return new ResultFormatter().formatResult(result);
            }
        };
    }

    private class OutputSystemRepresentationCalculator {

        private BigDecimal result = ZERO;
        private BigDecimal simulatedResult = ZERO;
        private Integer lastProcessedPrecision = 0;

        public OutputSystemRepresentation getRepresentation() {
            List<Character> approximateRepresentation = new ArrayList<>(PRECISION);
            for (int i = 1; i <= PRECISION; i++) {
                for (int number = outputSystem.intValue() - 1; number >= 0 && lastProcessedPrecision != i; number--) {
                    simulateAddingNumberInIPosition(i, number);
                    updateResultIfSuccessfulSimulation();
                    saveNumberIfSuccessfulSimulation(approximateRepresentation, number);
                    markIPositionAsProcessedIfSuccessfulSimulation(i);
                }
            }
            return new OutputSystemRepresentation(approximateRepresentation, isResultIdenticalToOriginalFraction());
        }

        private void simulateAddingNumberInIPosition(int i, int number) {
            BigDecimal decimalValueOfNumberInIPostion = BigDecimal.valueOf(number).multiply(outputSystemToNegativeIPower(i));
            simulatedResult = result.add(decimalValueOfNumberInIPostion);
        }

        private void updateResultIfSuccessfulSimulation() {
            result = returnValue(simulatedResult)
                    .when(simulatedResultDoesNotExceedOriginalFraction())
                    .otherwiseReturn(result);
        }

        private void saveNumberIfSuccessfulSimulation(List<Character> approximateRepresentation, int number) {
            when(numberWasApplied())
                    .then(addNumberToResultList(number, approximateRepresentation));
        }

        private void markIPositionAsProcessedIfSuccessfulSimulation(final int position) {
            when(numberWasApplied())
                    .then(new IfAction() {
                        @Override
                        public void perform() {
                            lastProcessedPrecision = position;
                        }
                    });
        }

        private boolean isResultIdenticalToOriginalFraction() {
            return result.compareTo(fractionPart) == 0;
        }

        private boolean simulatedResultDoesNotExceedOriginalFraction() {
            return simulatedResult.compareTo(fractionPart) <= 0;
        }

        private boolean numberWasApplied() {
            return result.compareTo(simulatedResult) == 0;
        }

        private BigDecimal outputSystemToNegativeIPower(int i) {
            return pow(outputSystem, BigDecimal.valueOf(i).negate(), outputSystem);
        }

        private IfAction addNumberToResultList(final int number, final List<Character> approximateRepresentation) {
            return new IfAction() {
                @Override
                public void perform() {
                    approximateRepresentation.add(asChar(BigDecimal.valueOf(number)));
                }
            };
        }
    }

    private class OutputSystemRepresentation {

        private final String result;
        private final boolean identicalToOriginalFraction;

        public OutputSystemRepresentation(List<Character> result, boolean identicalToOriginalFraction) {
            this.identicalToOriginalFraction = identicalToOriginalFraction;
            this.result = StringUtils.join(result.toArray(), "");
        }

        public String getResult() {
            return result;
        }

        public boolean isIdenticalToOriginalFraction() {
            return identicalToOriginalFraction;
        }
    }

    private class ResultFormatter {

        public String formatResult(OutputSystemRepresentation result) {
            String formattedResult = returnValue(removedTrailingZeros(result.getResult()))
                    .when(result.isIdenticalToOriginalFraction())
                    .otherwiseReturn(result.getResult());
            formattedResult = addDecimalSeparator(formattedResult);
            return formattedResult;
        }

        private IfActionReturningResult<String> removedTrailingZeros(final String result) {
            return new IfActionReturningResult<String>() {
                @Override
                public String returnCalculationResult() {
                    List<Character> characters = new ArrayList<>(Chars.asList(result.toCharArray()));
                    List<Character> reversedCharacters = Lists.reverse(characters);
                    List<Character> listWithoutZeros = new ArrayList<>();
                    forEach(reversedCharacters)
                            .breakWhen(new BreakWhenCharacterIsDifferentThanZero())
                            .onBreak(rewriteFollowingElements(listWithoutZeros))
                            .perform(rewriteToListIfDifferenetThanZero(listWithoutZeros));
                    return StringUtils.join(Lists.reverse(listWithoutZeros), "");
                }
            };
        }

        private String addDecimalSeparator(String result) {
            return DECIMAL_SEPARATOR + result;
        }

        private class BreakWhenCharacterIsDifferentThanZero implements BreakCondition<Character> {
            @Override
            public boolean breakLoopCondition(Character currentElement, List<Character> list) {
                return currentElement.compareTo('0') != 0;
            }
        }
    }
}
