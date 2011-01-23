package com.bee32.plover.expr;

public class ConstExpr
        extends Expression {

    private final Object constValue;

    public ConstExpr(Object value) {
        this.constValue = value;
    }

    @Override
    public Object eval() {
        return constValue;
    }

    @Override
    public void accept(IExpressionVisitor visitor) {
        visitor.visit(this);
    }

}
