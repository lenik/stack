package com.bee32.plover.expr;

public abstract class DeferredBinaryExpr
        extends Expression {

    protected final IOperatorSchema schema;

    private Expression aExpr;
    private Expression bExpr;

    public DeferredBinaryExpr(IOperatorSchema schema, Expression aExpr, Expression bExpr) {
        if (schema == null)
            throw new NullPointerException("schema");
        this.schema = schema;

        setAExpr(aExpr);
        setBExpr(bExpr);
    }

    public Expression getAExpr() {
        return aExpr;
    }

    public void setAExpr(Expression aExpr) {
        if (aExpr == null)
            throw new NullPointerException("aExpr");
        this.aExpr = aExpr;
    }

    public Expression getBExpr() {
        return bExpr;
    }

    public void setBExpr(Expression bExpr) {
        if (bExpr == null)
            throw new NullPointerException("bExpr");
        this.bExpr = bExpr;
    }

    @Override
    public Object eval() {
        Object a = aExpr.eval();
        Object b = bExpr.eval();
        return eval(a, b);
    }

    public abstract Object eval(Object a, Object b);

    @Override
    public void accept(IExpressionVisitor visitor) {
        if (visitor.begin(this)) {
            aExpr.accept(visitor);
            bExpr.accept(visitor);
        }
        visitor.end(this);
    }

}