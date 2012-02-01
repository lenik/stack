package com.bee32.sem.sandbox;

import java.util.Iterator;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.faces.utils.FacesUILogger;

public class FacesUILoggerTest
        extends Assert {

    FacesMessage lastMessage;

    class MockedFacesContext
            extends FacesContext {

        @Override
        public void addMessage(String clientId, FacesMessage message) {
            lastMessage = message;
        }

        @Override
        public Application getApplication() {
            return null;
        }

        @Override
        public Iterator<String> getClientIdsWithMessages() {
            return null;
        }

        @Override
        public ExternalContext getExternalContext() {
            return null;
        }

        @Override
        public Severity getMaximumSeverity() {
            return null;
        }

        @Override
        public Iterator<FacesMessage> getMessages() {
            return null;
        }

        @Override
        public Iterator<FacesMessage> getMessages(String clientId) {
            return null;
        }

        @Override
        public RenderKit getRenderKit() {
            return null;
        }

        @Override
        public boolean getRenderResponse() {
            return false;
        }

        @Override
        public boolean getResponseComplete() {
            return false;
        }

        @Override
        public ResponseStream getResponseStream() {
            return null;
        }

        @Override
        public ResponseWriter getResponseWriter() {
            return null;
        }

        @Override
        public UIViewRoot getViewRoot() {
            return null;
        }

        @Override
        public void release() {
        }

        @Override
        public void renderResponse() {
        }

        @Override
        public void responseComplete() {
        }

        @Override
        public void setResponseStream(ResponseStream responseStream) {
        }

        @Override
        public void setResponseWriter(ResponseWriter responseWriter) {
        }

        @Override
        public void setViewRoot(UIViewRoot root) {
        }

    }

    MockedFacesContext facesContext = new MockedFacesContext();

    FacesUILogger logger = new FacesUILogger(false) {
        protected FacesContext getFacesContext() {
            return facesContext;
        }
    };

    @Test
    public void testFatal() {
        logger.fatal("Fatal1");
        assertEquals(FacesMessage.SEVERITY_FATAL, lastMessage.getSeverity());
    }

    @Test
    public void testError() {
        logger.error("Error1");
        assertEquals(FacesMessage.SEVERITY_ERROR, lastMessage.getSeverity());
    }

    @Test
    public void testWarn() {
        logger.warn("Warn1");
        assertEquals(FacesMessage.SEVERITY_WARN, lastMessage.getSeverity());
    }

    @Test
    public void testInfo() {
        logger.info("Info1");
        assertEquals(FacesMessage.SEVERITY_INFO, lastMessage.getSeverity());
    }

    @Test
    public void testDebug() {
        logger.debug("Debug1");
        assertEquals(FacesMessage.SEVERITY_INFO, lastMessage.getSeverity());
    }

    @Test
    public void testTrace() {
        logger.trace("Trace1");
        assertEquals(FacesMessage.SEVERITY_INFO, lastMessage.getSeverity());
    }

}
