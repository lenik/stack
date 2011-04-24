package com.bee32.sem.event.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bee32.plover.orm.util.Accessors;
import com.bee32.sem.event.dao.ActivityDao;

/**
 * 主动产生的事件。
 */
@Entity
@DiscriminatorValue("ACT")
@Accessors(ActivityDao.class)
public class Activity
        extends Event
        implements IActivity {

    private static final long serialVersionUID = 1L;

}
