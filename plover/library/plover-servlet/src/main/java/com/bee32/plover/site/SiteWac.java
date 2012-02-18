package com.bee32.plover.site;

import com.bee32.plover.servlet.peripheral.PloverSrlMultiplexer;
import com.bee32.plover.servlet.test.AbstractWac;
import com.bee32.plover.servlet.test.ServletTestLibrary;

public class SiteWac
        extends AbstractWac {

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * @see PloverSrlMultiplexer
     */
    @Override
    public void configureContext(ServletTestLibrary stl) {
        // This is already added in srl-multiplexer.
        // stl.addEventListener(new StartAndStatsSrl());
    }

}
