package com.thejavatar.mastercoder03.converter;

import com.thejavatar.mastercoder03.converter.ifSupport.IfAction;

import static com.thejavatar.mastercoder03.Application.DECIMAL_SEPARATOR;
import static com.thejavatar.mastercoder03.Application.MAX_SUPPORTED_SYSTEM;
import static com.thejavatar.mastercoder03.Application.MIN_SUPPORTED_SYSTEM;
import static com.thejavatar.mastercoder03.converter.ifSupport.IfBuilder.when;
import static java.lang.String.valueOf;
import static org.apache.commons.lang3.StringUtils.countMatches;

/**
 * Created by Lukasz Janicki on 15.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
public class InputDataValidator {

    public void validateInputData(int inputSystem, int outputSystem, String numberToConvert) {
        checkIfNumericSystemIsInScope(inputSystem);
        checkIfNumericSystemIsInScope(outputSystem);
        checkIfNumberContainsOnlySupportedCharacters(numberToConvert);
        checkIfSeparatorIsPresentOnlyOnce(numberToConvert);
        checkIfFractionalPartIsFourDigitsLongAtMost(numberToConvert);
        checkIfNumberContainsCharsPermittedForInputSystem(inputSystem, numberToConvert);
        checkIfNumberIsNotNegative(numberToConvert);
    }

    private void checkIfNumericSystemIsInScope(int system) throws IllegalArgumentException {
        when(system > MAX_SUPPORTED_SYSTEM)
                .then(throwException("System cannot be greater than: " + MAX_SUPPORTED_SYSTEM));
        when(system < MIN_SUPPORTED_SYSTEM)
                .then(throwException("System cannot be lower than: " + MIN_SUPPORTED_SYSTEM));
    }

    private void checkIfNumberContainsOnlySupportedCharacters(String numberToConvert) {
        for(Character character : numberToConvert.toCharArray()) {
            when(SupportedCharacters.isNotSupportedNumericalCharacter(character))
                    .then(throwException(character + " is not permitted"));
        }
    }

    private void checkIfSeparatorIsPresentOnlyOnce(String numberToConvert) {
        when(countMatches(numberToConvert, valueOf(DECIMAL_SEPARATOR)) > 1)
                .then(throwException(DECIMAL_SEPARATOR + " is present more than once"));
    }

    private void checkIfFractionalPartIsFourDigitsLongAtMost(String numberToConvert) {
        when(numberToConvert.contains(valueOf(DECIMAL_SEPARATOR)))
                .then(new ThrowExceptionIfMoreThanFourFractionalDigits(numberToConvert));
    }

    private void checkIfNumberContainsCharsPermittedForInputSystem(int inputSystem, String numberToConvert) {
        String numberWithoutDecimalSeparator = numberToConvert.replace(valueOf(DECIMAL_SEPARATOR), "");
        for(Character character : numberWithoutDecimalSeparator.toCharArray()) {
            when(characterNotSupportedBySystem(inputSystem, character))
                    .then(throwException(character + " is not permitted in " + inputSystem + " system"));
        }
    }

    private void checkIfNumberIsNotNegative(String numberToConvert) {
        when(numberToConvert.substring(0,1).equals("-"))
                .then(throwException("Number cannot be negative"));
    }

    private boolean characterNotSupportedBySystem(int inputSystem, Character character) {
        return !CharacterToDecimalMapping.getInstance().isSupportedBySystem(inputSystem, character);
    }

    private ThrowIllegalArgumentExceptionAction throwException(String message) {
        return new ThrowIllegalArgumentExceptionAction(message);
    }

    private class ThrowExceptionIfMoreThanFourFractionalDigits implements IfAction {
        private String numberToConvert;

        public ThrowExceptionIfMoreThanFourFractionalDigits(String numberToConvert) {
            this.numberToConvert = numberToConvert;
        }

        @Override
        public void perform() {
            String fractionalPart = numberToConvert.split(String.valueOf(DECIMAL_SEPARATOR))[1];
            when(fractionalPart.length() > 4)
                    .then(throwException("Only four fractional numbers are permitted. Number: " + numberToConvert));
        }
    }

    private class ThrowIllegalArgumentExceptionAction implements IfAction {
        private String message;

        public ThrowIllegalArgumentExceptionAction(String message) {
            this.message = message;
        }
        @Override
        public void perform() {
            throw new IllegalArgumentException(message);
        }
    }
}
