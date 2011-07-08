package com.bee32.sem.base.tx;

import javax.persistence.MappedSuperclass;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.ext.color.MomentInterval;
import com.bee32.plover.orm.ext.color.Pink;

/**
 * 事务性实体所具有的特性：
 *
 * <ul>
 * <li>事务与事件的集成
 * <li>事务的一般属性：标题、制单人、起始/终止时间、简短标题、备注信息等。
 */
@MappedSuperclass
@Pink
public class TxEntity
        extends MomentInterval {

    private static final long serialVersionUID = 1L;

    String name;

    User creator;
    // logs...?

}
