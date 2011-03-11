package com.bee32.plover.expr;

public class ExpressionFormatter
        extends AbstractExpressionVisitor {

    private final StringBuilder buffer;

    public ExpressionFormatter() {
        this.buffer = new StringBuilder();
    }

    public void reset() {
        buffer.setLength(0);
    }

    @Override
    public boolean begin(Expression expression) {
        return true;
    }

    @Override
    public void end(Expression expression) {
    }

}
