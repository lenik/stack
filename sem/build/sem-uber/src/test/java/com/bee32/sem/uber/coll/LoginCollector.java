package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.icsf.login.ILoginListener;
import com.bee32.plover.test.ServiceCollector;

public class LoginCollector
        extends ServiceCollector<ILoginListener> {

    public static void main(String[] args)
            throws IOException {
        new LoginCollector().collect();
    }

}
