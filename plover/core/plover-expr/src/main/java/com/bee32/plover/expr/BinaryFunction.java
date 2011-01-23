package com.bee32.plover.expr;

public abstract class BinaryFunction<T>
        extends Function<T> {

    @Override
    public final T evaluate() {
        assertArgumentCount(2, 0);
        return null;
    }

    @Override
    public final T evaluate(Object obj) {
        assertArgumentCount(2, 1);
        return null;
    }

    @Override
    public abstract T evaluate(Object o1, Object o2);

    @Override
    public final T evaluate(Object o1, Object o2, Object o3) {
        assertArgumentCount(2, 3);
        return null;
    }

    @Override
    public final T eval(Object... args) {
        assertArgumentCount(2, args.length);
        return evaluate(args[0], args[1]);
    }

    @Override
    public final int getMinimumArgumentCount() {
        return 2;
    }

    @Override
    public final int getMaximumArgumentCount() {
        return 2;
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
        return true;
    }

    @Override
    public final boolean isTriple() {
        return false;
    }

}
