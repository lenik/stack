package com.bee32.plover.servlet.test;

import org.eclipse.jetty.testing.HttpTester;

public class HttpException
        extends Exception {

    private static final long serialVersionUID = 1L;

    private final HttpTester tester;

    public HttpException(HttpTester tester) {
        super(tester.getStatus() + " " + tester.getReason());

        this.tester = tester;
    }

    public HttpTester getTester() {
        return tester;
    }

}
