package com.bee32.zebra.oa.thread.impl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bee32.zebra.tk.sql.VhostDataService;

public class TopicMapperTest
        extends Assert {

    TopicMapper mapper;

    @BeforeClass
    void setUp() {
        mapper = VhostDataService.getInstance().getMapper(TopicMapper.class);
    }

    @Test
    public void test1() {
        mapper.select(1);
    }

}
