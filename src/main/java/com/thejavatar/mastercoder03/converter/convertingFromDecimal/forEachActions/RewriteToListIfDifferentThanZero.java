package com.thejavatar.mastercoder03.converter.convertingFromDecimal.forEachActions;

import com.thejavatar.mastercoder03.converter.forEachSupport.ForEachAction;
import com.thejavatar.mastercoder03.converter.ifSupport.IfAction;

import java.util.List;

import static com.thejavatar.mastercoder03.converter.ifSupport.IfBuilder.when;

/**
 * Created by Lukasz Janicki on 16.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
class RewriteToListIfDifferentThanZero implements ForEachAction<Character> {

    private List<Character> newList;

    public RewriteToListIfDifferentThanZero(List<Character> newList) {
        this.newList = newList;
    }

    @Override
    public void perform(Character currentElement, int currentElementPosition, List<Character> list) {
        when(currentElement.compareTo('0') != 0).then(new RewriteElement(newList, currentElement));
    }

    private class RewriteElement implements IfAction {
        private final List<Character> newList;
        private final Character currentElement;

        public RewriteElement(List<Character> newList, Character currentElement) {
            this.newList = newList;
            this.currentElement = currentElement;
        }

        @Override
        public void perform() {
            newList.add(currentElement);
        }
    }
}
