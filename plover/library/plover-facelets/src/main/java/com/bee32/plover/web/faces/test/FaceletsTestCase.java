package com.bee32.plover.web.faces.test;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.servlet.rabbits.OverlappedBases;
import com.bee32.plover.servlet.test.WiredServletTestCase;
import com.bee32.plover.web.faces.FaceletsConfig;

public class FaceletsTestCase
        extends WiredServletTestCase {

    static final FaceletsProvider faceletsProvider = FaceletsProvider.APACHE_MYFACES;

    static Logger logger = LoggerFactory.getLogger(FaceletsTestCase.class);

    List<String> taglibs = new ArrayList<String>();

    public FaceletsTestCase() {
        OverlappedBases.add("resources/");
        stl.welcomeList.add("index-ftc." + FaceletsConfig.extension);
        stl.welcomeList.add("index." + FaceletsConfig.extension);
    }

    /**
     * Please call this method in constructore, or configContext.
     *
     * TODO META-INF taglib.xml isn't read by myfaces-impl?
     *
     * It's recommendded to export taglib by declare taglib.xml files under META-INF/.
     */
    protected final void addTagLibrary(String taglib) {
        taglibs.add(taglib);
    }

    protected boolean isDebugMode() {
        return false;
    }

    protected boolean isStrictMode() {
        return false;
    }

    protected int getRefreshPeriod() {
        return 300;
    }

}