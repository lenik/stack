package com.bee32.plover.arch;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bee32.plover.arch.util.res.MapProperties;

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

    public static class Basket
            extends Composite {

        public Basket() {
        }

        @CompositeElement
        Fruit apple = new Fruit("apple");

        @Item
        Fruit banana = new Fruit("banana");

        @CompositeElement
        Fruit unknown1 = new Fruit("unkonwn1");

        @CompositeElement
        Fruit unknown2 = new Fruit("unkonwn2");

        Fruit pear = new Fruit("pear");

        @Override
        protected boolean isFallbackEnabled() {
            return true;
        }

    }

    Basket basket = new Basket();

    @Before
    public void bind() {
        Properties properties = new Properties();

        properties.setProperty("name", "Basket-1");
        properties.setProperty("description", "A Basket");
        properties.setProperty("apple.description", "An Apple");
        properties.setProperty("banana.description", "A Banana");
        properties.setProperty("pear.description", "A Pear");

        basket.setProperties(new MapProperties(properties));
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
