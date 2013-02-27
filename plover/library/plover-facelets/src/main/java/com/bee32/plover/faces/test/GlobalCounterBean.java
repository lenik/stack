package com.bee32.plover.faces.test;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;
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

        PushContext pushContext = PushContextFactory.getDefault().getPushContext();
        pushContext.push("/counter", count);
    }

}
