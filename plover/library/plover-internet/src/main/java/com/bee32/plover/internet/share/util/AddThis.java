package com.bee32.plover.internet.share.util;

import java.io.IOException;

import javax.free.ClassResource;
import javax.free.IllegalUsageException;
import javax.free.URLResource;

public class AddThis {

    static String widgetHtml;
    static {
        URLResource htmlRes = ClassResource.classData(AddThis.class, "html");
        try {
            widgetHtml = htmlRes.forRead().readTextContents();
        } catch (IOException e) {
            throw new IllegalUsageException("Can't load widget html", e);
        }
    }

    public String getWidgetHtml() {
        return widgetHtml;
    }

}
