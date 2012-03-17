package com.bee32.plover.servlet.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.free.AbstractProxy;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * @see HttpServletRequestWrapper
 */
@Deprecated
public class DecoratedHttpServletRequest
        extends AbstractProxy<HttpServletRequest>
        implements HttpServletRequest {

    public DecoratedHttpServletRequest(HttpServletRequest proxy) {
        super(proxy);
    }

    @Override
    public Object getAttribute(String name) {
        return proxy.getAttribute(name);
    }

    @Override
    public String getAuthType() {
        return proxy.getAuthType();
    }

    @Override
    public Cookie[] getCookies() {
        return proxy.getCookies();
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return proxy.getAttributeNames();
    }

    @Override
    public long getDateHeader(String name) {
        return proxy.getDateHeader(name);
    }

    @Override
    public String getCharacterEncoding() {
        return proxy.getCharacterEncoding();
    }

    @Override
    public void setCharacterEncoding(String env)
            throws UnsupportedEncodingException {
        proxy.setCharacterEncoding(env);
    }

    @Override
    public String getHeader(String name) {
        return proxy.getHeader(name);
    }

    @Override
    public int getContentLength() {
        return proxy.getContentLength();
    }

    @Override
    public String getContentType() {
        return proxy.getContentType();
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        return proxy.getHeaders(name);
    }

    @Override
    public ServletInputStream getInputStream()
            throws IOException {
        return proxy.getInputStream();
    }

    @Override
    public String getParameter(String name) {
        return proxy.getParameter(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return proxy.getHeaderNames();
    }

    @Override
    public int getIntHeader(String name) {
        return proxy.getIntHeader(name);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return proxy.getParameterNames();
    }

    @Override
    public String getMethod() {
        return proxy.getMethod();
    }

    @Override
    public String[] getParameterValues(String name) {
        return proxy.getParameterValues(name);
    }

    @Override
    public String getPathInfo() {
        return proxy.getPathInfo();
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return proxy.getParameterMap();
    }

    @Override
    public String getProtocol() {
        return proxy.getProtocol();
    }

    @Override
    public String getPathTranslated() {
        return proxy.getPathTranslated();
    }

    @Override
    public String getScheme() {
        return proxy.getScheme();
    }

    @Override
    public String getContextPath() {
        return proxy.getContextPath();
    }

    @Override
    public String getServerName() {
        return proxy.getServerName();
    }

    @Override
    public int getServerPort() {
        return proxy.getServerPort();
    }

    @Override
    public BufferedReader getReader()
            throws IOException {
        return proxy.getReader();
    }

    @Override
    public String getQueryString() {
        return proxy.getQueryString();
    }

    @Override
    public String getRemoteUser() {
        return proxy.getRemoteUser();
    }

    @Override
    public String getRemoteAddr() {
        return proxy.getRemoteAddr();
    }

    @Override
    public String getRemoteHost() {
        return proxy.getRemoteHost();
    }

    @Override
    public boolean isUserInRole(String role) {
        return proxy.isUserInRole(role);
    }

    @Override
    public void setAttribute(String name, Object o) {
        proxy.setAttribute(name, o);
    }

    @Override
    public Principal getUserPrincipal() {
        return proxy.getUserPrincipal();
    }

    @Override
    public String getRequestedSessionId() {
        return proxy.getRequestedSessionId();
    }

    @Override
    public void removeAttribute(String name) {
        proxy.removeAttribute(name);
    }

    @Override
    public String getRequestURI() {
        return proxy.getRequestURI();
    }

    @Override
    public Locale getLocale() {
        return proxy.getLocale();
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return proxy.getLocales();
    }

    @Override
    public StringBuffer getRequestURL() {
        return proxy.getRequestURL();
    }

    @Override
    public boolean isSecure() {
        return proxy.isSecure();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return proxy.getRequestDispatcher(path);
    }

    @Override
    public String getServletPath() {
        return proxy.getServletPath();
    }

    @Override
    public HttpSession getSession(boolean create) {
        return proxy.getSession(create);
    }

    @Deprecated
    @Override
    public String getRealPath(String path) {
        return proxy.getRealPath(path);
    }

    @Override
    public int getRemotePort() {
        return proxy.getRemotePort();
    }

    @Override
    public String getLocalName() {
        return proxy.getLocalName();
    }

    @Override
    public HttpSession getSession() {
        return proxy.getSession();
    }

    @Override
    public String getLocalAddr() {
        return proxy.getLocalAddr();
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return proxy.isRequestedSessionIdValid();
    }

    @Override
    public int getLocalPort() {
        return proxy.getLocalPort();
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return proxy.isRequestedSessionIdFromCookie();
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return proxy.isRequestedSessionIdFromURL();
    }

    @Deprecated
    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return proxy.isRequestedSessionIdFromUrl();
    }

    // Java Servlet 3.0
    @Override
    public ServletContext getServletContext() {
        return proxy.getServletContext();
    }

    @Override
    public AsyncContext startAsync()
            throws IllegalStateException {
        return proxy.startAsync();
    }

    @Override
    public boolean authenticate(HttpServletResponse response)
            throws IOException, ServletException {
        return proxy.authenticate(response);
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
            throws IllegalStateException {
        return proxy.startAsync(servletRequest, servletResponse);
    }

    @Override
    public void login(String username, String password)
            throws ServletException {
        proxy.login(username, password);
    }

    @Override
    public void logout()
            throws ServletException {
        proxy.logout();
    }

    @Override
    public Collection<Part> getParts()
            throws IOException, ServletException {
        return proxy.getParts();
    }

    @Override
    public boolean isAsyncStarted() {
        return proxy.isAsyncStarted();
    }

    @Override
    public Part getPart(String name)
            throws IOException, ServletException {
        return proxy.getPart(name);
    }

    @Override
    public boolean isAsyncSupported() {
        return proxy.isAsyncSupported();
    }

    @Override
    public AsyncContext getAsyncContext() {
        return proxy.getAsyncContext();
    }

    @Override
    public DispatcherType getDispatcherType() {
        return proxy.getDispatcherType();
    }

}
