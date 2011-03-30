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
    private PrettyPrintStream out;

    public SuperfishMenuBuilder(MenuBar menuBar) {
        if (menuBar == null)
            throw new NullPointerException("menuBar");
        this.menuBar = menuBar;
    }

    @Override
    public synchronized String toString() {
        if (html == null) {
            out = new PrettyPrintStream();
            buildMenubar(menuBar);
            html = out.toString();
            out = null;
        }
        return html;
    }

    void buildMenubar(IMenuNode menuBarNode) {
        this.out = new PrettyPrintStream();

        out.println("<ul class='sf-menu'>");
        out.enter();

        for (IMenuNode menuNode : menuBarNode)
            buildMenu(menuNode);

        out.leave();
        out.println("</ul>");
    }

    void buildMenu(IMenuNode menuNode) {
        IAppearance appearance = menuNode.getAppearance();

        String label = appearance.getDisplayName();
        String tooltips = appearance.getDescription();

        out.println("<li>");
        out.enter();

        out.print("<a");

        IAction action = menuNode.getAction();
        if (action != null) {
            boolean isEnabled = action.isEnabled();

            ContextLocation target = action.getTarget();
            if (target.getContext() == LocationContextConstants.JAVASCRIPT) {
                String javascript = target.getLocation();

                String javascriptEscaped = HtmlUtils.htmlEscape(javascript);

                out.print(" href='#' onclick=\"");
                out.print(javascriptEscaped);
                out.print("\"");

            } else {

                String href = target.getLocation();
                String hrefEncoded;
                try {
                    hrefEncoded = UriUtils.encodeUri(href, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    throw new IllegalUsageException(String.format("Bad location in %s: %s", menuNode, href));
                }

                out.print(" href='");
                out.print(hrefEncoded);
                out.print("'");
            }
        }

        if (tooltips != null && !tooltips.isEmpty()) {
            out.print(" title='");
            out.print(HtmlUtils.htmlEscape(tooltips));
            out.print("'");
        }

        out.print(">");
        out.print(label);
        out.println("</a>");

        if (!menuNode.isEmpty()) {
            for (IMenuNode childNode : menuNode) {
                out.println("<ul>");
                out.enter();

                buildMenu(childNode);

                out.leave();
                out.println("</ul>");
            }
        }

        out.leave();
        out.println("</li>");
    }

}
