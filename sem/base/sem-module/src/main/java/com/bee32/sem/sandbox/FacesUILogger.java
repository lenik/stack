package com.bee32.sem.sandbox;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.free.AbstractLogSink;
import javax.free.AbstractLogger;
import javax.free.ILogSink;
import javax.free.LogLevel;
import javax.free.NullLogSink;

public class FacesUILogger
        extends AbstractLogger {

    private static final long serialVersionUID = 1L;

    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    @Override
    public ILogSink get(LogLevel level, int delta) {
        FacesContext context = getFacesContext();

        int priority = level.getPriority() + delta;
        switch (priority) {
        case -4:
        case -3:
            return new FFatalSink(context);

        case -2:
            return new FErrorSink(context);

        case -1:
            // if (jdkLogger.isLoggable(Level.WARNING))
            return new FWarnSink(context);

        case 0:
        case 1:
        case 2:
        case 3: // debug?
        case 4: // trace
            return new FInfoSink(context);
        }
        return NullLogSink.getInstance();
    }

    static abstract class FSink
            extends AbstractLogSink {

        final FacesContext context;

        public FSink(FacesContext context) {
            this.context = context;
        }

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
                if (message.startsWith(";"))
                    message = title + message.substring(1);
            }

            if (e != null) {
                String err = e.getMessage();

                message += "（错误消息：" + err + "）";

                message = message.replace("\n", "<br>");
                message = message.replace("&", "&amp;");
                message = message.replace("<", "&lt;");
                message = "<div style='color: #ff0033; font-size: small; font-style: italic'>" + message + "</div>";
            }

            FacesMessage facesMessage = new FacesMessage(getSeverity(), title, message);
            String clientId = null; // XXX clientId purpose?

            context.addMessage(clientId, facesMessage);

            if (e != null)
                e.printStackTrace();
        }

        protected abstract Severity getSeverity();

    }

    static final class FInfoSink
            extends FSink {

        public FInfoSink(FacesContext context) {
            super(context);
        }

        @Override
        protected Severity getSeverity() {
            return FacesMessage.SEVERITY_INFO;
        }

    }

    static final class FWarnSink
            extends FSink {

        public FWarnSink(FacesContext context) {
            super(context);
        }

        @Override
        protected Severity getSeverity() {
            return FacesMessage.SEVERITY_WARN;
        }

    }

    static final class FErrorSink
            extends FSink {

        public FErrorSink(FacesContext context) {
            super(context);
        }

        @Override
        protected Severity getSeverity() {
            return FacesMessage.SEVERITY_ERROR;
        }

    }

    static final class FFatalSink
            extends FSink {

        public FFatalSink(FacesContext context) {
            super(context);
        }

        @Override
        protected Severity getSeverity() {
            return FacesMessage.SEVERITY_FATAL;
        }

    }

}
