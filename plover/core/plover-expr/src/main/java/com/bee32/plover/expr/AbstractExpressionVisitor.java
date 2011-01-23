package com.bee32.plover.expr;

public abstract class AbstractExpressionVisitor
        implements IExpressionVisitor {

    @Override
    public boolean begin(Expression expression) {
        return true;
    }

    @Override
    public void end(Expression expression) {
    }

    @Override
    public void visit(ConstExpr constExpr) {
    }

    @Override
    public void visit(VariableExpr variableExpr) {
    }

    @Override
    public void visit(EvalExpr evalExpr) {
    }

    @Override
    public void visit(BinaryExpr evalExpr) {
    }

    @Override
    public void visit(DeferredEvalExpr deferredEvalExpr) {
    }

}
