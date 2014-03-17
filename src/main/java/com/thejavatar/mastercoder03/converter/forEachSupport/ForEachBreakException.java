package com.thejavatar.mastercoder03.converter.forEachSupport;

import java.util.Collections;
import java.util.List;

/**
 * Created by Lukasz Janicki on 16.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
public class ForEachBreakException extends RuntimeException {
    private final Object elementThatCausedBreak;
    private final int position;
    private final List elements;


    public ForEachBreakException(Object elementThatCausedBreak, int position, List elements) {
        this.elementThatCausedBreak = elementThatCausedBreak;
        this.position = position;
        this.elements = elements;
    }

    public Object getElementThatCausedBreak() {
        return elementThatCausedBreak;
    }

    public List getElements() {
        return Collections.unmodifiableList(elements);
    }

    public int getPosition() {
        return position;
    }
}