package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.test.ServiceCollector;
import com.bee32.sem.process.verify.IVerifyPolicy;

public class VerifyPolicyCollector
        extends ServiceCollector<IVerifyPolicy> {

    public static void main(String[] args)
            throws IOException {
        new VerifyPolicyCollector().collect();
    }

}
