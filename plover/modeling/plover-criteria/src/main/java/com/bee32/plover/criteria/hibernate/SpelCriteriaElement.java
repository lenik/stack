package com.bee32.plover.criteria.hibernate;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

abstract class SpelCriteriaElement
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    protected static final SpelExpressionParser PARSER = new SpelExpressionParser();

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        if (context == null)
            context = getDefaultContext(obj);
        initContext(context, obj);

        Expression exp = compile();
        Object varValue = exp.getValue(context);
        return filterValue(varValue);
    }

    protected StandardEvaluationContext getDefaultContext(Object root) {
        return new StandardEvaluationContext(root);
    }

    protected void initContext(EvaluationContext context, Object obj) {
    }

    protected abstract Expression compile();

    protected final Expression compile(String exprText) {
        Expression expr = PARSER.parseExpression(exprText);
        return expr;
    }

    protected abstract boolean filterValue(Object var);

}
