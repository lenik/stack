package com.bee32.plover.web.faces.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.web.faces.utils.ComponentHelper;
import com.bee32.plover.web.faces.utils.FacesContextSupport;
import com.bee32.plover.web.faces.utils.FacesUILogger;

@ComponentTemplate
@PerView
public abstract class ViewBean
        extends FacesContextSupport
        implements Serializable, DisposableBean {

    private static final long serialVersionUID = 1L;

    protected transient FacesUILogger uiLogger = new FacesUILogger();

    public ViewBean() {
        // wire();
        create();
        getMetadata().addViewBean(this);
    }

    private void readObject(ObjectInputStream in)
            throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        create();
    }

    public ViewMetadata getMetadata() {
        return getBean(ViewMetadata.class);
    }

    /**
     * TODO Is this safe for view-bean?
     */
    @Deprecated
    protected void wire() {
        ApplicationContext context = ThreadHttpContext.requireApplicationContext();
        AutowireCapableBeanFactory factory = context.getAutowireCapableBeanFactory();
        factory.autowireBean(this);
    }

    protected void create() {
        uiLogger = new FacesUILogger();
    }

    @Override
    public void destroy()
            throws Exception {
        getMetadata().removeViewBean(this);
    }

    protected UIComponent findComponent(String expr) {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = context.getViewRoot();
        UIComponent component = viewRoot.findComponent(expr);

        if (component == null)
            throw new IllegalArgumentException("Illegal component id expr: " + expr);

        return component;
    }

    protected ComponentHelper findComponentEx(String expr) {
        UIComponent component = findComponent(expr);
        if (component == null)
            return null;

        return new ComponentHelper(component);
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

}
