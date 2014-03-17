package com.thejavatar.mastercoder03.converter;

import java.util.*;

import static com.thejavatar.mastercoder03.Application.DECIMAL_SEPARATOR;
import static com.thejavatar.mastercoder03.converter.ifSupport.IfReturningValueBuilder.returnValue;

/**
 * Created by Lukasz Janicki on 15.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
public class SupportedCharacters {

    private static final List<Character> SUPPORTED_NUMERICAL_CHARACTERS = Arrays.asList('0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');

    private static final List<Character> ADDITIONAL_SUPPORTED_CHARACTERS = Arrays.asList(DECIMAL_SEPARATOR);

    public static boolean isNotSupportedNumericalCharacter(Character character) {
        ArrayList<Character> supportedCharacters = new ArrayList<>(SUPPORTED_NUMERICAL_CHARACTERS);
        supportedCharacters.addAll(ADDITIONAL_SUPPORTED_CHARACTERS);
        return returnValue(false)
                .when(supportedCharacters.contains(character))
                .otherwiseReturn(true);
    }

    public static List<Character> getSupportedNumericalCharacters() {
        return Collections.unmodifiableList(SUPPORTED_NUMERICAL_CHARACTERS);
    }
}
