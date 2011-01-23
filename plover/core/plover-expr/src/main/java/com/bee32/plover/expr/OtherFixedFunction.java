package com.bee32.plover.expr;

public abstract class OtherFixedFunction<T>
        extends Function<T> {

    @Override
    public final T evaluate() {
        assertArgumentCount(getArgumentCount(), 0);
        return null;
    }

    @Override
    public final T evaluate(Object obj) {
        assertArgumentCount(getArgumentCount(), 1);
        return null;
    }

    @Override
    public final T evaluate(Object o1, Object o2) {
        assertArgumentCount(getArgumentCount(), 2);
        return null;
    }

    @Override
    public final T evaluate(Object o1, Object o2, Object o3) {
        assertArgumentCount(getArgumentCount(), 3);
        return null;
    }

    public abstract int getArgumentCount();

    @Override
    public final int getMinimumArgumentCount() {
        return getArgumentCount();
    }

    @Override
    public final int getMaximumArgumentCount() {
        return getArgumentCount();
    }

    @Override
    public boolean isVoid() {
        return false;
    }

    @Override
    public final boolean isUnary() {
        return false;
    }

    @Override
    public final boolean isBinary() {
        return false;
    }

    @Override
    public final boolean isTriple() {
        return false;
    }

}
