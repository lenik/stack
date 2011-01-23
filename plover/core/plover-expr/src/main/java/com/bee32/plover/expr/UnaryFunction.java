package com.bee32.plover.expr;

public abstract class UnaryFunction<T>
        extends Function<T> {

    @Override
    public final T evaluate() {
        assertArgumentCount(1, 0);
        return null;
    }

    @Override
    public abstract T evaluate(Object obj);

    @Override
    public final T evaluate(Object o1, Object o2) {
        assertArgumentCount(1, 2);
        return null;
    }

    @Override
    public final T evaluate(Object o1, Object o2, Object o3) {
        assertArgumentCount(1, 3);
        return null;
    }

    @Override
    public final T eval(Object... args) {
        assertArgumentCount(1, args.length);
        return evaluate(args[0]);
    }

    @Override
    public final int getMinimumArgumentCount() {
        return 1;
    }

    @Override
    public final int getMaximumArgumentCount() {
        return 1;
    }

    @Override
    public boolean isVoid() {
        return false;
    }

    @Override
    public final boolean isUnary() {
        return true;
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
