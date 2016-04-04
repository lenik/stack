package com.bee32.zebra.oa.thread.impl;

import java.util.List;

import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.tk.sql.FooMapper;
import com.bee32.zebra.tk.stat.ValueDistrib;
import com.bee32.zebra.tk.util.F_YearCount;

public interface TopicMapper
        extends FooMapper<Topic, TopicMask> {

    List<F_YearCount> histoByYear();

    int replyCount(int topicId);

    List<ValueDistrib> catDistrib();

    List<ValueDistrib> phaseDistrib();

}
