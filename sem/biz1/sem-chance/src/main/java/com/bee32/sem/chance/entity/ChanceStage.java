package com.bee32.sem.chance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.ShortNameDict;

/**
 * 机会阶段推进
 */
@Entity
public class ChanceStage
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    int order;

    public ChanceStage() {
        super();
    }

    public ChanceStage(String name, String label) {
        super(name, label);
    }

    public ChanceStage(String name, String label, String description) {
        super(name, label, description);
    }

    /**
     * 机会推进顺序
     */
    @Column(nullable = false, length = 10)
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
