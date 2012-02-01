package com.bee32.plover.faces.test;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class FaceletsEnviron {

    public String getVersion() {
        return "FTC-1.0";
    }

}
