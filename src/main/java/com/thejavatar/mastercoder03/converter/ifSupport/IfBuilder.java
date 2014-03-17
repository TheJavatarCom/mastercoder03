package com.thejavatar.mastercoder03.converter.ifSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lukasz Janicki on 16.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
public class IfBuilder {

    private boolean condition;
    private Map<Boolean, IfAction> actionMap = new HashMap<>();

    private IfBuilder(boolean condition) {
        this.condition = condition;
        actionMap.put(false, new NoAction());
    }

    public static IfBuilder when(boolean condition) {
        return new IfBuilder(condition);
    }

    public void then(IfAction action) {
        actionMap.put(true, action);
        actionMap.get(condition).perform();
    }

    private class NoAction implements IfAction {
        @Override
        public void perform() { }
    }
}
