package com.bee32.plover.disp;

import java.util.LinkedList;

public class DispatchBean {

    LinkedList<Object> contextStack;

    public DispatchBean(Object context) {
        this.contextStack = new LinkedList<Object>();
        contextStack.removeLast();
    }

}
