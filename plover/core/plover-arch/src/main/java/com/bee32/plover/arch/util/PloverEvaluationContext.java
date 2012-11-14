package com.bee32.plover.arch.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.bee32.sem.salary.util.ChineseCodec;

public class PloverEvaluationContext
        extends StandardEvaluationContext {

    public PloverEvaluationContext() {
        super(new StandardEvaluationContext());
        importStaticFunctions(Math.class);
    }

    void importStaticFunctions(Class<?> clazz) {
        for (Method method : clazz.getMethods()) {
            int modifier = method.getModifiers();
            if (!Modifier.isPublic(modifier))
                continue;
            if (!Modifier.isStatic(modifier))
                continue;
            registerFunction(method.getName(), method);
        }
    }

    /**
     * @throws ParseException
     * @throws EvaluationException
     */
    public Object evaluateZh(String expr) {
        String encoded = ChineseCodec.encode(expr);
        return evaluate(encoded);
    }

    /**
     * @throws ParseException
     * @throws EvaluationException
     */
    public Object evaluate(String expr) {
        String[] statements = expr.split(";");
        Object lastResult = null;

        SpelExpressionParser parser = new SpelExpressionParser();
        for (String statement : statements) {
            String varName = null;
            int colonEq = statement.indexOf(":=");
            if (colonEq != -1) {
                varName = statement.substring(0, colonEq).trim();
                statement = statement.substring(colonEq + 2);
            }

            statement = statement.trim();
            if (statement.isEmpty())
                continue;
            if (statement.startsWith("//"))
                continue;

            Expression parsed = parser.parseExpression(statement);
            if (parsed == null)
                throw new ParseException(0, "Parsed to null: " + statement);

            Object result = parsed.getValue(this);

            if (varName != null) {
                this.setVariable(varName, result);
            }
            lastResult = result;
        }

        return lastResult;
    }

    /**
     * @throws EvaluationException
     */
    public void checkSyntaxZh(String expr) {
        String encodedExpr = ChineseCodec.encode(expr);
        checkSyntax(encodedExpr);
    }

    /**
     * @throws EvaluationException
     */
    public void checkSyntax(String expr) {
        String[] statements = expr.split(";");

        SpelExpressionParser parser = new SpelExpressionParser();
        for (String statement : statements) {
            int colonEq = statement.indexOf(":=");
            if (colonEq != -1) {
                statement = statement.substring(colonEq + 1);
            }
            parser.parseExpression(expr);
        }
    }

}
