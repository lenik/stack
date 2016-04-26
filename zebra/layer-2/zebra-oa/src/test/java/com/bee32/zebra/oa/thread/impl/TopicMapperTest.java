package com.bee32.zebra.oa.thread.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.bodz.bas.db.ctx.DataContext;
import net.bodz.bas.site.vhost.VhostDataContexts;


public class TopicMapperTest
        extends Assert {

    TopicMapper mapper;

    @Before
    public void setUp() {
        DataContext dataContext = VhostDataContexts.getInstance().forCurrentRequest();
        mapper = dataContext.query(TopicMapper.class);
    }

    @Test
    public void test1() {
        mapper.select(1);
    }

}
