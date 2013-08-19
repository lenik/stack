package com.bee32.sem.mail.entity;

import javax.free.AbstractNonNullComparator;

/**
 * 文件过滤比较器
 *
 * <p lang="en">
 * Mail Filter Comparator
 */
public class MailFilterComparator
        extends AbstractNonNullComparator<MailFilter> {

    @Override
    public int compareNonNull(MailFilter a, MailFilter b) {
        int cmp = a.getOrder() - b.getOrder();
        if (cmp != 0)
            return cmp;

        cmp = a.getName().compareTo(b.getName());
        if (cmp != 0)
            return cmp;

        return System.identityHashCode(a) - System.identityHashCode(b);
    }

    public static final MailFilterComparator INSTANCE = new MailFilterComparator();

}
