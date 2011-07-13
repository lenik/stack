package com.bee32.sem.inventory.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.ext.color.Green;
import com.bee32.plover.orm.ext.xp.EntityExt;

/**
 * 库存。通常一个企业只有一个库存（但有多个仓库）。
 */
@Entity
@Green
public class StockInventory
        extends EntityExt<Integer, StockInventoryXP> {

    private static final long serialVersionUID = 1L;

    List<StockSnapshot> snapshots = new ArrayList<StockSnapshot>();
    StockSnapshot workingBase;

    /**
     * 逻辑库存名称
     */
    @Column(length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 库存快照列表
     */
    @OneToMany(mappedBy = "inventory")
    @Cascade(CascadeType.ALL)
    public List<StockSnapshot> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(List<StockSnapshot> snapshots) {
        this.snapshots = snapshots;
    }

    /**
     * 当前工作拷贝
     */
    @OneToOne
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public StockSnapshot getWorkingBase() {
        return workingBase;
    }

    public void setWorkingBase(StockSnapshot workingBase) {
        this.workingBase = workingBase;
    }

}
