package com.bee32.plover.expr;

public class DeferredEvalExpr
        extends Expression {

    private final Function<?> function;
    private Expression[] exprArgs;

    private static final boolean precheck = true;

    public DeferredEvalExpr(Function<?> function, Expression... exprArgs) {
        if (function == null)
            throw new NullPointerException("function");
        if (exprArgs == null)
            throw new NullPointerException("exprArgs");

        if (precheck)
            for (int i = 0; i < exprArgs.length; i++)
                if (exprArgs[i] == null)
                    throw new NullPointerException("exprArg:" + i);

        this.function = function;
        this.exprArgs = exprArgs;
    }

    @Override
    public Object eval() {
        // Evaluate the deferred expression arguments.
        Object[] args = new Object[exprArgs.length];
        for (int i = 0; i < exprArgs.length; i++)
            args[i] = exprArgs[i].eval();

        return function.eval(args);
    }

    @Override
    public void accept(IExpressionVisitor visitor) {
        if (visitor.begin(this))
            for (int i = 0; i < exprArgs.length; i++) {
                Expression exprArg = exprArgs[i];
                exprArg.accept(visitor);
            }
        visitor.end(this);
    }

}
