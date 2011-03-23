package com.bee32.sem.frame.menu;

import com.bee32.plover.servlet.context.LocationContext;
import com.bee32.sem.frame.Contribution;
import com.bee32.sem.frame.action.Action;

public class MenuContributionTest {

    public static class MyMenu
            extends MenuContribution {

        Action openFileAction = new Action("file.open", LocationContext.SERVLET_CONTEXT.resolve("file/open/htm"));

        @Contribution("menu.file")
        MenuItem fileOpen = new MenuItem("file.open", openFileAction);

        @Override
        protected void preamble() {
        }

    }

}
