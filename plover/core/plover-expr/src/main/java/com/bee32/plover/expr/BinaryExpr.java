package com.bee32.plover.expr;

public abstract class BinaryExpr
        extends Expression {

    protected final IOperatorSchema schema;

    protected Object a;
    protected Object b;

    public BinaryExpr(IOperatorSchema schema, Object a, Object b) {
        if (schema == null)
            throw new NullPointerException("schema");
        this.schema = schema;

        setA(a);
        setB(b);
    }

    public Object getA() {
        return a;
    }

    public void setA(Object a) {
        if (a == null)
            throw new NullPointerException("a");
        this.a = a;
    }

    public Object getB() {
        return b;
    }

    public void setB(Object b) {
        if (b == null)
            throw new NullPointerException("b");
        this.b = b;
    }

    @Override
    public void accept(IExpressionVisitor visitor) {
        visitor.visit(this);
    }

}