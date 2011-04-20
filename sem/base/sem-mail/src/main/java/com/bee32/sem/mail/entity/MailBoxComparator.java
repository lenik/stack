package com.bee32.sem.mail.entity;

import javax.free.AbstractNonNullComparator;

public class MailBoxComparator
        extends AbstractNonNullComparator<MailBox> {

    @Override
    public int compareNonNull(MailBox a, MailBox b) {
        int cmp = a.getPriority() - b.getPriority();
        if (cmp != 0)
            return cmp;

        cmp = a.getOrder() - b.getOrder();
        if (cmp != 0)
            return cmp;

        cmp = a.getName().compareTo(b.getName());
        if (cmp != 0)
            return cmp;

        return System.identityHashCode(a) - System.identityHashCode(b);
    }

    public static final MailBoxComparator INSTANCE = new MailBoxComparator();

}
