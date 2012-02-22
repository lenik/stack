package com.bee32.icsf.login;

import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.site.AbstractRec;
import com.bee32.plover.site.RequestEntry;

public class SessionUserRec
        extends AbstractRec {

    public static final String USER_KEY = "user";

    public SessionUserRec() {
        RequestEntry.registerAttribute(USER_KEY, "操作用户");
    }

    @Override
    public void completeEntry(RequestEntry entry) {
        UserDto user = SessionUser.getInstance().getUserOpt();
        String userName = user == null ? "(N/A)" : user.getDisplayName();
        entry.setAttribute(USER_KEY, userName);
    }

}
