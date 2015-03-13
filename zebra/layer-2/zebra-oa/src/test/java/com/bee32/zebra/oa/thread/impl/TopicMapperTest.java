package com.bee32.zebra.oa.thread.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bee32.zebra.tk.sql.VhostDataService;

public class TopicMapperTest
        extends Assert {

    TopicMapper mapper;

    @Before
    public void setUp() {
        mapper = VhostDataService.forCurrentRequest().query(TopicMapper.class);
    }

    @Test
    public void test1() {
        mapper.select(1);
    }

}
