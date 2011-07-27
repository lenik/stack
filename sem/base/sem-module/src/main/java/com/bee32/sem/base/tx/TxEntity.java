package com.bee32.sem.base.tx;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.ext.color.MomentInterval;

/**
 * 事务性实体所具有的特性：
 *
 * <ul>
 * <li>事务与事件的集成
 * <li>事务的一般属性：标题、制单人、起始/终止时间、简短标题、备注信息等。
 */
@MappedSuperclass
public class TxEntity
        extends MomentInterval {

    private static final long serialVersionUID = 1L;

    String name;

    // logs...?

    @Column(length = 40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
