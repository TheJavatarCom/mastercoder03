package com.thejavatar.mastercoder03.converter.convertingFromDecimal.forEachActions;

import com.thejavatar.mastercoder03.converter.forEachSupport.ForEachAction;

import java.util.List;

/**
 * Created by Lukasz Janicki on 16.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
public class ForEachActionFactory {

    public static ForEachAction rewriteFollowingElements(List<Character> newList) {
        return new RewriteFollowingElements(newList);
    }

    public static ForEachAction rewriteToListIfDifferenetThanZero(List<Character> newList) {
        return new RewriteToListIfDifferentThanZero(newList);
    }
}
