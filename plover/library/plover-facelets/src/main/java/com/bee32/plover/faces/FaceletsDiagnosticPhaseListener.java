package com.bee32.plover.faces;

import javax.faces.context.ExternalContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.free.ExpectedException;
import javax.servlet.http.HttpServletRequest;

public class FaceletsDiagnosticPhaseListener
        implements PhaseListener {

    private static final long serialVersionUID = 1L;

    public static final String DIAG_KEY = ".diag";
    public static final String DIAG_BREAK = "break";
    public static final String DIAG_BREAK2 = "break2";

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        ExternalContext externalContext = event.getFacesContext().getExternalContext();
        Object request = externalContext.getRequest();
        if (!(request instanceof HttpServletRequest))
            return;

        HttpServletRequest req = (HttpServletRequest) request;
        String diag = req.getParameter(DIAG_KEY);
        if (diag == null)
            return;

        if (DIAG_BREAK.equals(diag))
            throw new ExpectedException("Diagnostic Breakpoint Here.");
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        ExternalContext externalContext = event.getFacesContext().getExternalContext();
        Object request = externalContext.getRequest();
        if (!(request instanceof HttpServletRequest))
            return;

        HttpServletRequest req = (HttpServletRequest) request;
        String diag = req.getParameter(DIAG_KEY);
        if (diag == null)
            return;

        if (DIAG_BREAK2.equals(diag))
            throw new ExpectedException("Diagnostic Breakpoint Here.");
    }

}
