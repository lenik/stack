package com.bee32.sem.asset.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.base.tx.TxEntity;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "account_init_seq", allocationSize = 1)
public class AccountInit
        extends TxEntity {

    private static final long serialVersionUID = 1L;

    List<AccountInitItem> items = new ArrayList<AccountInitItem>();

    @OneToMany(mappedBy = "init", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<AccountInitItem> getItems() {
        return items;
    }

    public void setItems(List<AccountInitItem> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

}
