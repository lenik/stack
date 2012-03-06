package com.bee32.sem.world.com;

import javax.persistence.Entity;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.world.color.TrueColor;

/**
 * 银行
 */
@Entity
public class Bank
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    TrueColor color;

    @Override
    public void populate(Object source) {
        if (source instanceof Bank)
            _populate((Bank) source);
        else
            super.populate(source);
    }

    protected void _populate(Bank o) {
        super._populate(o);
        if (o.color == null)
            color = null;
        else
            color = o.color.clone();
    }

    /**
     * 显示颜色（未使用）
     */
    public TrueColor getColor() {
        return color;
    }

    public void setColor(TrueColor color) {
        this.color = color;
    }

}
