package com.bee32.plover.javascript.util;

import org.springframework.web.util.HtmlUtils;

import com.bee32.plover.javascript.JavascriptChunk;

public class Javascripts {

    public static JavascriptChunk alertAndBack(String message) {
        JavascriptChunk chunk = new JavascriptChunk();
        chunk.println("alert('" + HtmlUtils.htmlEscape(message) + "'); ");
        chunk.println("history.back(); ");
        return chunk;
    }

}
