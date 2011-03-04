package com.bee32.plover.servlet.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.free.AbstractProxy;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

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

    public Object getAttribute(String name) {
        return proxy.getAttribute(name);
    }

    public String getAuthType() {
        return proxy.getAuthType();
    }

    public Cookie[] getCookies() {
        return proxy.getCookies();
    }

    @SuppressWarnings("unchecked")
    public Enumeration<String> getAttributeNames() {
        return proxy.getAttributeNames();
    }

    public long getDateHeader(String name) {
        return proxy.getDateHeader(name);
    }

    public String getCharacterEncoding() {
        return proxy.getCharacterEncoding();
    }

    public void setCharacterEncoding(String env)
            throws UnsupportedEncodingException {
        proxy.setCharacterEncoding(env);
    }

    public String getHeader(String name) {
        return proxy.getHeader(name);
    }

    public int getContentLength() {
        return proxy.getContentLength();
    }

    public String getContentType() {
        return proxy.getContentType();
    }

    @SuppressWarnings("unchecked")
    public Enumeration<String> getHeaders(String name) {
        return proxy.getHeaders(name);
    }

    public ServletInputStream getInputStream()
            throws IOException {
        return proxy.getInputStream();
    }

    public String getParameter(String name) {
        return proxy.getParameter(name);
    }

    @SuppressWarnings("unchecked")
    public Enumeration<String> getHeaderNames() {
        return proxy.getHeaderNames();
    }

    public int getIntHeader(String name) {
        return proxy.getIntHeader(name);
    }

    @SuppressWarnings("unchecked")
    public Enumeration<String> getParameterNames() {
        return proxy.getParameterNames();
    }

    public String getMethod() {
        return proxy.getMethod();
    }

    public String[] getParameterValues(String name) {
        return proxy.getParameterValues(name);
    }

    public String getPathInfo() {
        return proxy.getPathInfo();
    }

    @SuppressWarnings("unchecked")
    public Map<String, String[]> getParameterMap() {
        return proxy.getParameterMap();
    }

    public String getProtocol() {
        return proxy.getProtocol();
    }

    public String getPathTranslated() {
        return proxy.getPathTranslated();
    }

    public String getScheme() {
        return proxy.getScheme();
    }

    public String getContextPath() {
        return proxy.getContextPath();
    }

    public String getServerName() {
        return proxy.getServerName();
    }

    public int getServerPort() {
        return proxy.getServerPort();
    }

    public BufferedReader getReader()
            throws IOException {
        return proxy.getReader();
    }

    public String getQueryString() {
        return proxy.getQueryString();
    }

    public String getRemoteUser() {
        return proxy.getRemoteUser();
    }

    public String getRemoteAddr() {
        return proxy.getRemoteAddr();
    }

    public String getRemoteHost() {
        return proxy.getRemoteHost();
    }

    public boolean isUserInRole(String role) {
        return proxy.isUserInRole(role);
    }

    public void setAttribute(String name, Object o) {
        proxy.setAttribute(name, o);
    }

    public Principal getUserPrincipal() {
        return proxy.getUserPrincipal();
    }

    public String getRequestedSessionId() {
        return proxy.getRequestedSessionId();
    }

    public void removeAttribute(String name) {
        proxy.removeAttribute(name);
    }

    public String getRequestURI() {
        return proxy.getRequestURI();
    }

    public Locale getLocale() {
        return proxy.getLocale();
    }

    @SuppressWarnings("unchecked")
    public Enumeration<Locale> getLocales() {
        return proxy.getLocales();
    }

    public StringBuffer getRequestURL() {
        return proxy.getRequestURL();
    }

    public boolean isSecure() {
        return proxy.isSecure();
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        return proxy.getRequestDispatcher(path);
    }

    public String getServletPath() {
        return proxy.getServletPath();
    }

    public HttpSession getSession(boolean create) {
        return proxy.getSession(create);
    }

    @Deprecated
    public String getRealPath(String path) {
        return proxy.getRealPath(path);
    }

    public int getRemotePort() {
        return proxy.getRemotePort();
    }

    public String getLocalName() {
        return proxy.getLocalName();
    }

    public HttpSession getSession() {
        return proxy.getSession();
    }

    public String getLocalAddr() {
        return proxy.getLocalAddr();
    }

    public boolean isRequestedSessionIdValid() {
        return proxy.isRequestedSessionIdValid();
    }

    public int getLocalPort() {
        return proxy.getLocalPort();
    }

    public boolean isRequestedSessionIdFromCookie() {
        return proxy.isRequestedSessionIdFromCookie();
    }

    public boolean isRequestedSessionIdFromURL() {
        return proxy.isRequestedSessionIdFromURL();
    }

    @Deprecated
    public boolean isRequestedSessionIdFromUrl() {
        return proxy.isRequestedSessionIdFromUrl();
    }

}
