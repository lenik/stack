package com.bee32.plover.orm.ext.basic;

import com.bee32.plover.javascript.JavascriptChunk;
import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.servlet.mvc.ActionHandler;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;

public class NotApplicableHandler
        extends ActionHandler {

    @Override
    public ActionResult handleRequest(ActionRequest req, ActionResult result)
            throws Exception {
        JavascriptChunk chunk = Javascripts.alertAndBack("Not applicable");
        chunk.dump(result.getResponse());
        return null;
    }

    public static final NotApplicableHandler INSTANCE = new NotApplicableHandler();

}
