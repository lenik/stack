package com.bee32.sem.mail.util;

import com.bee32.icsf.principal.User;

public class EmailUtil {

    public static String getFriendlyEmail(User user) {
        if (user == null)
            return null;
        return getFriendlyEmail(user.getDisplayName(), user.getEmailAddress());
    }

    public static String getFriendlyEmail(String name, String mailbox) {
        if (name == null)
            return mailbox;
        if (mailbox == null)
            return name;

        return name + " <" + mailbox + ">";
    }

    public static String getDisplayName(String friendlyEmail) {
        if (friendlyEmail == null)
            return null;

        int lt = friendlyEmail.indexOf('<');
        if (lt == -1)
            return friendlyEmail;

        String displayName = friendlyEmail.substring(0, lt);
        return displayName.trim();
    }

    public static String getMailbox(String friendlyEmail) {
        if (friendlyEmail == null)
            return null;

        int lt = friendlyEmail.indexOf('<');
        int gt = friendlyEmail.lastIndexOf('>');
        if (lt == -1 || gt == -1)
            return friendlyEmail;

        return friendlyEmail.substring(lt + 1, gt).trim();
    }

}
