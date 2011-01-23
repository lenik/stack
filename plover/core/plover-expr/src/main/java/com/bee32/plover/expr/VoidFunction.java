package com.bee32.plover.expr;

public abstract class VoidFunction<T>
        extends Function<T> {

    @Override
    public abstract T evaluate();

    @Override
    public final T evaluate(Object obj) {
        assertArgumentCount(0, 1);
        return null;
    }

    @Override
    public final T evaluate(Object o1, Object o2) {
        assertArgumentCount(0, 2);
        return null;
    }

    @Override
    public final T evaluate(Object o1, Object o2, Object o3) {
        assertArgumentCount(0, 3);
        return null;
    }

    @Override
    public final T eval(Object... args) {
        assertArgumentCount(0, args.length);
        return evaluate();
    }

    @Override
    public final int getMinimumArgumentCount() {
        return 0;
    }

    @Override
    public final int getMaximumArgumentCount() {
        return 0;
    }

    @Override
    public final boolean isVoid() {
        return true;
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
