package com.bee32.plover.test;

import org.junit.Ignore;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class PloverJUnit4Runner
        extends BlockJUnit4ClassRunner {

    public PloverJUnit4Runner(Class<?> klass)
            throws InitializationError {
        super(klass);
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        EachTestNotifier eachNotifier = makeNotifier(method, notifier);
        if (method.getAnnotation(Ignore.class) != null) {
            runIgnored(eachNotifier);
        } else {
            runNotIgnored(method, eachNotifier);
        }
    }

    private void runIgnored(EachTestNotifier eachNotifier) {
        eachNotifier.fireTestIgnored();
    }

    private void runNotIgnored(FrameworkMethod method, EachTestNotifier eachNotifier) {
        eachNotifier.fireTestStarted();
        try {
            methodBlock(method).evaluate();
        } catch (AssumptionViolatedException e) {
            eachNotifier.addFailedAssumption(e);
        } catch (Throwable e) {
            eachNotifier.addFailure(e);
        } finally {
            eachNotifier.fireTestFinished();
        }
    }

    private EachTestNotifier makeNotifier(FrameworkMethod method, RunNotifier notifier) {
        Description description = describeChild(method);
        return new EachTestNotifier(notifier, description);
    }

}
