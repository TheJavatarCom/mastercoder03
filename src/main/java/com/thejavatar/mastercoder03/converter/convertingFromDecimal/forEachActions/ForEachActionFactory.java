package com.thejavatar.mastercoder03.converter.convertingFromDecimal.forEachActions;

import com.thejavatar.mastercoder03.converter.forEachSupport.ForEachAction;

import java.util.List;

/**
 * Created by Lukasz Janicki on 16.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
public class ForEachActionFactory {

    public static ForEachAction rewriteThisAndFollowingElements(List<Character> newList) {
        return new RewriteFollowingElements(newList);
    }

    public static ForEachAction ignoreZeroCharacter() {
        return new ForEachAction<Character>() {
            @Override
            public void perform(Character currentElement, int currentElementPosition, List<Character> list) {}
        };
    }
}
