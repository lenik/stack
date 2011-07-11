package com.bee32.sem.inventory.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.ext.xp.EntityExt;
import com.bee32.plover.orm.ext.xp.XPool;

@Entity
public class StockInventory<X extends XPool<?>>
        extends EntityExt<Integer, X> {

    private static final long serialVersionUID = 1L;

    String name;
    String description;

    List<StockSnapshot> snapshots = new ArrayList<StockSnapshot>();
    StockSnapshot workingCopy;

    @Column(length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany
    @Cascade(CascadeType.ALL)
    public List<StockSnapshot> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(List<StockSnapshot> snapshots) {
        this.snapshots = snapshots;
    }

    @OneToOne
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public StockSnapshot getWorkingCopy() {
        return workingCopy;
    }

    public void setWorkingCopy(StockSnapshot workingCopy) {
        this.workingCopy = workingCopy;
    }

}
