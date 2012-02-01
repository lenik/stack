package com.bee32.plover.faces.test;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class LongViewBean
        implements Serializable {

    private static final long serialVersionUID = 1L;

    String foo = "FoOo";
    String bar = "Barr";

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

}
