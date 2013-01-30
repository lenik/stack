package com.bee32.plover.servlet.test;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.BindException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.free.IIndentedOut;
import javax.free.IllegalUsageException;
import javax.free.IndentedOutImpl;
import javax.free.Stdio;
import javax.free.StringPart;
import javax.free.SystemProperties;
import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;
import org.eclipse.jetty.testing.HttpTester;
import org.eclipse.jetty.util.LazyList;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.ISupportLibrary;
import com.bee32.plover.servlet.rabbits.RabbitServer;
import com.bee32.plover.servlet.rabbits.RabbitServletContextHandler;

public class ServletTestLibrary
        extends RabbitServer
        implements ISupportLibrary {

    protected final Logger logger;

    private String host = "localhost";
    private int actualPort = -1;

    private Class<?> baseClass;
    private String[] hintFilenames = { "WEB-INF/web.xml", "index.html" };

    private boolean initialized;

    WebAppConfigure wac = WebAppConfigure.getInstance();

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

        String connector;
        try {
            connector = createSocketConnector(false);
        } catch (BindException e) {
            System.err.println("Can't bind to port: " + getPort());
            e.printStackTrace();
            return;
        }

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
        try {
            stop();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public synchronized void initialize() {
        if (initialized)
            return;

        wac.configureContext(this);
        wac.configureServlets(this);

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
            getServletContextHandler().setResourceBase(resourceRoot);
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

    public final List<String> welcomeList = new ArrayList<String>();
    {
        welcomeList.add("index.html");
    }

    public ServletHolder addServlet(String servletName, Class<? extends HttpServlet> servletClass, String... pathSpecs) {
        if (servletName == null)
            throw new NullPointerException("servletName");

        RabbitServletContextHandler servletManager = getServletContextHandler();
        ServletHandler servletHandler = servletManager.getServletHandler();

        ServletHolder holder = new ServletHolder(servletClass);

        holder.setName(servletName);

        String firstPathSpec = null;
        if (pathSpecs.length != 0)
            firstPathSpec = pathSpecs[0];
        servletHandler.addServletWithMapping(holder, firstPathSpec);

        for (int i = 1; i < pathSpecs.length; i++) {
            ServletMapping mapping = new ServletMapping();
            mapping.setServletName(servletName);
            mapping.setPathSpec(pathSpecs[i]);
            servletHandler.setServletMappings((ServletMapping[]) LazyList.addToArray(
                    servletHandler.getServletMappings(), mapping, ServletMapping.class));
        }

        return holder;
    }

    @Override
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

    protected String getLocalHost() {
        return "localhost";
    }

    protected URL getURL(String location)
            throws IOException {
        String urlString;
        if (location.contains("://"))
            urlString = location;
        else {
            String contextPath = getServletContextHandler().getContextPath();
            String root = "http://" + getLocalHost() + ":" + actualPort + contextPath;
            if (!location.startsWith("/"))
                location = "/" + location;
            urlString = root + location;
        }

        URL url = new URL(urlString);
        return url;
    }

    static enum Command {
        HELP, SHOW_LOCATION, BROWSE, QUIT_SAFE, QUIT
    }

    static Map<String, Command> commands = new TreeMap<String, Command>();
    static {
        commands.put("", Command.HELP);
        commands.put("h", Command.HELP);
        commands.put("?", Command.HELP);
        commands.put("l", Command.SHOW_LOCATION);
        commands.put("b", Command.BROWSE);
        commands.put("q", Command.QUIT_SAFE);
        commands.put("qq", Command.QUIT);
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

            case QUIT_SAFE:
                QuitListeners.getInstance().quit();
                break;

            case QUIT:
                try {
                    shutdown();
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
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
        logger.info("Browse " + url);

        URI uri;
        try {
            uri = url.toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        try {
            Desktop.getDesktop().browse(uri);
        } catch (UnsupportedOperationException e) {
            String[] browsers = { "gnome-open", "google-chrome", "firefox", "iexplore" };
            String browser = null;
            b: for (String br : browsers) {
                for (String dir : System.getenv("PATH").split(SystemProperties.getPathSeparator())) {
                    if (new File(dir, br).canExecute()) {
                        browser = br;
                        break b;
                    }
                }
            }
            if (browser == null)
                throw new RuntimeException("No available browser.");
            logger.debug("Launch browser: " + browser + " for " + uri);
            Runtime.getRuntime().exec(new String[] { browser, uri.toString() }, null);
        }

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

    public void dumpXML() {
        IIndentedOut out = new IndentedOutImpl(Stdio.cout);
        out.enter();
        dumpXML(out);
        out.leave();
    }

    public void dumpXML(IIndentedOut out) {
        RabbitServletContextHandler servletContextHandler = getServletContextHandler();
        ServletHandler servletHandler = servletContextHandler.getServletHandler();

        out.println("<context-params>");
        dumpInitParameters(out, servletContextHandler.getInitParams());
        out.println("</context-params>");
        out.println();

        for (EventListener eventListener : servletContextHandler.getEventListeners()) {
            out.println("<event-listener>" + eventListener.getClass().getName() + "</event-listener>");
        }
        out.println();

        for (FilterHolder filterHolder : servletHandler.getFilters()) {
            out.println("<filter>");
            out.println("    <filter-name>" + filterHolder.getName() + "</filter-name>");
            out.println("    <filter-class>" + filterHolder.getClassName() + "</filter-class>");
            dumpInitParameters(out, filterHolder.getInitParameters());
            out.println("</filter>");
        }
        out.println();

        for (FilterMapping mapping : servletHandler.getFilterMappings()) {
            String[] pathSpecs = mapping.getPathSpecs();
            if (pathSpecs.length == 0)
                throw new IllegalUsageException("No mapping path specified for filter " + mapping.getFilterName());
            for (String pathSpec : pathSpecs) {
                out.println("<filter-mapping>");
                out.println("    <filter-name>" + mapping.getFilterName() + "</filter-name>");
                out.println("    <url-pattern>" + pathSpec + "</url-pattern>");
                out.println("</filter-mapping>");
            }
        }
        out.println();

        for (ServletHolder servletHolder : servletHandler.getServlets()) {
            out.println("<servlet>");
            out.println("    <servlet-name>" + servletHolder.getName() + "</servlet-name>");

            if (servletHolder.getDisplayName() != null)
                out.println("    <display-name>" + servletHolder.getDisplayName() + "</display-name>");

            out.println("    <servlet-class>" + servletHolder.getClassName() + "</servlet-class>");

            if (servletHolder.getInitOrder() != 0)
                out.println("    <init-order>" + servletHolder.getInitOrder() + "</init-order>");

            dumpInitParameters(out, servletHolder.getInitParameters());
            out.println("</servlet>");
        }
        out.println();

        for (ServletMapping mapping : servletHandler.getServletMappings()) {
            String[] pathSpecs = mapping.getPathSpecs();
            if (pathSpecs.length == 0)
                throw new IllegalUsageException("No mapping path specified for servlet " + mapping.getServletName());
            for (String pathSpec : pathSpecs) {
                out.println("<servlet-mapping>");
                out.println("    <servlet-name>" + mapping.getServletName() + "</servlet-name>");
                out.println("    <url-pattern>" + pathSpec + "</url-pattern>");
                out.println("</servlet-mapping>");
            }
        }
        out.println();

    }

    static void dumpInitParameters(IIndentedOut out, Map<?, ?> map) {
        out.enter();
        for (Entry<?, ?> entry : map.entrySet()) {
            out.println("<init-param>");
            out.println("    <param-name>" + entry.getKey() + "</param-name>");
            out.println("    <param-value>" + entry.getValue() + "</param-value>");
            out.println("</init-param>");
        }
        out.leave();
    }

}
