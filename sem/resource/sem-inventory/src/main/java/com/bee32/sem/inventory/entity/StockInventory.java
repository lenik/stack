package com.bee32.sem.inventory.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.ext.color.Green;
import com.bee32.plover.orm.ext.xp.EntityExt;

/**
 * 库存。通常一个企业只有一个库存（但有多个仓库）。
 */
@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "stock_inventory_seq", allocationSize = 1)
public class StockInventory
        extends EntityExt<Integer, StockInventoryXP> {

    private static final long serialVersionUID = 1L;

    private static final int NAME_LENGTH = 30;

    List<StockPeriod> snapshots = new ArrayList<StockPeriod>();
    StockPeriod workingBase;

    public StockInventory() {
    }

    public StockInventory(String name) {
        super(name);
    }

    /**
     * 逻辑库存名称
     */
    @Column(length = NAME_LENGTH)
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
    public List<StockPeriod> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(List<StockPeriod> snapshots) {
        this.snapshots = snapshots;
    }

    /**
     * 当前工作拷贝
     */
    @OneToOne
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public StockPeriod getWorkingBase() {
        return workingBase;
    }

    public void setWorkingBase(StockPeriod workingBase) {
        this.workingBase = workingBase;
    }

    public static StockInventory MAIN = new StockInventory("MAIN");
    static {
        MAIN.setLabel("总库存");
        MAIN.setDescription("一般全局库存。对于使用多个库存的企业，或应取消本总库存而设置各自独立的分立库存。");
    }

}
