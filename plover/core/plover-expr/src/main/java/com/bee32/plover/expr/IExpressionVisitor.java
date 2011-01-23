package com.bee32.plover.expr;

public interface IExpressionVisitor {

    boolean begin(Expression expression);

    void end(Expression expression);

    void visit(ConstExpr constExpr);

    void visit(VariableExpr variableExpr);

    void visit(EvalExpr evalExpr);

    void visit(BinaryExpr evalExpr);

    void visit(DeferredEvalExpr deferredEvalExpr);

}
