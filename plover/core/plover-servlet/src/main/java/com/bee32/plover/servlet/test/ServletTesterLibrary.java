package com.bee32.plover.servlet.test;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;

import javax.free.StringPart;
import javax.servlet.http.HttpServlet;

import org.junit.After;
import org.junit.Before;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.ISupportLibrary;

public class ServletTesterLibrary
        extends ServletTester
        implements ISupportLibrary {

    protected final Logger logger;

    private String host = "localhost";
    private int socketPort = -1;

    private Class<?> baseClass;
    private String rootResourceFile = "index.html";

    public ServletTesterLibrary() {
        this(null);
    }

    public ServletTesterLibrary(Class<?> baseClass) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.baseClass = baseClass == null ? getClass() : baseClass;
    }

    public String getRootResourceFile() {
        return rootResourceFile;
    }

    public void setRootResourceFile(String rootResourceFile) {
        this.rootResourceFile = rootResourceFile;
    }

    @Before
    @Override
    public void startup()
            throws Exception {

        // int port = getPort();
        // logger.debug("Set connector listen on port " + port);
        // LocalConnector connector = createLocalConnector();

        String connector = createSocketConnector(false);
        int colon = connector.lastIndexOf(':');
        String portString = connector.substring(colon + 1);
        socketPort = Integer.parseInt(portString);

        configureBuiltinServlets();
        configureServlets();

        // Find the resource base.
        if (rootResourceFile != null) {
            URL indexResource = null;
            Class<?> chain = baseClass;
            while (chain != null) {
                indexResource = chain.getResource(rootResourceFile);
                if (indexResource != null)
                    break;
                chain = chain.getSuperclass();
            }
            if (indexResource != null) {
                String fileUrl = indexResource.toString();

                int shrink = fileUrl.length() - rootResourceFile.length();
                String resourceBase = fileUrl.substring(0, shrink);

                // remove the trailing /*.
                resourceBase = StringPart.beforeLast(resourceBase, '/');

                setResourceBase(resourceBase);
            }
        }

        logger.debug("Start test server: " + this);
        start();
        logger.debug("Test server started: " + this);
    }

    @After
    @Override
    public void shutdown()
            throws Exception {
        logger.debug("Stop test server: " + this);
        stop();
    }

    protected void configureBuiltinServlets()
            throws Exception {

        addFilter(RequestLogger.class, "*", 0);

        addServlet(Favicon.class, "/favicon.ico");

        addFilter(Welcome.class, "/", 0);
        addServlet(Logo.class, "/logo/*");
    }

    /**
     * Config servlet, filter, etc.
     * <p>
     * Don't call {@link #start()} here.
     */
    protected void configureServlets()
            throws Exception {
    }

    public ServletHolder addServlet(String servletName, Class<? extends HttpServlet> servlet, String pathSpec) {
        if (servletName == null)
            throw new NullPointerException("servletName");

        Context context = getContext();
        ServletHandler servletHandler = context.getServletHandler();

        ServletHolder holder = new ServletHolder(servlet);

        holder.setName(servletName);

        servletHandler.addServletWithMapping(holder, pathSpec);
        return holder;
    }

    public int getPort() {
        return socketPort;
    }

    public HttpTester httpGet(String uriOrPath)
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

        if (response.getStatus() >= 400)
            throw new HttpException(response);

        return response;
    }

    public HttpTester httpPost(String uri, String content, Map<String, String> map)
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

    public void dumpResponse(HttpTester http) {
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

    public void browse()
            throws IOException {
        browse("");
    }

    public void browse(String location)
            throws IOException {

        String root = "http://localhost:" + this.getPort();
        URL rootUrl = new URL(root);

        URL url = new URL(rootUrl, location);
        logger.warn("Browse " + url);

        URI uri;
        try {
            uri = url.toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        Desktop.getDesktop().browse(uri);
    }

    public void browseAndWait()
            throws IOException {
        browse();
        readLine();
    }

    public void browseAndWait(String location)
            throws IOException {
        browse(location);
        readLine();
    }

    String readLine()
            throws IOException {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        return stdin.readLine();
    }

}
