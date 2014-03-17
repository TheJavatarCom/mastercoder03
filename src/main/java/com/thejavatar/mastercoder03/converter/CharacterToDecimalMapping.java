package com.thejavatar.mastercoder03.converter;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.math.BigDecimal;

/**
 * Created by Lukasz Janicki on 15.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 *
 * Maps:
 * 0 <-> 0
 * A <-> 10
 * F <-> 15 etc.
 */
public class CharacterToDecimalMapping {

    private final static CharacterToDecimalMapping INSTANCE = new CharacterToDecimalMapping();
    private BidiMap<Character, BigDecimal> characterToDecimal = new DualHashBidiMap<>();

    private CharacterToDecimalMapping() {
        populateDecimalValuesForSupportedDigits();
    }

    public static CharacterToDecimalMapping getInstance() {
        return INSTANCE;
    }

    public static Character asChar(BigDecimal decimal) {
        return getInstance().getCharacter(decimal);
    }

    public BigDecimal getDecimalValue(Character digit) {
        return characterToDecimal.get(digit);
    }

    public Character getCharacter(BigDecimal digitValue) {
        return characterToDecimal.getKey(BigDecimal.valueOf(digitValue.intValue()));
    }

    public boolean isSupportedBySystem(int inputSystem, Character character) {
        return getDecimalValue(character).compareTo(BigDecimal.valueOf(inputSystem)) < 0;
    }

    private void populateDecimalValuesForSupportedDigits() {
        Integer characterValue = 0;
        for (Character character : SupportedCharacters.getSupportedNumericalCharacters()) {
            characterToDecimal.put(character, BigDecimal.valueOf(characterValue++));
        }
    }
}
