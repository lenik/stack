package com.bee32.sem.frame.menu;

import java.io.UnsupportedEncodingException;

import javax.free.IllegalUsageException;

import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.UriUtils;

import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.servlet.context.ContextLocation;
import com.bee32.plover.servlet.context.LocationContextConstants;
import com.bee32.plover.util.PrettyPrintStream;
import com.bee32.sem.frame.action.IAction;

public class SuperfishMenuBuilder {

    private final MenuBar menuBar;

    private String html;
    private PrettyPrintStream buf;

    public SuperfishMenuBuilder(MenuBar menuBar) {
        if (menuBar == null)
            throw new NullPointerException("menuBar");
        this.menuBar = menuBar;
    }

    @Override
    public synchronized String toString() {
        if (html == null) {
            buf = new PrettyPrintStream();
            buildMenubar(menuBar);
            html = buf.toString();
            buf = null;
        }
        return html;
    }

    void buildMenubar(IMenuNode menuBarNode) {
        this.buf = new PrettyPrintStream();

        buf.println("<ul class='sf-menu'>");
        buf.enter();

        for (IMenuNode menuNode : menuBarNode)
            buildMenu(menuNode);

        buf.leave();
        buf.println("</ul>");
    }

    void buildMenu(IMenuNode menuNode) {
        IAppearance appearance = menuNode.getAppearance();

        String label = appearance.getDisplayName();
        String tooltips = appearance.getDescription();

        buf.println("<li>");
        buf.enter();

        buf.print("<a");

        IAction action = menuNode.getAction();
        if (action != null) {
            boolean isEnabled = action.isEnabled();

            ContextLocation target = action.getTarget();
            if (target.getContext() == LocationContextConstants.JAVASCRIPT) {
                String javascript = target.getLocation();

                String javascriptEscaped = HtmlUtils.htmlEscape(javascript);

                buf.print(" href='#' onclick=\"");
                buf.print(javascriptEscaped);
                buf.print("\"");

            } else {

                String href = target.getLocation();
                String hrefEncoded;
                try {
                    hrefEncoded = UriUtils.encodeUri(href, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    throw new IllegalUsageException(String.format("Bad location in %s: %s", menuNode, href));
                }

                buf.print(" href='");
                buf.print(hrefEncoded);
                buf.print("'");
            }
        }

        if (tooltips != null && !tooltips.isEmpty()) {
            buf.print(" title='");
            buf.print(HtmlUtils.htmlEscape(tooltips));
            buf.print("'");
        }

        buf.print(">");
        buf.print(label);
        buf.println("</a>");

        if (!menuNode.isEmpty()) {
            for (IMenuNode childNode : menuNode) {
                buf.println("<ul>");
                buf.enter();

                buildMenu(childNode);

                buf.leave();
                buf.println("</ul>");
            }
        }

        buf.leave();
        buf.println("</li>");
    }

}
