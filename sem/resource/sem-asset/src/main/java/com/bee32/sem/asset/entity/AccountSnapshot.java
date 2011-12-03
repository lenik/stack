package com.bee32.sem.asset.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.base.tx.TxEntity;

/**
 * {@link #getBeginTime() begin-time} is used as the snapshot time.
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "account_snapshot_seq", allocationSize = 1)
public class AccountSnapshot
        extends TxEntity {

    private static final long serialVersionUID = 1L;

    List<AccountSnapshotItem> items;

    @OneToMany(mappedBy = "snapshot")
    public List<AccountSnapshotItem> getItems() {
        return items;
    }

    public void setItems(List<AccountSnapshotItem> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

}
