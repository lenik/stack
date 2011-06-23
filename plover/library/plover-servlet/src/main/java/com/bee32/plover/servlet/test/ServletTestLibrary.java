package com.bee32.plover.servlet.test;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.free.IllegalUsageException;
import javax.free.StringPart;
import javax.servlet.http.HttpServlet;

import org.junit.After;
import org.junit.Before;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.testing.HttpTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.ISupportLibrary;
import com.bee32.plover.servlet.util.ThreadServletContextFilter;

public class ServletTestLibrary
        extends RabbitServer
        implements ISupportLibrary {

    protected final Logger logger;

    private String host = "localhost";
    private int actualPort = -1;

    private Class<?> baseClass;
    private String[] hintFilenames = { "WEB-INF/web.xml", "index.html" };

    private boolean initialized;

    public ServletTestLibrary() {
        this(null);
    }

    public ServletTestLibrary(Class<?> baseClass) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.baseClass = baseClass == null ? getClass() : baseClass;
    }

    @Before
    @Override
    public void startup()
            throws Exception {

        initialize();

        // int port = getPort();
        // logger.debug("Set connector listen on port " + port);
        // LocalConnector connector = createLocalConnector();

        String connector = createSocketConnector(false);
        int colon = connector.lastIndexOf(':');
        String portString = connector.substring(colon + 1);
        actualPort = Integer.parseInt(portString);

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

    protected synchronized void initialize() {
        if (initialized)
            return;

        configureContext();
        configureBuiltinServlets();
        configureServlets();
        configureFallbackServlets();

        // Find the resource base.
        if (hintFilenames != null) {

            String resourceRoot = null;

            Class<?> chain = baseClass;
            while (chain != null) {
                resourceRoot = searchResourceRoot(chain);

                if (resourceRoot != null)
                    break;

                chain = chain.getSuperclass();
            }

            if (resourceRoot == null)
                throw new IllegalUsageException("Can't find resource root for " + baseClass);

            /**
             * setResourceBase only affects DefaultServlet.
             *
             * [However, it's used to get WEB-INF/web.xml, too.]
             *
             * NOTE: Using RabbitServer to override the resource resolver.
             */
            getServletContext().setResourceBase(resourceRoot);
        }

        initialized = true;
    }

    public String[] getHintRoots() {
        return hintFilenames;
    }

    public void setHintRoots(String... hintRoots) {
        this.hintFilenames = hintRoots;
    }

    String searchResourceRoot(Class<?> clazz) {
        URL hintResourceURL = null;

        for (String hintFilename : hintFilenames) {

            hintResourceURL = clazz.getResource(hintFilename);

            if (hintResourceURL != null) {
                String hintResourcePath = hintResourceURL.toString();

                int shrink = hintResourcePath.length() - hintFilename.length();
                String hintResourceRoot = hintResourcePath.substring(0, shrink);

                // remove the trailing /*.
                hintResourceRoot = StringPart.beforeLast(hintResourceRoot, '/');
                return hintResourceRoot;
            }
        }

        return null;
    }

    protected void configureContext() {
        List<String> list = new ArrayList<String>();

        String[] welcomeFiles = getServletContext().getWelcomeFiles();
        if (welcomeFiles != null)
            for (String welcomeFile : welcomeFiles)
                list.add(welcomeFile);

        if (!list.contains("index.html"))
            list.add(0, "index.html");

        welcomeFiles = list.toArray(new String[0]);
        getServletContext().setWelcomeFiles(welcomeFiles);
    }

    protected void configureBuiltinServlets() {

        addFilter(RequestLogger.class, "*", 0);

        addServlet(Favicon.class, "/favicon.ico");

        addFilter(Welcome.class, "/", 0);
        addServlet(Logo.class, "/logo/*");

        // Use filter to get the reponse object.
        // addEventListener(new ThreadServletContextListener());
        addFilter(ThreadServletContextFilter.class, "/*", 0);
    }

    /**
     * Config servlet, filter, etc.
     * <p>
     * Don't call {@link #start()} here.
     */
    protected void configureServlets() {
    }

    protected void configureFallbackServlets() {
        // Add the fallback-servlet.
        // Otherwise, the filter won't work.
        addServlet(OverlappedResourceServlet.class, "/");
    }

    public ServletHolder addServlet(String servletName, Class<? extends HttpServlet> servlet, String pathSpec) {
        if (servletName == null)
            throw new NullPointerException("servletName");

        RabbitServletContext servletManager = getServletContext();
        ServletHandler servletHandler = servletManager.getServletHandler();

        ServletHolder holder = new ServletHolder(servlet);

        holder.setName(servletName);

        servletHandler.addServletWithMapping(holder, pathSpec);
        return holder;
    }

    public int getPort() {
        return actualPort;
    }

    // Delegates.

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

        Enumeration<String> names = http.getHeaderNames();

        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String header = http.getHeader(name);
            out.println(name + ": " + header);
        }

        out.println();

        out.println(http.getContent());
    }

    static final String DEFAULT_LOCATION = "";

    protected URL getURL(String location)
            throws IOException {
        String urlString;
        if (location.contains("://"))
            urlString = location;
        else {
            String contextPath = getServletContext().getContextPath();
            String root = "http://localhost:" + actualPort + contextPath;
            if (!location.startsWith("/"))
                location = "/" + location;
            urlString = root + location;
        }

        URL url = new URL(urlString);
        return url;
    }

    static enum Command {
        HELP, SHOW_LOCATION, BROWSE, QUIT
    }

    static Map<String, Command> commands = new TreeMap<String, Command>();
    static {
        commands.put("", Command.HELP);
        commands.put("h", Command.HELP);
        commands.put("?", Command.HELP);
        commands.put("l", Command.SHOW_LOCATION);
        commands.put("b", Command.BROWSE);
        commands.put("q", Command.QUIT);
    }

    public void mainLoop()
            throws IOException {
        mainLoop("/");
    }

    public void mainLoop(String location)
            throws IOException {
        URL url = getURL(location);

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String line = stdin.readLine();
            if (line == null)
                break;

            Command command = commands.get(line.trim());
            if (command == null) {
                System.err.println("No such command: " + line);
                continue;
            }

            switch (command) {
            case SHOW_LOCATION:
                System.out.println("<<URL>> :: " + url);
                System.out.println("<<WebApp>> :: " + location);
                break;

            case BROWSE:
                browse(location);
                break;

            case HELP:
                System.out.println("Shortcuts: ");
                for (Entry<String, Command> cmd : commands.entrySet()) {
                    String key = cmd.getKey();
                    if (key.isEmpty())
                        key = "(empty)";
                    System.out.println("    " + key + ": " + cmd.getValue());
                }
                break;

            case QUIT:
                Thread[] threads = new Thread[Thread.activeCount()];
                int nthreads = Thread.enumerate(threads);
                for (int i = 0; i < nthreads; i++) {
                    Thread th = threads[i];
                    if (th.isDaemon())
                        continue;

                    String threadName = th.getName();
                    if (threadName.equals("main"))
                        continue;

                    // otherwise the application won't quit.
                    th.stop();
                }
                return;
            }
        }
    }

    public void browse()
            throws IOException {
        browse(DEFAULT_LOCATION);
    }

    public URL browse(String location)
            throws IOException {
        URL url = getURL(location);
        logger.warn("Browse " + url);

        URI uri;
        try {
            uri = url.toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        Desktop.getDesktop().browse(uri);

        return url;
    }

    public void browseAndWait()
            throws IOException {
        browseAndWait(DEFAULT_LOCATION);
    }

    public void browseAndWait(String location)
            throws IOException {
        browse(location);
        mainLoop(location);
    }

}
