package com.bee32.plover.faces.test;

import org.primefaces.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class GlobalCounterBean {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public synchronized void increment() {
        count++;
        RequestContext.getCurrentInstance().push("counter", count);
    }

}
