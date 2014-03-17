package com.thejavatar.mastercoder03.converter.convertingFromDecimal.forEachActions;

import com.thejavatar.mastercoder03.converter.forEachSupport.ForEachAction;

import java.util.List;

/**
 * Created by Lukasz Janicki on 16.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
class RewriteFollowingElements implements ForEachAction<Character> {

    private List<Character> newList;

    public RewriteFollowingElements(List<Character> newList) {
        this.newList = newList;
    }

    @Override
    public void perform(Character currentElement, int currentElementPosition, List<Character> list) {
        for (int i = currentElementPosition; i < list.size(); i++) {
            newList.add(list.get(i));
        }
    }
}