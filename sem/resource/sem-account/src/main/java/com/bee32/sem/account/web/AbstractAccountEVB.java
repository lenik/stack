package com.bee32.sem.account.web;

import com.bee32.plover.arch.util.FriendData;
import com.bee32.plover.orm.util.EntityViewBean;

public abstract class AbstractAccountEVB
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    static final boolean readOnlyTxEnabled = true;

    protected String getBundledSQL(String name, Object... args) {
        String script = FriendData.script(getClass(), name + ".sql");
        for (int i = 0; i < args.length - 1; i += 2) {
            String key = String.valueOf(args[i]);
            Object value = args[i + 1];
            if (value == null)
                continue;
            String replacement = String.valueOf(value);
            script = script.replace(key, replacement);
        }
        return script;
    }

}
