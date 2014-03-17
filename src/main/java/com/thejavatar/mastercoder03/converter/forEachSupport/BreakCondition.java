package com.thejavatar.mastercoder03.converter.forEachSupport;

import java.util.List;

/**
 * Created by Lukasz Janicki on 16.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
public interface BreakCondition<T> {
    boolean breakLoopCondition(T currentElement, List<T> list);
}
