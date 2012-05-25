package com.bee32.plover.arch;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CompositeTest
        extends Assert {

    public static class Fruit
            extends Component {

        public Fruit(String name) {
            super(name);
        }

    }

    @Retention(RetentionPolicy.RUNTIME)
    @CompositeElement
    public @interface Item {
    }

    Basket basket = new Basket();

    @Before
    public void bind() {
        basket.assembleOnce();
    }

    @Test
    public void testSelfAnnotation() {
        assertEquals("A Basket", //
                basket.getAppearance().getDescription());
    }

    @Test
    public void testMetaAnnotation() {
        assertEquals("An Apple", //
                basket.apple.getAppearance().getDescription());
    }

    @Test
    public void testConcreteAnnotation() {
        assertEquals("A Banana", //
                basket.banana.getAppearance().getDescription());
    }

    @Test
    // (expected = MissingResourceException.class)
    public void testNoAnnotation() {
        assertNull(basket.pear.getAppearance().getDescription());
    }

}
