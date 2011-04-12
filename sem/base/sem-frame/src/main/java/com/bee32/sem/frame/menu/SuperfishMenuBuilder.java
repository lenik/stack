package com.bee32.sem.frame.menu;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.free.IllegalUsageException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.UriUtils;

import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.servlet.context.ContextLocation;
import com.bee32.plover.servlet.context.LocationContextConstants;
import com.bee32.plover.servlet.util.ThreadServletContext;
import com.bee32.plover.util.PrettyPrintStream;
import com.bee32.sem.frame.action.IAction;

public class SuperfishMenuBuilder {

    private final MenuBar menuBar;
    private final HttpServletRequest request;

    private String html;
    private PrettyPrintStream out;

    public SuperfishMenuBuilder(MenuBar menuBar) {
        this(menuBar, null);
    }

    public SuperfishMenuBuilder(MenuBar menuBar, HttpServletRequest request) {
        if (menuBar == null)
            throw new NullPointerException("menuBar");
        this.menuBar = menuBar;

        if (request != null)
            this.request = request;
        else
            this.request = ThreadServletContext.requireRequest();
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
        if (action != null && action.isEnabled()) {

            ContextLocation target = action.getTargetLocation();
            if (target.getContext() == LocationContextConstants.JAVASCRIPT) {
                String javascript = target.getLocation();

                String javascriptEscaped = HtmlUtils.htmlEscape(javascript);

                out.print(" href='#' onclick=\"");
                out.print(javascriptEscaped);
                out.print("\"");

            } else {

                String href = target.resolve(request);
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
            List<IMenuNode> children = new ArrayList<IMenuNode>(menuNode.getChildren());
            Collections.sort(children, MenuEntryComparator.INSTANCE);

            out.println("<ul>");
            out.enter();
            for (IMenuNode childNode : children) {
                buildMenu(childNode);
            }
            out.leave();
            out.println("</ul>");
        }

        out.leave();
        out.println("</li>");
    }

}
