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
import com.bee32.plover.rtx.location.ILocationContext;
import com.bee32.plover.servlet.util.ThreadServletContext;
import com.bee32.plover.util.PrettyPrintStream;
import com.bee32.sem.frame.action.IAction;

public class SuperfishMenuBuilder {

    private final MenuNode toplevel;
    private final HttpServletRequest request;

    private String html;
    private PrettyPrintStream out;

    public SuperfishMenuBuilder(MenuNode toplevel) {
        this(toplevel, null);
    }

    public SuperfishMenuBuilder(MenuNode toplevel, HttpServletRequest request) {
        if (toplevel == null)
            throw new NullPointerException("menuModel");
        this.toplevel = toplevel;
        this.request = request;
    }

    String resolve(ILocationContext location) {
        HttpServletRequest request = this.request;
        if (request == null)
            request = ThreadServletContext.getRequest();

        if (request == null)
            return location.toString();
        else
            return location.resolve(request);

    }

    @Override
    public synchronized String toString() {
        if (html == null) {
            out = new PrettyPrintStream();
            buildMenubar(toplevel);
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

            ILocationContext target = action.getTargetLocation();
            String href = resolve(target);
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
