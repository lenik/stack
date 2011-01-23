package com.bee32.plover.expr;

import javax.free.Func_;
import javax.free.Strings;

public abstract class Function<T>
        implements Func_<T, Object> {

    private static final Object[] EMPTY_OBJECT_ARRAY = new String[0];

    /**
     * 按零元函数的形式调用。
     * <p>
     * 函数可以有多种形式，在调用时应该以函数期望的形式调用，以获得性能上的优化。
     *
     * @return 函数的返回值。
     */
    public T evaluate() {
        return eval(EMPTY_OBJECT_ARRAY);
    }

    /**
     * 按一元函数的形式调用。
     * <p>
     * 函数可以有多种形式，在调用时应该以函数期望的形式调用，以获得性能上的优化。
     *
     * @param obj
     *            传递给函数的第一个变元。
     * @return 函数的返回值。
     */
    public T evaluate(Object obj) {
        return eval(new Object[] { obj });
    }

    /**
     * 按二元函数的形式调用。
     * <p>
     * 函数可以有多种形式，在调用时应该以函数期望的形式调用，以获得性能上的优化。
     *
     * @param o1
     *            传递给函数的第一个变元。
     * @param o2
     *            传递给函数的第二个变元。
     * @return 函数的返回值。
     */
    public T evaluate(Object o1, Object o2) {
        return eval(new Object[] { o1, o2 });
    }

    /**
     * 按三元函数的形式调用。
     * <p>
     * 函数可以有多种形式，在调用时应该以函数期望的形式调用，以获得性能上的优化。
     *
     * @param o1
     *            传递给函数的第一个变元。
     * @param o2
     *            传递给函数的第二个变元。
     * @param o3
     *            传递给函数的第三个变元。
     * @return 函数的返回值。
     */
    public T evaluate(Object o1, Object o2, Object o3) {
        return eval(new Object[] { o1, o2, o3 });
    }

    /**
     * 按多元函数的形式调用。
     * <p>
     * 函数可以有多种形式，在调用时应该以函数期望的形式调用，以获得性能上的优化。
     *
     * @param args
     *            传递给函数的所有变元。
     * @return 函数的返回值。
     */
    @Override
    public abstract T eval(Object... args);

    protected void assertArgumentCount(int expected, int actual) {
        if (expected != actual)
            throw new IllegalArgumentException(String.format( //
                    "Expected %d arguments, but got %d.", expected, actual));
    }

    /**
     * 获取最小参数数目。
     *
     * @return 最小参数数目。
     */
    public int getMinimumArgumentCount() {
        return 0;
    }

    /**
     * 获取最大参数数目。
     *
     * @return 最大参数数目，如果函数不限参数数目，返回 {@link Integer#MAX_VALUE}。
     */
    public int getMaximumArgumentCount() {
        return Integer.MAX_VALUE;
    }

    /**
     * 测试是否为零元函数。
     *
     * @return 如果是零元函数则返回<code>true</code> ，否则返回<code>false</code>。
     */
    public boolean isVoid() {
        return getMinimumArgumentCount() == 0 && getMaximumArgumentCount() == 0;
    }

    /**
     * 测试是否为一元函数。
     *
     * @return 如果是一元函数则返回<code>true</code> ，否则返回<code>false</code>。
     */
    public boolean isUnary() {
        return getMinimumArgumentCount() == 1 && getMaximumArgumentCount() == 1;
    }

    /**
     * 测试是否为二元函数。
     *
     * @return 如果是二元函数则返回<code>true</code> ，否则返回<code>false</code>。
     */
    public boolean isBinary() {
        return getMinimumArgumentCount() == 2 && getMaximumArgumentCount() == 2;
    }

    /**
     * 测试是否为三元函数。
     *
     * @return 如果是三元函数则返回<code>true</code> ，否则返回<code>false</code>。
     */
    public boolean isTriple() {
        return getMinimumArgumentCount() == 3 && getMaximumArgumentCount() == 3;
    }

    /**
     * 获取函数的名称。
     *
     * @return 函数名称的字符串，必须为非空的、去除了两端空格的字符串。
     */
    public String getName() {
        String name = getClass().getSimpleName();
        if (name.endsWith("Function"))
            name = name.substring(0, name.length() - "Function".length());
        name = Strings.lcfirst(name);
        return name;
    }

    /**
     * 是否建议使用中缀表达式。
     * <p>
     * 中缀表达式仅适用于二元函数。对于其它变元数目的函数，将忽略本选项。
     *
     * @return <code>true</code> 表示建议使用中缀表达式。
     */
    public boolean isInfixPreferred() {
        String name = getName();
        char lead = name.charAt(0);
        return !Character.isLetter(lead);
    }

}
