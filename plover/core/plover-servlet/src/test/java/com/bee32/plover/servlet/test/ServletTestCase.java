package com.bee32.plover.servlet.test;

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
    private int socketPort = -1;

    public ServletTestCase() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Before
    public void start()
            throws Exception {

        // int port = getPort();
        // logger.debug("Set connector listen on port " + port);
        // LocalConnector connector = createLocalConnector();

        String connector = createSocketConnector(false);
        int colon = connector.lastIndexOf(':');
        String portString = connector.substring(colon + 1);
        socketPort = Integer.parseInt(portString);

        setup();
        logger.debug("Start test server: " + this);
        super.start();
        logger.debug("Test server started: " + this);
    }

    protected void setup()
            throws Exception {
    }

    @After
    public void stop()
            throws Exception {
        logger.debug("Stop test server: " + this);
        super.stop();
    }

    protected int getPort() {
        return socketPort;
    }

    protected HttpTester get(String uriOrPath)
            throws Exception {
        HttpTester request = new HttpTester();
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");

        String host = this.host;
        if (uriOrPath.startsWith("http://")) {
            uriOrPath = uriOrPath.substring("http://".length());
            int slash = uriOrPath.indexOf('/');
            if (slash == -1) {
                host = uriOrPath;
                uriOrPath = "/";
            } else {
                host = uriOrPath.substring(0, slash);
                uriOrPath = uriOrPath.substring(slash);
            }
        }

        request.setHeader("Host", host);
        request.setURI(uriOrPath);

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
