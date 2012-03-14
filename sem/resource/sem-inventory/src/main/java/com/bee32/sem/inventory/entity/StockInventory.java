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

import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.xp.EntityExt;

/**
 * 库存。通常一个企业只有一个库存（但有多个仓库）。
 */
@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "stock_inventory_seq", allocationSize = 1)
public class StockInventory
        extends EntityExt<Integer, StockInventoryXP> {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 30;

    List<StockPeriod> snapshots = new ArrayList<StockPeriod>();
    StockPeriod workingBase;

    public StockInventory() {
    }

    public StockInventory(String name) {
        super(name);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof StockInventory)
            _populate((StockInventory) source);
        else
            super.populate(source);
    }

    protected void _populate(StockInventory o) {
        super._populate(o);
        snapshots = CopyUtils.copyList(o.snapshots);
        workingBase = o.workingBase;
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
    @OneToMany(mappedBy = "inventory", orphanRemoval = true)
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
    @OneToOne(orphanRemoval = true)
    @Cascade({ CascadeType.ALL })
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
