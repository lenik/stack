package com.bee32.plover.faces.diag;

import com.bee32.plover.faces.PloverErrorPageRenderer;

public class StackTraceElementWrapper {

    final StackTraceElement element;

    public StackTraceElementWrapper(StackTraceElement element) {
        if (element == null)
            throw new NullPointerException("element");
        this.element = element;
    }

    public String getFileName() {
        return element.getFileName();
    }

    public int getLineNumber() {
        return element.getLineNumber();
    }

    public String getClassName() {
        return element.getClassName();
    }

    public String getMethodName() {
        return element.getMethodName();
    }

    public boolean isNativeMethod() {
        return element.isNativeMethod();
    }

    public String getLine() {
        StringBuilder sb = new StringBuilder(100);
        sb.append(getClassName());
        sb.append(".");
        sb.append(getMethodName());
        sb.append("(");
        sb.append(getFileName());
        sb.append(":");
        sb.append(getLineNumber());
        sb.append(")");
        return sb.toString();
    }

    public String getHtml() {
        String line = getLine();
        return PloverErrorPageRenderer.highlight(line);
    }

    @Override
    public String toString() {
        return getLine();
    }

}
