package com.bee32.plover.faces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.context.PartialViewContextWrapper;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoUpdatePhaseListener
        implements PhaseListener {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(AutoUpdatePhaseListener.class);

    Set<String> staticIds = new TreeSet<String>();
    List<FacesVolatileFragments> fvfs = new ArrayList<FacesVolatileFragments>();

    public AutoUpdatePhaseListener() {
        for (FacesVolatileFragments fvf : ServiceLoader.load(FacesVolatileFragments.class)) {
            if (fvf.isStatic()) {
                List<String> ids = fvf.getFragmentIds(null);
                if (ids == null)
                    throw new NullPointerException("ids from " + fvf);
                staticIds.addAll(ids);
            } else {
                fvfs.add(fvf);
            }
        }
    }

    /**
     * Hook on the phase of restore-view, so one can query on the view root.
     */
    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        PartialViewContext pvc = facesContext.getPartialViewContext();

        while (pvc instanceof PartialViewContextWrapper) {
            PartialViewContextWrapper wrapper = (PartialViewContextWrapper) pvc;
            pvc = wrapper.getWrapped();
        }

        Collection<String> renderIds = pvc.getRenderIds();
        renderIds.addAll(staticIds);

        for (FacesVolatileFragments fvf : fvfs) {
            List<String> ids = fvf.getFragmentIds(facesContext);
            if (ids == null)
                continue;

            for (String id : ids) {
                // No duplicates.
                if (!renderIds.contains(id))
                    renderIds.add(id);
            }
        }
    }

    @Override
    public void afterPhase(PhaseEvent event) {
    }

}
