package com.bee32.plover.ox1.formula;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class FormulaEvaluatorTest
        extends Assert {

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    public static void main(String[] args) {
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        ctx.setVariable("a", 100);

        Map<String, Object> vars = new HashMap<>();
        vars.put("a", 1000);
        vars.put("x", 10);

        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expr = parser.parseExpression("1 + 3+#a");

        System.out.println(expr.getValue(ctx, ""));
    }

}
