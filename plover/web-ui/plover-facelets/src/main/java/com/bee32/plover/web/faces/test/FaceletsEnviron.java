package com.bee32.plover.web.faces.test;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Scope("request")
public class FaceletsEnviron {

    public String getVersion() {
        return "FTC-1.0";
    }

}
