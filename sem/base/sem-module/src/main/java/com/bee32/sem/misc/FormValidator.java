package com.bee32.sem.misc;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.bee32.plover.faces.utils.FacesMessageReporter;

public abstract class FormValidator
        implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {
        FacesMessageReporter reporter = new FacesMessageReporter(false);
        validate(reporter, component);
        List<FacesMessage> messages = reporter.getMessages();
        if (messages != null)
            throw new ValidatorException(messages);
    }

    protected abstract void validate(FacesMessageReporter reporter, UIComponent component);

}
