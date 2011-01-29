package com.bee32.plover.test.servlet;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ServletTestCase
        extends ServletTester {

    protected final Logger logger;

    private String host = "localhost";

    public ServletTestCase() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Before
    public void start()
            throws Exception {
        logger.debug("Start test server: " + this);
        super.start();
        logger.debug("Test server started: " + this);
    }

    @After
    public void stop()
            throws Exception {
        logger.debug("Stop test server: " + this);
        super.stop();
    }

    protected HttpTester get(String uri)
            throws Exception {
        HttpTester request = new HttpTester();
        request.setMethod("GET");
        request.setHeader("Host", host);
        request.setVersion("HTTP/1.0");
        request.setURI(uri);

        String rawRequest = request.generate();

        String rawResponse = getResponses(rawRequest);
        HttpTester response = new HttpTester();
        response.parse(rawResponse);
        return response;
    }

    protected HttpTester post(String uri, String content, Map<String, String> map)
            throws Exception {
        HttpTester request = new HttpTester();
        request.setMethod("POST");
        request.setHeader("Host", host);
        request.setVersion("HTTP/1.0");
        request.setURI(uri);
        request.setContent(content);

        String rawResponse = getResponses(request.generate());
        HttpTester response = new HttpTester();
        response.parse(rawResponse);
        return response;
    }

    protected void dumpResponse(HttpTester http) {
        PrintStream out = System.err;
        out.println(http.getStatus() + " " + http.getReason());

        @SuppressWarnings("unchecked")
        Enumeration<String> names = http.getHeaderNames();

        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String header = http.getHeader(name);
            out.println(name + ": " + header);
        }

        out.println();

        out.println(http.getContent());
    }

}
