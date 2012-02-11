package com.bee32.plover.faces.view;

import java.util.Collection;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.validation.ConstraintViolation;

import com.bee32.plover.faces.utils.FacesAssembledContext;

public class SelfValidatePhaseListener
        implements PhaseListener {

    private static final long serialVersionUID = 1L;

    static class ctx
            extends FacesAssembledContext {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.PROCESS_VALIDATIONS;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ViewMetadata metadata = ctx.bean.getBean(ViewMetadata.class);
        Collection<?> viewBeans = metadata.getViewBeans();
        boolean failed = false;
        for (Object viewBean : viewBeans) {
            if (viewBean instanceof IValidatable) {
                IValidatable validatable = (IValidatable) viewBean;
                Set<ConstraintViolation<?>> violations = validatable.validate();
                for (ConstraintViolation<?> violation : violations) {
                    String _message = violation.getMessage();
                    FacesMessage message = new FacesMessage(_message);
                    facesContext.addMessage(null, message);
                    failed = true;
                }
            }
        }
        if (failed)
            facesContext.validationFailed();
    }

}
