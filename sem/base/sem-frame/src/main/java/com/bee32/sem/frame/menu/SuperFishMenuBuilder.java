package com.bee32.sem.frame.menu;

import com.bee32.plover.servlet.context.ContextLocation;
import com.bee32.plover.servlet.context.LocationContextConstants;
import com.bee32.sem.frame.action.IAction;


public class SuperFishMenuBuilder {

    public static String buildMenubar(MenuBar menuBar) {
        StringBuilder sb = new StringBuilder();
        sb.append("<ul class='sf-menu'>");

        for(IMenuEntry menuEntry : menuBar) {
            sb.append(buildMenu(menuEntry));
        }
        sb.append("</ul>");

        return sb.toString();
    }

    public static String buildMenu(IMenuEntry menuEntry) {
        StringBuilder sb = new StringBuilder();
        sb.append("<li>");
        sb.append("<a ");

        IAction action = menuEntry.getAction();
        if (action != null) {
            boolean isEnabled = action.isEnabled();

            String onClick;
            String href;

            ContextLocation target = action.getTarget();
            if (target.getContext() == LocationContextConstants.JAVASCRIPT) {
                onClick = target.getLocation();
                href = null;

                sb.append("href='#' onclick=\"");
                sb.append(onClick);
                sb.append("\"");

            } else {
                onClick = null;
                href = target.getLocation();
                sb.append("href='");
                sb.append(href);
                sb.append("'");
            }
        } else {

        }
        sb.append(">");
        sb.append(menuEntry.getAppearance().getDisplayName());
        sb.append("</a>");

        if(!isLeafNode(menuEntry)) {
            for(IMenuEntry childEntry : menuEntry) {

                sb.append("<ul>");
                sb.append(buildMenu(childEntry));
                sb.append("</ul>");
            }
        }
        sb.append("</li>");

        return sb.toString();
    }


    protected static boolean isLeafNode(IMenuEntry entry) {
        if (entry.size() == 0)
            return true;

        if (entry.size() > 1)
            return false;

        return false; //isLeafNode(entry.iterator().next());
    }
}
