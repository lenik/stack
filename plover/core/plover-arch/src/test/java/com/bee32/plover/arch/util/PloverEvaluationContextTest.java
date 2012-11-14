package com.bee32.plover.arch.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.bee32.plover.arch.util.PloverEvaluationContext;

public class PloverEvaluationContextTest {

    @Test
    public void testSimple() {
        PloverEvaluationContext context = new PloverEvaluationContext();
        Object result = context.evaluate("1+2");
        assertEquals(3, result);
    }

    @Test
    public void testSetVar() {
        PloverEvaluationContext context = new PloverEvaluationContext();
        Object result = context.evaluate("a:=1; b:=2; #a+#b");
        assertEquals(3, result);
    }

    @Test
    public void testSetVarWithTrailingSemicolon() {
        PloverEvaluationContext context = new PloverEvaluationContext();
        Object result = context.evaluate("a:=1; b:=2; #a+#b;");
        assertEquals(3, result);
    }

    @Test
    public void testSetVarWithExtraSpaces() {
        PloverEvaluationContext context = new PloverEvaluationContext();
        Object result = context.evaluate(" a:=    1; b  := 2    ;;;; #a+#b; ");
        assertEquals(3, result);
    }

}
