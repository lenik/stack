package com.bee32.icsf.access.shield;

import java.io.Serializable;

import javax.free.ArrayStack;
import javax.free.Stack;

public class AclOptionStack
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Stack<AclOption> stack;
    AclOption defaultAclOption = new AclOption();

    public AclOptionStack() {
        stack = new ArrayStack<>();
    }

    public AclOption getDefault() {
        return defaultAclOption;
    }

    public AclOption peek() {
        if (stack.isEmpty())
            return defaultAclOption;
        else
            return stack.top();
    }

    public AclOption enter() {
        AclOption frame = new AclOption();
        stack.push(frame);
        return frame;
    }

    public AclOption leave() {
        if (stack.isEmpty())
            throw new IllegalStateException("No acl-option frame in the stack.");
        AclOption top = stack.pop();
        return top;
    }

}
