package com.bee32.icsf.login;

import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.logging.AbstractEec;
import com.bee32.plover.arch.logging.ExceptionLogEntry;

public class SessionUserEec
        extends AbstractEec {

    public static final String USER_KEY = "user";

    public SessionUserEec() {
        ExceptionLogEntry.registerAttribute(USER_KEY, "操作用户");
    }

    @Override
    public void completeEntry(ExceptionLogEntry entry) {
        UserDto user = SessionUser.getInstance().getUserOpt();
        entry.setAttribute(USER_KEY, user.getDisplayName());
    }

}
