package com.bee32.sem.file;

import com.bee32.plover.servlet.context.Location;
import com.bee32.sem.file.web.FileBlobController;
import com.bee32.sem.frame.menu.MenuContribution;

public class SEMFileMenu
        extends MenuContribution {

    Location FILE = WEB_APP.join(FileBlobController.PREFIX);

    @Override
    protected void preamble() {
    }

}
