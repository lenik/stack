package com.bee32.plover.faces.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;

import com.bee32.plover.arch.operation.Operation;
import com.bee32.plover.arch.util.SelectionHolder;
import com.bee32.plover.faces.utils.FacesAssembledContext;
import com.bee32.plover.faces.utils.FacesUILogger;
import com.bee32.plover.inject.ComponentTemplate;

@ComponentTemplate
@PerView
public abstract class ViewBean
        extends SelectionHolder
        implements Serializable, DisposableBean {

    private static final long serialVersionUID = 1L;

    protected transient FacesUILogger uiLogger = new FacesUILogger(false);
    protected transient FacesUILogger uiHtmlLogger = new FacesUILogger(true);

    protected static class ctx
            extends FacesAssembledContext {
    }

    protected static Object BEAN(String name)
            throws BeansException {
        return ctx.bean.getBean(name);
    }

    protected static <T> T BEAN(String name, Class<T> requiredType)
            throws BeansException {
        return ctx.bean.getBean(name, requiredType);
    }

    protected static <T> T BEAN(Class<T> requiredType)
            throws BeansException {
        return ctx.bean.getBean(requiredType);
    }

    public ViewBean() {
        // wire();
        createTransients();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            getMetadata().addViewBean(this);
        }
    }

    private void readObject(ObjectInputStream in)
            throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        createTransients();
    }

    public ViewMetadata getMetadata() {
        return ctx.bean.getBean(ViewMetadata.class);
    }

    protected void createTransients() {
        uiLogger = new FacesUILogger(false);
        uiHtmlLogger = new FacesUILogger(true);
    }

    @Override
    public void destroy()
            throws Exception {
        getMetadata().removeViewBean(this);
    }

    /**
     * Some JSF tags (from third-party toolkits) require listener method, well, this listener method
     * just do nothing.
     */
    @Operation
    public void dummy() {
        // System.out.println("Dummy");
    }

    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    static {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    protected <T> boolean validate(T bean) {
        Set<ConstraintViolation<T>> violations = validator.validate(bean);
        if (violations.isEmpty())
            return true;

        // Instead of uiLogger, here access the context directly to support message binding.
        FacesContext context = FacesContext.getCurrentInstance();

        for (ConstraintViolation<T> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            // System.err.println("invalid value for: '" + propertyPath + "': " + message);

            String clientId = propertyPath;

            FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            context.addMessage(clientId, fmsg);

            uiLogger.error(message);
        }
        return false;
    }

    public void validateDialog() {
        if (getSelection().isEmpty())
            uiLogger.error("???????????????????????????");
    }

    public final <T> T getOpenedObject(boolean selectionRequired) {
        T openedObject = getOpenedObject();
        if (!selectionRequired && openedObject == null) {
            uiLogger.error("?????????????????????");
            return null;
        }
        return openedObject;
    }

}
