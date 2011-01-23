package com.bee32.plover.expr;

public abstract class TripleFunction<T>
        extends Function<T> {

    @Override
    public final T evaluate() {
        assertArgumentCount(3, 0);
        return null;
    }

    @Override
    public final T evaluate(Object obj) {
        assertArgumentCount(3, 1);
        return null;
    }

    @Override
    public final T evaluate(Object o1, Object o2) {
        assertArgumentCount(3, 2);
        return null;
    }

    @Override
    public abstract T evaluate(Object o1, Object o2, Object o3);

    @Override
    public final T eval(Object... args) {
        assertArgumentCount(3, args.length);
        return evaluate(args[0], args[1], args[2]);
    }

    @Override
    public final int getMinimumArgumentCount() {
        return 3;
    }

    @Override
    public final int getMaximumArgumentCount() {
        return 3;
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
        return true;
    }

}
