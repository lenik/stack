package com.bee32.plover.model;

import javax.free.IllegalUsageException;
import javax.servlet.ServletException;

import com.bee32.plover.model.stage.ModelStage;
import com.bee32.plover.model.stage.ModelStageException;
import com.bee32.plover.restful.IRESTfulRequest;
import com.bee32.plover.restful.IRESTfulResponse;
import com.bee32.plover.servlet.context.ServletContainer;

public class ModelController {

    public void read(IRESTfulRequest req, IRESTfulResponse resp)
            throws ServletException {

        Object target = req.getTarget();

        if (!(target instanceof IModel))
            throw new IllegalUsageException("Arrival target isn't a Model");

        IModel model = (IModel) target;

        ServletContainer container = new ServletContainer(req, resp);

        ModelStage stage = new ModelStage(container);
        // ... stage.setView(tq.rest()); ...
        try {
            model.stage(stage);
        } catch (ModelStageException e) {
            throw new ServletException(e.getMessage(), e);
        }

        // stage.getElements() -> Tree-Convert...
        // display...
    }

}
