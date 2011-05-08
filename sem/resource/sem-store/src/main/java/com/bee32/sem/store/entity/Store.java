package com.bee32.sem.store.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.ext.xp.EntityExt;
import com.bee32.plover.orm.ext.xp.XPool;

@MappedSuperclass
public abstract class Store<X extends XPool<?>>
        extends EntityExt<Integer, X> {

    private static final long serialVersionUID = 1L;

    String name;
    String description;

    List<StoreSnapshot> snapshots;
    StoreSnapshot workingCopy;

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
    public List<StoreSnapshot> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(List<StoreSnapshot> snapshots) {
        this.snapshots = snapshots;
    }

    @OneToOne
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public StoreSnapshot getWorkingCopy() {
        return workingCopy;
    }

    public void setWorkingCopy(StoreSnapshot workingCopy) {
        this.workingCopy = workingCopy;
    }

}
