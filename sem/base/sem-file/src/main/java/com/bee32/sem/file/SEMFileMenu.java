package com.bee32.sem.file;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.menu.MenuContribution;

public class SEMFileMenu
        extends MenuContribution {

    static Location FILE_ = WEB_APP.join(SEMFileModule.PREFIX_);

    @Override
    protected void preamble() {
    }

}
