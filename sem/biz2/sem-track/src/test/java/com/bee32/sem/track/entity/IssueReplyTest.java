package com.bee32.sem.track.entity;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.orm.entity.EntityUtil;

public class IssueReplyTest
        extends Assert {

    @Test
    public void testKeyType() {
        Class<Long> keyType = EntityUtil.getKeyType(IssueReply.class);
        assertEquals(Long.class, keyType);
    }

}
