package com.bee32.plover.faces.primefaces;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.push.Push;
import org.primefaces.component.push.PushRenderer;
import org.primefaces.util.Constants;

public class PloverPushRenderer
        extends PushRenderer {

    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        Push push = (Push) component;

        ResponseWriter writer = context.getResponseWriter();
        String widgetVar = push.resolveWidgetVar();

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(getPushServerUrl(context))//
                .append(push.getChannel());

        String url = context.getExternalContext().encodeActionURL(urlBuilder.toString());

        writer.startElement("script", null);
        writer.writeAttribute("type", "text/javascript", null);

        writer.write("$(function() {");
        writer.write(widgetVar + " = new PrimeFaces.widget.PrimeWebSocket({");
        writer.write("url:'" + url + "'");
        writer.write(",channel:'" + push.getChannel() + "'");
        writer.write(",onmessage:" + push.getOnmessage());
        writer.write(",autoConnect:" + push.isAutoConnect());

        encodeClientBehaviors(context, push);

        writer.write("});});");

        writer.endElement("script");
    }

    protected String getPushServerUrl(FacesContext context) {
        ExternalContext ectx = context.getExternalContext();
        String url = ectx.getInitParameter(Constants.PUSH_SERVER_URL);
        if (!url.contains("://")) {
            String requestServerName = ectx.getRequestServerName();
            int requestServerPort = ectx.getRequestServerPort();
            url = "ws://" + requestServerName + ":" + requestServerPort + url;
        }
        // url += Constants.PUSH_PATH;
        if (!url.endsWith("/"))
            url += "/";
        return url;
    }

}
