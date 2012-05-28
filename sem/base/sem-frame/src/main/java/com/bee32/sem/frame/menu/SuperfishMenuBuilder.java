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
import com.bee32.plover.util.PrettyPrintStream;
import com.bee32.sem.frame.action.IAction;

public class SuperfishMenuBuilder
        extends AbstractMenuBuilder<String> {

    final MenuNode virtualRoot;

    private String _cachedHtml;

    public SuperfishMenuBuilder(MenuNode virtualRoot) {
        this(virtualRoot, null);
    }

    public SuperfishMenuBuilder(MenuNode virtualRoot, HttpServletRequest request) {
        super(request);
        if (virtualRoot == null)
            throw new NullPointerException("virtualRoot");
        this.virtualRoot = virtualRoot;
    }

    @Override
    protected String buildMenubarImpl(IMenuNode virtualRoot) {
        PrettyPrintStream out = new PrettyPrintStream();

        out.println("<ul class='sf-menu'>");
        out.enter();

        for (IMenuNode root : virtualRoot)
            buildMenu(out, root);

        out.leave();
        out.println("</ul>");

        String html = out.toString();
        return html;
    }

    void buildMenu(PrettyPrintStream out, IMenuNode menuNode) {
        boolean skipped;
        if (isShowAll())
            skipped = menuNode.isEmpty();
        else
            skipped = menuNode.isBarren();
        if (skipped)
            return;

        IAppearance appearance = menuNode.getAppearance();

        String label = appearance.getDisplayName();
        String tooltips = appearance.getDescription();

        out.println("<li>");
        out.enter();

        out.print("<a");

        IAction action = menuNode.getAction();
        if (action != null && action.isEnabled()) {
            ILocationContext target = action.getTargetLocation();
            String hrefEncoded = "#";
            if (target != null) {
                String href = resolve(target);
                try {
                    hrefEncoded = UriUtils.encodeUri(href, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    throw new IllegalUsageException(String.format("Bad location in %s: %s", menuNode, href));
                }
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

            boolean flatten = menuNode.isFlatten();

            if (!flatten) {
                out.println("<ul>");
                out.enter();
            }

            for (IMenuNode childNode : children) {
                buildMenu(out, childNode);
            }

            if (!flatten) {
                out.leave();
                out.println("</ul>");
            }
        }

        out.leave();
        out.println("</li>");
    }

    @Override
    public synchronized String toString() {
        if (_cachedHtml == null) {
            _cachedHtml = buildMenubar(virtualRoot);
        }
        return _cachedHtml;
    }

}
