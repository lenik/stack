package com.bee32.sem.frame.ui;

import java.util.EventObject;

public class SelectionEvent
        extends EventObject {

    private static final long serialVersionUID = 1L;

    final Object selection;

    public SelectionEvent(Object source, Object selection) {
        super(source);
        this.selection = selection;
    }

    public Object getSelection() {
        return selection;
    }

}
