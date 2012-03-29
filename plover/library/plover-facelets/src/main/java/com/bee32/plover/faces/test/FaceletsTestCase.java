package com.bee32.plover.faces.test;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.faces.FaceletsConfig;
import com.bee32.plover.servlet.rabbits.OverlappedBases;
import com.bee32.plover.servlet.test.WiredServletTestCase;

public class FaceletsTestCase
        extends WiredServletTestCase {

    static final FaceletsProvider faceletsProvider = FaceletsProvider.APACHE_MYFACES;

    static Logger logger = LoggerFactory.getLogger(FaceletsTestCase.class);

    List<String> taglibs = new ArrayList<String>();

    public FaceletsTestCase() {
        OverlappedBases.add("resources/");
        // OverlappedBases.add("META-INF/");
        stl.welcomeList.add("index-ftc." + FaceletsConfig.extension);
        stl.welcomeList.add("index." + FaceletsConfig.extension);
    }

    /**
     * Please call this method in constructore, or configContext.
     *
     * @deprecated It's recommendded to export taglib by declare taglib.xml files under META-INF/.
     */
    protected final void addTagLibrary(String taglib) {
        taglibs.add(taglib);
    }

    protected boolean isDebugMode() {
        return true;
    }

    protected boolean isStrictMode() {
        return false;
    }

    protected int getRefreshPeriod() {
        return 300;
    }

}