package com.bee32.plover.faces.utils;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

public class FacesMessageReporter
        extends FacesUILogger {

    final List<FacesMessage> messages = new ArrayList<FacesMessage>();

    public FacesMessageReporter(boolean html) {
        super(html);
    }

    @Override
    protected void append(FacesMessage message) {
        messages.add(message);
    }

    public List<FacesMessage> getMessages() {
        return messages;
    }

    public void clear() {
        messages.clear();
    }

}
