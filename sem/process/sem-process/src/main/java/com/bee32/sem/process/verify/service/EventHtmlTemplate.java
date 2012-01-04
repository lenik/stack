package com.bee32.sem.process.verify.service;

import com.bee32.plover.html.HtmlTemplate;
import com.bee32.sem.event.entity.Event;

public class EventHtmlTemplate
        extends HtmlTemplate {

    protected final Event event;

    public EventHtmlTemplate(Event event) {
        if (event == null)
            throw new NullPointerException("event");
        this.event = event;
    }

    @Override
    protected boolean isFragment() {
        return true;
    }

    @Override
    public String getTitle() {
        return event.getSubject();
    }

}
