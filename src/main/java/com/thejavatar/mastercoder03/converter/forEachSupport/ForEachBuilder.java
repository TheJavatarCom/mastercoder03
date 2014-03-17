package com.thejavatar.mastercoder03.converter.forEachSupport;

import com.thejavatar.mastercoder03.converter.ifSupport.IfAction;

import java.util.List;

import static com.thejavatar.mastercoder03.converter.ifSupport.IfBuilder.when;

/**
 * Created by Lukasz Janicki on 16.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
public class ForEachBuilder<T> {

    private List<T> elements;
    private BreakCondition<T> breakCondition = new DoNotBreak();
    private ForEachAction<T> onBreakAction = new DoNothing();

    private ForEachBuilder(List<T> elements) {
        this.elements = elements;
    }

    public static <T> ForEachBuilder<T> forEach(List<T> elemets) {
        return new ForEachBuilder<>(elemets);
    }

    public ForEachBuilder breakWhen(BreakCondition<T> breakCondition) {
        this.breakCondition = breakCondition;
        return this;
    }

    public ForEachBuilder onBreak(ForEachAction<T> onBreakAction) {
        this.onBreakAction = onBreakAction;
        return this;
    }

    public void perform(ForEachAction<T> action) {
        try {
            int position = 0;
            for (T element : elements) {
                when(breakCondition.breakLoopCondition(element, elements))
                        .then(new ThrowForEachBreakException(element, position, elements));
                action.perform(element, position, elements);
                position++;
            }
        } catch (ForEachBreakException e) {
            onBreakAction.perform((T)e.getElementThatCausedBreak(), e.getPosition(), (List<T>) e.getElements());
        }
    }

    private class ThrowForEachBreakException implements IfAction {
        private final T elementThatCausedBreak;
        private final int position;
        private final List<T> elements;

        public ThrowForEachBreakException(T elementThatCausedBreak, int position, List<T> elements) {
            this.elementThatCausedBreak = elementThatCausedBreak;
            this.position = position;
            this.elements = elements;
        }

        @Override
        public void perform() {
            throw new ForEachBreakException(elementThatCausedBreak, position, elements);
        }
    }

    private class DoNotBreak implements BreakCondition<T> {
        @Override
        public boolean breakLoopCondition(T currentElement, List<T> list) throws ForEachBreakException {
            return false;
        }
    }

    private class DoNothing implements ForEachAction<T> {
        @Override
        public void perform(T currentElement, int position, List<T> list) { }
    }
}
