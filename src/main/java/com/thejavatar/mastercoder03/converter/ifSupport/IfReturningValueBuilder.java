package com.thejavatar.mastercoder03.converter.ifSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lukasz Janicki on 16.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
public class IfReturningValueBuilder<T> {

    private Map<Boolean, IfActionReturningResult<T>> actionMap = new HashMap<>();
    private Boolean condition;

    private IfReturningValueBuilder(IfActionReturningResult<T> action) {
        actionMap.put(true, action);
    }

    private IfReturningValueBuilder(T value) {
        actionMap.put(true, actionReturning(value));
    }

    public static <T> IfReturningValueBuilder<T> returnValue(IfActionReturningResult<T> action) {
        return new IfReturningValueBuilder(action);
    }

    public static <T> IfReturningValueBuilder<T> returnValue(T value) {
        return new IfReturningValueBuilder(value);
    }

    public IfReturningValueBuilder<T> when(boolean condition) {
        this.condition = condition;
        return this;
    }

    public T otherwiseReturn(T value) {
        return otherwiseReturn(actionReturning(value));
    }

    public T otherwiseReturn(IfActionReturningResult<T> action) {
        if(condition == null) {
            throw new IllegalStateException("Condtion was not specified");
        }
        this.actionMap.put(false, action);
        return this.actionMap.get(condition).returnCalculationResult();
    }

    private IfActionReturningResult actionReturning(final T value) {
        return new IfActionReturningResult<T>() {
            @Override
            public T returnCalculationResult() {
                return value;
            }
        };
    }
}
