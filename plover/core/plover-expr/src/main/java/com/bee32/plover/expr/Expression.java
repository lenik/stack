package com.bee32.plover.expr;

import javax.free.Func0;
import javax.free.Strings;

public abstract class Expression
        implements Func0<Object> {

    /**
     * 获取表达式的名称。
     *
     * @return 表达式名称的字符串，必须为非空的、去除了两端空格的字符串。
     */
    public String getName() {
        String name = getClass().getSimpleName();

        if (name.endsWith("Expr"))
            name = name.substring(0, name.length() - "Expr".length());
        else if (name.endsWith("Expression"))
            name = name.substring(0, name.length() - "Expression".length());

        name = Strings.lcfirst(name);
        return name;
    }

    public abstract void accept(IExpressionVisitor visitor);

    public String toString() {
        ExpressionFormatter formatter = new ExpressionFormatter();
        accept(formatter);
        return formatter.toString();
    }

}
