package com.bee32.zebra.oa.thread.impl;

import java.util.Set;

import net.bodz.bas.t.set.IntRange;

import com.tinylily.model.mx.base.CoMessageCriteria;

public class TopicCriteria
        extends CoMessageCriteria {

    Integer categoryId;
    Integer phaseId;
    IntRange valueRange;
    Set<String> tags;

}
