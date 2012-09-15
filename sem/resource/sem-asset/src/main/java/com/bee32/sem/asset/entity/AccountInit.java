package com.bee32.sem.asset.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.sem.process.base.ProcessEntity;

/**
 * 账务初始化
 *
 * 账务系统初始化
 *
 * @author jack
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "account_init_seq", allocationSize = 1)
public class AccountInit
        extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    List<AccountInitItem> items = new ArrayList<AccountInitItem>();

    @Override
    public void populate(Object source) {
        if (source instanceof AccountInit)
            _populate((AccountInit) source);
        else
            super.populate(source);
    }

    protected void _populate(AccountInit o) {
        super._populate(o);
        items = CopyUtils.copyList(o.items);
    }

    @OneToMany(mappedBy = "init", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<AccountInitItem> getItems() {
        return items;
    }

    /**
     * 初始化明细列表
     *
     * 账务初始化时对应的科目明细列表
     *
     * @param items
     */
    public void setItems(List<AccountInitItem> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

}
