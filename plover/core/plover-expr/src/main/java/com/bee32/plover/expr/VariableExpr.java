package com.bee32.plover.expr;

public class VariableExpr
        extends Expression {

    private Object value;

    public VariableExpr(Object value) {
        this.value = value;
    }

    @Override
    public Object eval() {
        return value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public void accept(IExpressionVisitor visitor) {
        visitor.visit(this);
    }

}
