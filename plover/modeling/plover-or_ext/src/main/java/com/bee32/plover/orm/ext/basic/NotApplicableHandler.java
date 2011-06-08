package com.bee32.plover.orm.ext.basic;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.javascript.JavascriptChunk;
import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.servlet.mvc.ActionHandler;
import com.bee32.plover.servlet.mvc.ResultView;

public class NotApplicableHandler
        extends ActionHandler {

    @Override
    public ResultView handleRequest(HttpServletRequest req, ResultView view)
            throws Exception {
        JavascriptChunk chunk = Javascripts.alertAndBack("Not applicable");
        chunk.dump(req, view);
        return null;
    }

}
