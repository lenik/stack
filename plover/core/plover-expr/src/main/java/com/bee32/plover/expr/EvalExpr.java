package com.bee32.plover.expr;

public class EvalExpr
        extends Expression {

    private final Function<?> function;
    private Object[] args;

    public EvalExpr(Function<?> function, Object... args) {
        if (function == null)
            throw new NullPointerException("function");
        if (args == null)
            throw new NullPointerException("args");

        this.function = function;
        this.args = args;
    }

    @Override
    public Object eval() {
        return function.eval(args);
    }

    @Override
    public void accept(IExpressionVisitor visitor) {
        visitor.visit(this);
    }

}
