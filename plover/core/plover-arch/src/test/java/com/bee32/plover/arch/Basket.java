package com.bee32.plover.arch;

import com.bee32.plover.arch.CompositeTest.Fruit;
import com.bee32.plover.arch.CompositeTest.Item;

public class Basket
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
