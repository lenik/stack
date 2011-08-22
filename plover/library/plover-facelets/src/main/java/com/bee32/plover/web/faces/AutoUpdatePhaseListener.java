package com.bee32.plover.web.faces;

import javax.faces.context.PartialViewContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoUpdatePhaseListener
        implements PhaseListener {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(AutoUpdatePhaseListener.class);

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        logger.info("Adding __status to be rendered");
        PartialViewContext pvc = event.getFacesContext().getPartialViewContext();
        pvc.getRenderIds().add("__status");
    }

    @Override
    public void afterPhase(PhaseEvent event) {
    }

}
