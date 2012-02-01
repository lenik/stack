package com.bee32.plover.internet.spam;

public class EmailUtil {

    /**
     * Obfuscate email address in html.
     *
     * @param email
     *            Email address to be Obfuscated. Maybe <code>null</code>.
     * @return Obfuscated email. Returns <code>null</code> if <code>email</code> is
     *         <code>null</code>.
     */
    public static String safeEmail(String email) {
        return htmlReverse(email);
    }

    static String htmlReverse(String email) {
        if (email == null)
            return null;

        if (email.length() < 2 || !email.contains("@"))
            return email;

        StringBuilder sb = new StringBuilder(email);
        sb.reverse();
        sb.insert(0, "<span style='unicode-bidi: bidi-override; direction: rtl'>");
        sb.append("</span>");
        return sb.toString();
    }

}
