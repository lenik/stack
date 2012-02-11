package com.bee32.plover.faces.utils;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.free.AbstractLogSink;
import javax.free.AbstractLogger;
import javax.free.ILogSink;
import javax.free.LogLevel;
import javax.free.NullLogSink;

import com.bee32.plover.faces.ErrorMessageTranslator;

public class FacesUILogger
        extends AbstractLogger {

    final boolean html;

    public FacesUILogger(boolean html) {
        this.html = html;
    }

    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    protected void append(FacesMessage message) {
        String clientId = null; // XXX clientId purpose?

        FacesContext context = getFacesContext();
        context.addMessage(clientId, message);

        Severity severity = message.getSeverity();
        if (severity.compareTo(FacesMessage.SEVERITY_ERROR) >= 0)
            context.validationFailed();
    }

    @Override
    public ILogSink get(LogLevel level, int delta) {
        int priority = level.getPriority() + delta;
        switch (priority) {
        case -4:
        case -3:
            return new FFatalSink();

        case -2:
            return new FErrorSink();

        case -1:
            // if (jdkLogger.isLoggable(Level.WARNING))
            return new FWarnSink();

        case 0:
        case 1:
        case 2:
        case 3: // debug?
        case 4: // trace
            return new FInfoSink();
        }
        return NullLogSink.getInstance();
    }

    abstract class FSink
            extends AbstractLogSink {

        @Override
        public void logMessage(Object obj) {
            log(String.valueOf(obj), null);
        }

        @Override
        public void logException(Object obj, Throwable throwable) {
            log(String.valueOf(obj), throwable);
        }

        protected void log(String text, Throwable e) {
            int colon = text.indexOf(':');
            String title;
            String message;
            if (colon == -1) {
                title = text;
                message = "";
            } else {
                title = text.substring(0, colon);
                message = text.substring(colon + 1);
                if (message.startsWith(";")) // title:;cont-message
                    message = title + message.substring(1);
            }
            // title = encodeHtml(title);
            message = encodeHtml(message);

            if (e != null) {
                String err = e.getMessage();
                if (err != null) {
                    err = ErrorMessageTranslator.translateMessage(err);
                    message = "<div style='color: #ff0033; font-size: small; font-style: italic'>" //
                            + "（错误消息：" + encodeHtml(err) + "）" + message + "</div>";
                }
            }

            FacesMessage facesMessage = new FacesMessage(getSeverity(), title, message);
            append(facesMessage);

            if (e != null)
                e.printStackTrace();
        }

        protected abstract Severity getSeverity();

        String encodeHtml(String s) {
            if (!html) {
                if (s == null)
                    return null;
                s = s.replace("&", "&amp;");
                s = s.replace("<", "&lt;");
                s = s.replace(" ", "&nbsp;");
                s = s.replace("\n", "<br>");
            }
            return s;
        }

    }

    final class FInfoSink
            extends FSink {

        @Override
        protected Severity getSeverity() {
            return FacesMessage.SEVERITY_INFO;
        }

    }

    final class FWarnSink
            extends FSink {

        @Override
        protected Severity getSeverity() {
            return FacesMessage.SEVERITY_WARN;
        }

    }

    final class FErrorSink
            extends FSink {

        @Override
        protected Severity getSeverity() {
            return FacesMessage.SEVERITY_ERROR;
        }

    }

    final class FFatalSink
            extends FSink {

        @Override
        protected Severity getSeverity() {
            return FacesMessage.SEVERITY_FATAL;
        }

    }

}
