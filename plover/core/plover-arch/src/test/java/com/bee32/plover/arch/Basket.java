package com.bee32.plover.arch;

import com.bee32.plover.arch.CompositeTest.Fruit;
import com.bee32.plover.arch.CompositeTest.Item;

/**
 * A Basket
 *
 * @name Basket-1
 */
public class Basket
        extends Composite {

    public Basket() {
    }

    /**
     * An Apple
     */
    @CompositeElement
    Fruit apple = new Fruit("apple");

    /**
     * A Banana
     */
    @Item
    Fruit banana = new Fruit("banana");

    @CompositeElement
    Fruit unknown1 = new Fruit("unkonwn1");

    @CompositeElement
    Fruit unknown2 = new Fruit("unkonwn2");

    /**
     * A Pear
     */
    Fruit pear = new Fruit("pear");

    @Override
    protected boolean isFallbackEnabled() {
        return true;
    }

}
