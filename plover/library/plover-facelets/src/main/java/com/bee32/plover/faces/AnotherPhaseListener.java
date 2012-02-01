package com.bee32.plover.faces;

import java.util.Collection;

import javax.faces.component.UIViewRoot;
import javax.faces.context.PartialViewContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.primefaces.context.PrimePartialViewContext;

public class AnotherPhaseListener
        implements PhaseListener {

    private static final long serialVersionUID = 1L;

    @Override
    public void beforePhase(PhaseEvent event) {
        PhaseId phaseId = event.getPhaseId();

        UIViewRoot root = event.getFacesContext().getViewRoot();
        if (root == null) {
            System.err.println("[BEFORE] " + phaseId + " => null root");
            return;
        }
        int n = root.getChildCount();
        System.err.println("[BEFORE] " + phaseId + " => " + n);

        if (phaseId == PhaseId.RENDER_RESPONSE) {
            PartialViewContext pvc = event.getFacesContext().getPartialViewContext();
            Collection<String> renderIds = pvc.getRenderIds();
            System.err.println("[BEFORE RR] renderIds=" + renderIds);
        }
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        PhaseId phaseId = event.getPhaseId();
        UIViewRoot root = event.getFacesContext().getViewRoot();
        if (root == null) {
            System.err.println("[AFTER] " + phaseId + " => null root");
            return;
        }
        int n = root.getChildCount();
        System.err.println("[AFTER] " + phaseId + " => " + n);

        if (phaseId == PhaseId.RESTORE_VIEW) {
            PartialViewContext pvc = event.getFacesContext().getPartialViewContext();
            if (pvc instanceof PrimePartialViewContext) {
                PrimePartialViewContext ppvc = (PrimePartialViewContext) pvc;
                pvc = ppvc.getWrapped();
            }
            Collection<String> renderIds = pvc.getRenderIds();
            renderIds.add("spec");
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

}
