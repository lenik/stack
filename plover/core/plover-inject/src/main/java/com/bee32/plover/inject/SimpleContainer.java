package com.bee32.plover.inject;

import java.util.ArrayList;
import java.util.List;

public class SimpleContainer
        extends AbstractContainer {

    private List<Object> stack;
    private int stackPointer;

    public SimpleContainer() {
        super("SimpleContainer");
        stack = new ArrayList<Object>();
        stackPointer = -1;
    }

    /**
     * XXX - synchronized is just not enough.
     */
    public synchronized void enter() {
        stack.add(null);
        stackPointer++;
    }

    /**
     * XXX - synchronized is just not enough.
     */
    public synchronized void leave() {
        if (stack.isEmpty())
            throw new StackUnderflowException();
        stack.remove(stackPointer--);
    }

    @Override
    public synchronized Object getFrame() {
        return stack.get(stackPointer);
    }

    @Override
    public synchronized void setFrame(Object frameObject) {
        stack.set(stackPointer, frameObject);
    }

}
