package com.bee32.plover.faces;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.faces.ErrorMessageTranslator;

public class ErrorMessageTranslatorTest
        extends Assert {

    @Test
    public void testDaoEmt() {
        String message = ErrorMessageTranslator.translateMessage(//
                "org.hibernate.exception.ConstraintViolationException");
        assertEquals("记录被自动保护", message);
    }

}
