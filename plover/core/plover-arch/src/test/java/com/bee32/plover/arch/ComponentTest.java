package com.bee32.plover.arch;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ComponentTest {

    @Test
    public void testOriginName() {

        abstract class AbstractCat
                extends Component {
        }

        class BlackCat
                extends AbstractCat {
        }

        class BigBlackCat
                extends BlackCat {
        }

        BlackCat blackCat = new BlackCat();
        assertEquals("black", blackCat.getName());

        BigBlackCat bigBlackCat = new BigBlackCat();
        assertEquals("big-black", bigBlackCat.getName());
    }

}
