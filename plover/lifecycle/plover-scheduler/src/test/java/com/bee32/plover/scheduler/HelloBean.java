package com.bee32.plover.scheduler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class HelloBean {

    @Override
    public String toString() {
        return "A spring bean.";
    }

}
