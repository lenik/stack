package com.bee32.plover.faces;

import org.junit.Assert;
import org.junit.Test;

public class FacesExceptionTranslatorTest
        extends Assert {

    @Test
    public void testDataAccessFet() {
        Exception e = new Exception("org.hibernate.exception.ConstraintViolationException");
        String message = FetManager.translateMessage(e);
        assertEquals("记录被自动保护", message);
    }

}
