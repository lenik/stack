package com.bee32.plover.faces.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

import com.bee32.plover.arch.operation.Operation;
import com.bee32.plover.arch.util.ISelection;
import com.bee32.plover.faces.utils.ComponentHelper;
import com.bee32.plover.faces.utils.FacesContextSupport;
import com.bee32.plover.faces.utils.FacesUILogger;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.servlet.util.ThreadHttpContext;

@ComponentTemplate
@PerView
public abstract class ViewBean
        extends FacesContextSupport
        implements ISelection, Serializable, DisposableBean {

    private static final long serialVersionUID = 1L;

    protected transient FacesUILogger uiLogger = new FacesUILogger(false);
    protected transient FacesUILogger uiHtmlLogger = new FacesUILogger(true);

    public ViewBean() {
        // wire();
        createTransients();
        getMetadata().addViewBean(this);
    }

    private void readObject(ObjectInputStream in)
            throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        createTransients();
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

    protected void createTransients() {
        uiLogger = new FacesUILogger(false);
        uiHtmlLogger = new FacesUILogger(true);
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

    // selection/openedObjects

    List<?> selection = new ArrayList<Object>();
    List<?> openedObjects = new ArrayList<Object>();

    @Override
    public List<?> getSelection() {
        return selection;
    }

    public final List<?> getSelection(Class<?>... interfaces) {
        List<?> selection = getSelection();
        if (interfaces.length == 0)
            return selection;
        List<Object> interestings = new ArrayList<Object>();
        for (Object item : selection) {
            boolean interesting = true;
            if (item == null)
                continue;
            for (Class<?> iface : interfaces)
                if (!iface.isInstance(item)) {
                    interesting = false;
                    break;
                }
            if (interesting)
                interestings.add(item);
        }
        return interestings;
    }

    public void setSelection(List<?> selection) {
        if (selection == null)
            selection = new ArrayList<Object>();
        this.selection = selection;
    }

    public final Object getSingleSelection() {
        List<?> selection = getSelection();
        if (selection.isEmpty())
            return null;
        else
            return selection.get(0);
    }

    public final void setSingleSelection(Object singleSelection) {
        List<Object> list = new ArrayList<Object>();
        if (singleSelection != null)
            list.add(singleSelection);
        setSelection(list);
    }

    public final Object[] getSelectionArray() {
        Object[] array = selection.toArray();
        return array;
    }

    public final void setSelectionArray(Object... selectionArray) {
        List<Object> list = new ArrayList<Object>(selectionArray.length);
        for (Object item : selectionArray)
            list.add(item);
        setSelection(list);
    }

    public final boolean isSelected() {
        return !getSelection().isEmpty();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getOpenedObjects() {
        return ((List<T>) openedObjects);
    }

    public void setOpenedObjects(List<?> openedObjects) {
        if (openedObjects == null)
            openedObjects = Collections.emptyList();
        this.openedObjects = openedObjects;
    }

    public final <T> T getOpenedObject(boolean selectionRequired) {
        T openedObject = getOpenedObject();
        if (!selectionRequired && openedObject == null) {
            uiLogger.error("请先选择对象。");
            return null;
        }
        return openedObject;
    }

    public final <T> T getOpenedObject() {
        List<?> objects = getOpenedObjects();
        if (objects.isEmpty())
            return null;
        T first = (T) objects.get(0);
        return first;
    }

    public final void setOpenedObject(Object openedObject) {
        List<?> nonNulls = listOfNonNulls(openedObject);
        setOpenedObjects(nonNulls);
    }

    // Utils
    @SafeVarargs
    protected static <T> List<T> listOf(T... selection) {
        return Arrays.asList(selection);
    }

    @SafeVarargs
    protected static <T> List<T> listOfNonNulls(T... selection) {
        List<T> list = new ArrayList<T>(selection.length);
        for (T item : selection)
            if (item != null)
                list.add(item);
        return Collections.unmodifiableList(list);
    }

}
